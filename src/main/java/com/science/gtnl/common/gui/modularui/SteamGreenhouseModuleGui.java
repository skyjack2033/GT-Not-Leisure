package com.science.gtnl.common.gui.modularui;

import static gregtech.api.metatileentity.BaseTileEntity.TOOLTIP_DELAY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumChatFormatting;

import org.jetbrains.annotations.Nullable;

import com.cleanroommc.modularui.api.IPanelHandler;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.api.widget.IWidget;
import com.cleanroommc.modularui.drawable.DynamicDrawable;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.RichTooltip;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.value.sync.BooleanSyncValue;
import com.cleanroommc.modularui.value.sync.DynamicSyncHandler;
import com.cleanroommc.modularui.value.sync.GenericListSyncHandler;
import com.cleanroommc.modularui.value.sync.IntSyncValue;
import com.cleanroommc.modularui.value.sync.InteractionSyncHandler;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widget.EmptyWidget;
import com.cleanroommc.modularui.widget.ParentWidget;
import com.cleanroommc.modularui.widgets.ButtonWidget;
import com.cleanroommc.modularui.widgets.DynamicSyncedWidget;
import com.cleanroommc.modularui.widgets.ItemDisplayWidget;
import com.cleanroommc.modularui.widgets.layout.Flow;
import com.science.gtnl.common.machine.multiblock.module.steamElevator.SteamGreenhouseModule;
import com.science.gtnl.utils.machine.greenHouseManager.GreenHouseBucket;

import gregtech.api.modularui2.GTGuiTextures;

public class SteamGreenhouseModuleGui extends GTNLSteamMultiBlockBaseGui {

    private static final String INVENTORY_VIEW_SYNC_KEY = "steamGreenhouseInventoryView";
    private static final String SEED_SLOT_LIST_SYNC_KEY = "steamGreenhouseSeedSlots";
    private static final String SEED_SLOT_WIDGET_SYNC_KEY = "steamGreenhouseSeedSlotWidget";
    private static final String MAX_SEED_TYPES_SYNC_KEY = "steamGreenhouseMaxSeedTypes";
    private static final String MAX_SEED_COUNT_SYNC_KEY = "steamGreenhouseMaxSeedCount";
    private static final String USED_SEED_TYPES_SYNC_KEY = "steamGreenhouseUsedSeedTypes";
    private static final String USED_SEED_COUNT_SYNC_KEY = "steamGreenhouseUsedSeedCount";
    private static final String SEED_INVENTORY_ENABLED_SYNC_KEY = "steamGreenhouseSeedInventoryEnabled";
    private static final String DROP_TRACKER_SYNC_KEY = "steamGreenhouseDropTracker";
    private static final int TERMINAL_HEIGHT = 85;
    private static final int SEED_INVENTORY_WIDTH = 128;
    private static final int SEED_INVENTORY_HEIGHT = 60;
    private static final int SLOT_SIZE = 18;
    private static final int SLOTS_PER_ROW = SEED_INVENTORY_WIDTH / SLOT_SIZE;

    private final SteamGreenhouseModule steamGreenhouse;
    private DynamicSyncHandler seedInventoryWidgetSyncer;

    public SteamGreenhouseModuleGui(SteamGreenhouseModule multiblock) {
        super(multiblock);
        this.steamGreenhouse = multiblock;
    }

    @Override
    public ModularPanel build(PosGuiData guiData, PanelSyncManager syncManager, UISettings uiSettings) {
        steamGreenhouse.isInInventory = !baseMetaTileEntity.isActive();
        return super.build(guiData, syncManager, uiSettings);
    }

    @Override
    protected void registerSyncValues(PanelSyncManager syncManager) {
        syncManager.syncValue(
            DROP_TRACKER_SYNC_KEY,
            GenericListSyncHandler.<DropEntry>builder()
                .getter(this::createDropEntries)
                .setter(this::setClientDropEntries)
                .serializer(DropEntry::write)
                .deserializer(DropEntry::read)
                .equals(SteamGreenhouseModuleGui::areDropEntriesEqual)
                .build());
        super.registerSyncValues(syncManager);
        new GTNLGreenHouseGui(steamGreenhouse).registerSyncValues(syncManager);

        BooleanSyncValue inventoryViewSyncer = new BooleanSyncValue(
            () -> steamGreenhouse.isInInventory,
            value -> steamGreenhouse.isInInventory = value).allowC2S();
        syncManager.syncValue(INVENTORY_VIEW_SYNC_KEY, inventoryViewSyncer);

        IntSyncValue maxSeedTypesSyncer = new IntSyncValue(steamGreenhouse::getMaxSeedTypes);
        IntSyncValue maxSeedCountSyncer = new IntSyncValue(steamGreenhouse::getMaxSeedCount);
        IntSyncValue usedSeedTypesSyncer = new IntSyncValue(
            () -> steamGreenhouse.getBuckets()
                .size());
        IntSyncValue usedSeedCountSyncer = new IntSyncValue(steamGreenhouse::getTotalSeedCount);
        syncManager.syncValue(MAX_SEED_TYPES_SYNC_KEY, maxSeedTypesSyncer);
        syncManager.syncValue(MAX_SEED_COUNT_SYNC_KEY, maxSeedCountSyncer);
        syncManager.syncValue(USED_SEED_TYPES_SYNC_KEY, usedSeedTypesSyncer);
        syncManager.syncValue(USED_SEED_COUNT_SYNC_KEY, usedSeedCountSyncer);
        syncManager.syncValue(
            SEED_INVENTORY_ENABLED_SYNC_KEY,
            new BooleanSyncValue(() -> steamGreenhouse.getMaxProgressTime() == 0));

        GenericListSyncHandler<SeedSlot> seedSlotSyncer = GenericListSyncHandler.<SeedSlot>builder()
            .getter(this::createSeedSlots)
            .setter(unused -> {})
            .serializer(SeedSlot::write)
            .deserializer(SeedSlot::read)
            .equals(SteamGreenhouseModuleGui::areSeedSlotsEqual)
            .immutableCopy()
            .build();
        syncManager.syncValue(SEED_SLOT_LIST_SYNC_KEY, seedSlotSyncer);
        seedInventoryWidgetSyncer = new DynamicSyncHandler().widgetProvider((unused, packet) -> {
            if (packet == null) {
                return new EmptyWidget();
            }
            return createSeedInventoryWidget(syncManager);
        });
        syncManager.syncValue(SEED_SLOT_WIDGET_SYNC_KEY, seedInventoryWidgetSyncer);
        if (!syncManager.isClient()) {
            seedSlotSyncer.setChangeListener(this::notifySeedInventoryUpdate);
            maxSeedTypesSyncer.setChangeListener(this::notifySeedInventoryUpdate);
            maxSeedCountSyncer.setChangeListener(this::notifySeedInventoryUpdate);
            usedSeedTypesSyncer.setChangeListener(this::notifySeedInventoryUpdate);
            usedSeedCountSyncer.setChangeListener(this::notifySeedInventoryUpdate);
        }
    }

    @Override
    protected void initPanelMap(ModularPanel parent, PanelSyncManager syncManager) {
        super.initPanelMap(parent, syncManager);
        new GTNLGreenHouseGui(steamGreenhouse).registerPanel(parent, syncManager, panelMap);
    }

    @Override
    protected ParentWidget<?> createTerminalParentWidget(ModularPanel panel, PanelSyncManager syncManager) {
        BooleanSyncValue inventoryViewSyncer = syncManager
            .findSyncHandler(INVENTORY_VIEW_SYNC_KEY, BooleanSyncValue.class);
        DynamicSyncedWidget<?> seedInventoryWidget = createDynamicSeedInventoryWidget(syncManager);
        seedInventoryWidget.pos(10, 16)
            .setEnabledIf(unused -> inventoryViewSyncer.getBoolValue());
        return new ParentWidget<>().size(getTerminalWidgetWidth(), getTerminalWidgetHeight())
            .child(
                GTGuiTextures.PICTURE_SCREEN_BLACK.asWidget()
                    .pos(4, 4)
                    .size(190, 85)
                    .setEnabledIf(unused -> !inventoryViewSyncer.getBoolValue()))
            .child(seedInventoryWidget)
            .child(
                createTerminalTextWidget(syncManager, panel).pos(0, 7)
                    .size(190, 79)
                    .collapseDisabledChild()
                    .setEnabledIf(unused -> !inventoryViewSyncer.getBoolValue()))
            .childIf(
                steamGreenhouse.supportsTerminalRightCornerColumn(),
                () -> createTerminalRightCornerColumn(panel, syncManager))
            .childIf(
                steamGreenhouse.supportsTerminalLeftCornerColumn(),
                () -> createTerminalLeftCornerColumn(panel, syncManager));
    }

    @Override
    protected int getTerminalRowHeight() {
        return TERMINAL_HEIGHT + 9;
    }

    @Override
    protected int getTerminalWidgetHeight() {
        return TERMINAL_HEIGHT + 9;
    }

    @Override
    protected Flow createPanelGap(ModularPanel parent, PanelSyncManager syncManager) {
        return Flow.row()
            .fullWidth()
            .height(getTextBoxToInventoryGap())
            .paddingLeft(4)
            .paddingRight(25)
            .mainAxisAlignment(Alignment.MainAxis.END)
            .child(createInventoryStatusToggle(syncManager))
            .child(createConfigurationButton())
            .child(createRightPanelGapRow(parent, syncManager));
    }

    private IWidget createInventoryStatusToggle(PanelSyncManager syncManager) {
        BooleanSyncValue inventoryViewSyncer = syncManager
            .findSyncHandler(INVENTORY_VIEW_SYNC_KEY, BooleanSyncValue.class);
        return new ButtonWidget<>().size(55, 16)
            .background(GTGuiTextures.BUTTON_STANDARD)
            .overlay(
                new DynamicDrawable(
                    () -> IKey
                        .lang(
                            inventoryViewSyncer.getBoolValue() ? "Info_EdenGarden_Inventory" : "Info_EdenGarden_Status")
                        .asIcon()
                        .size(55, 16)))
            .syncHandler(
                new InteractionSyncHandler().setOnMousePressed(
                    unused -> inventoryViewSyncer.setBoolValue(!inventoryViewSyncer.getBoolValue(), true, true)))
            .tooltipShowUpTimer(TOOLTIP_DELAY);
    }

    private IWidget createConfigurationButton() {
        IPanelHandler panelHandler = panelMap.get(GTNLGreenHouseGui.CONFIGURATION_PANEL_KEY);
        return new GTNLGreenHouseGui(steamGreenhouse).createConfigurationButton(panelHandler);
    }

    private DynamicSyncedWidget<?> createDynamicSeedInventoryWidget(PanelSyncManager syncManager) {
        DynamicSyncHandler syncer = syncManager.findSyncHandler(SEED_SLOT_WIDGET_SYNC_KEY, DynamicSyncHandler.class);
        return new DynamicSyncedWidget<>().syncHandler(syncer)
            .initialChild(createSeedInventoryWidget(syncManager))
            .size(SEED_INVENTORY_WIDTH, SEED_INVENTORY_HEIGHT);
    }

    private IWidget createSeedInventoryWidget(PanelSyncManager syncManager) {
        GenericListSyncHandler<SeedSlot> seedSlotSyncer = getSeedSlotSyncer(syncManager);
        IntSyncValue maxSeedTypesSyncer = syncManager.findSyncHandler(MAX_SEED_TYPES_SYNC_KEY, IntSyncValue.class);
        IntSyncValue maxSeedCountSyncer = syncManager.findSyncHandler(MAX_SEED_COUNT_SYNC_KEY, IntSyncValue.class);
        IntSyncValue usedSeedTypesSyncer = syncManager.findSyncHandler(USED_SEED_TYPES_SYNC_KEY, IntSyncValue.class);
        IntSyncValue usedSeedCountSyncer = syncManager.findSyncHandler(USED_SEED_COUNT_SYNC_KEY, IntSyncValue.class);

        Flow column = Flow.column()
            .size(SEED_INVENTORY_WIDTH, SEED_INVENTORY_HEIGHT)
            .crossAxisAlignment(Alignment.CrossAxis.START);
        List<IWidget> buttons = new ArrayList<>();
        for (SeedSlot seedSlot : seedSlotSyncer.getValue()) {
            buttons.add(createSeedSlotButton(seedSlot, syncManager));
        }
        if (usedSeedCountSyncer.getIntValue() < maxSeedCountSyncer.getIntValue()) {
            buttons.add(
                createInsertionSlotButton(
                    maxSeedTypesSyncer,
                    maxSeedCountSyncer,
                    usedSeedTypesSyncer,
                    usedSeedCountSyncer,
                    syncManager));
        }

        for (int i = 0; i < buttons.size(); i += SLOTS_PER_ROW) {
            Flow row = Flow.row()
                .height(SLOT_SIZE);
            for (int j = 0; j < SLOTS_PER_ROW && i + j < buttons.size(); j++) {
                row.child(buttons.get(i + j));
            }
            column.child(row);
        }
        return column;
    }

    private IWidget createSeedSlotButton(SeedSlot seedSlot, PanelSyncManager syncManager) {
        return new ButtonWidget<>().size(SLOT_SIZE, SLOT_SIZE)
            .background(GTGuiTextures.SLOT_ITEM_DARK)
            .child(
                new ItemDisplayWidget().item(seedSlot.stack())
                    .displayAmount(true)
                    .disableThemeBackground(true)
                    .disableHoverThemeBackground(true)
                    .size(SLOT_SIZE, SLOT_SIZE))
            .syncHandler(new InteractionSyncHandler().setOnMousePressed(mouseData -> {
                if (!isSeedInventoryEnabled()) return;
                if (mouseData.mouseButton == 2) {
                    handleCreativeSeedCopy(seedSlot, syncManager);
                    return;
                }
                if (mouseData.shift) {
                    extractSeedToInventory(seedSlot.index(), syncManager);
                    return;
                }
                ItemStack cursorStack = syncManager.getCursorItem();
                if (cursorStack != null) {
                    insertCursorSeed(cursorStack, mouseData.mouseButton == 1, syncManager);
                    return;
                }
                extractSeedToCursor(seedSlot.index(), syncManager);
            }))
            .tooltipBuilder(tooltip -> addSeedTooltip(tooltip, seedSlot.stack(), syncManager))
            .tooltipShowUpTimer(TOOLTIP_DELAY);
    }

    private IWidget createInsertionSlotButton(IntSyncValue maxSeedTypesSyncer, IntSyncValue maxSeedCountSyncer,
        IntSyncValue usedSeedTypesSyncer, IntSyncValue usedSeedCountSyncer, PanelSyncManager syncManager) {
        return new ButtonWidget<>().size(SLOT_SIZE, SLOT_SIZE)
            .background(GTGuiTextures.SLOT_ITEM_DARK)
            .child(
                IKey.dynamic(() -> String.valueOf(maxSeedCountSyncer.getIntValue() - usedSeedCountSyncer.getIntValue()))
                    .asWidget()
                    .size(SLOT_SIZE, SLOT_SIZE)
                    .textAlign(Alignment.BottomRight))
            .syncHandler(new InteractionSyncHandler().setOnMousePressed(mouseData -> {
                if (!isSeedInventoryEnabled()) return;
                ItemStack cursorStack = syncManager.getCursorItem();
                if (cursorStack != null) {
                    insertCursorSeed(cursorStack, mouseData.mouseButton == 1, syncManager);
                }
            }))
            .tooltipBuilder(
                tooltip -> tooltip
                    .addLine(
                        IKey.str(
                            EnumChatFormatting.DARK_PURPLE + "Remaining seed types: "
                                + (maxSeedTypesSyncer.getIntValue() - usedSeedTypesSyncer.getIntValue())))
                    .addLine(
                        IKey.str(
                            EnumChatFormatting.DARK_GREEN + "Remaining seed capacity: "
                                + (maxSeedCountSyncer.getIntValue() - usedSeedCountSyncer.getIntValue()))))
            .tooltipShowUpTimer(TOOLTIP_DELAY);
    }

    private boolean isSeedInventoryEnabled() {
        return steamGreenhouse.getMaxProgressTime() == 0;
    }

    private void handleCreativeSeedCopy(SeedSlot seedSlot, PanelSyncManager syncManager) {
        if (!syncManager.getPlayer().capabilities.isCreativeMode || syncManager.getCursorItem() != null) return;
        ItemStack stack = seedSlot.stack()
            .copy();
        stack.stackSize = stack.getMaxStackSize();
        syncManager.setCursorItem(stack);
        updateHeldItem(syncManager);
    }

    private void extractSeedToInventory(int bucketIndex, PanelSyncManager syncManager) {
        ItemStack removed = removeSeedStack(bucketIndex, syncManager);
        if (removed == null) return;
        if (syncManager.getPlayer().inventory.addItemStackToInventory(removed)) {
            syncManager.getPlayer().inventoryContainer.detectAndSendChanges();
        } else {
            syncManager.getPlayer()
                .entityDropItem(removed, 0.0f);
        }
        notifySeedInventoryUpdate();
    }

    private void extractSeedToCursor(int bucketIndex, PanelSyncManager syncManager) {
        ItemStack removed = removeSeedStack(bucketIndex, syncManager);
        if (removed == null) return;
        syncManager.setCursorItem(removed);
        updateHeldItem(syncManager);
        notifySeedInventoryUpdate();
    }

    private @Nullable ItemStack removeSeedStack(int bucketIndex, PanelSyncManager syncManager) {
        if (bucketIndex < 0 || bucketIndex >= steamGreenhouse.getBuckets()
            .size()) {
            return null;
        }
        GreenHouseBucket bucket = steamGreenhouse.getBuckets()
            .get(bucketIndex);
        int maxRemove = bucket.getSeedStack()
            .getMaxStackSize();
        ItemStack[] outputs = bucket.tryRemoveSeed(maxRemove, false);
        if (outputs == null || outputs.length == 0) return null;
        ItemStack result = outputs[0];
        for (int i = 1; i < outputs.length; i++) {
            addSupportItemToPlayer(outputs[i], syncManager);
        }
        if (bucket.getSeedCount() <= 0) {
            steamGreenhouse.getBuckets()
                .remove(bucket);
        }
        return result;
    }

    private void insertCursorSeed(ItemStack cursorStack, boolean singleItem, PanelSyncManager syncManager) {
        ItemStack stackToInsert = cursorStack;
        if (singleItem) {
            stackToInsert = cursorStack.copy();
            stackToInsert.stackSize = 1;
        }
        steamGreenhouse.addCrop(stackToInsert);
        if (singleItem) {
            if (stackToInsert.stackSize == 1) return;
            cursorStack.stackSize--;
        }
        if (cursorStack.stackSize > 0) {
            syncManager.setCursorItem(null);
            updateHeldItem(syncManager);
            syncManager.setCursorItem(cursorStack);
            updateHeldItem(syncManager);
        } else {
            syncManager.setCursorItem(null);
            updateHeldItem(syncManager);
        }
        notifySeedInventoryUpdate();
    }

    private void updateHeldItem(PanelSyncManager syncManager) {
        if (syncManager.getPlayer() instanceof EntityPlayerMP playerMP) {
            playerMP.isChangingQuantityOnly = false;
            playerMP.updateHeldItem();
        }
    }

    private void addSupportItemToPlayer(ItemStack supportItem, PanelSyncManager syncManager) {
        if (!syncManager.getPlayer().inventory.addItemStackToInventory(supportItem)) {
            syncManager.getPlayer()
                .entityDropItem(supportItem, 0.0f);
        }
    }

    private void addSeedTooltip(RichTooltip tooltip, ItemStack stack, PanelSyncManager syncManager) {
        List<String> lines = stack.getTooltip(syncManager.getPlayer(), false);
        if (!lines.isEmpty() && lines.get(0) != null) {
            lines.set(0, stack.stackSize + " x " + lines.get(0));
        }
        for (String line : lines) {
            tooltip.addLine(IKey.str(line));
        }
    }

    private void notifySeedInventoryUpdate() {
        if (seedInventoryWidgetSyncer != null) {
            seedInventoryWidgetSyncer.notifyUpdate(unused -> {});
        }
    }

    private List<SeedSlot> createSeedSlots() {
        List<SeedSlot> slots = new ArrayList<>();
        for (int i = 0; i < steamGreenhouse.getBuckets()
            .size(); i++) {
            GreenHouseBucket bucket = steamGreenhouse.getBuckets()
                .get(i);
            if (bucket == null) continue;
            ItemStack stack = bucket.getSeedStack();
            if (stack == null) continue;
            slots.add(new SeedSlot(i, stack.copy()));
        }
        return slots;
    }

    private List<DropEntry> createDropEntries() {
        Map<ItemStack, Double> merged = new HashMap<>();
        for (Map.Entry<ItemStack, Double> drop : steamGreenhouse.guiDropTracker.entrySet()) {
            merged.merge(drop.getKey(), drop.getValue(), Double::sum);
        }
        List<DropEntry> entries = new ArrayList<>(merged.size());
        for (Map.Entry<ItemStack, Double> drop : merged.entrySet()) {
            entries.add(
                new DropEntry(
                    drop.getKey()
                        .copy(),
                    drop.getValue()));
        }
        entries.sort(
            Comparator.comparing(
                entry -> entry.stack()
                    .toString()
                    .toLowerCase()));
        return entries;
    }

    private void setClientDropEntries(List<DropEntry> entries) {
        HashMap<ItemStack, Double> tracker = new HashMap<>(entries.size());
        for (DropEntry entry : entries) {
            tracker.put(entry.stack(), entry.chance());
        }
        steamGreenhouse.synchedGUIDropTracker = tracker;
    }

    @SuppressWarnings("unchecked")
    private GenericListSyncHandler<SeedSlot> getSeedSlotSyncer(PanelSyncManager syncManager) {
        return syncManager.findSyncHandler(SEED_SLOT_LIST_SYNC_KEY, GenericListSyncHandler.class);
    }

    private static boolean areSeedSlotsEqual(SeedSlot first, SeedSlot second) {
        if (first == null || second == null) return first == second;
        return first.index() == second.index() && ItemStack.areItemStacksEqual(first.stack(), second.stack());
    }

    private static boolean areDropEntriesEqual(DropEntry first, DropEntry second) {
        if (first == null || second == null) return first == second;
        return Double.compare(first.chance(), second.chance()) == 0
            && ItemStack.areItemStacksEqual(first.stack(), second.stack());
    }

    public record SeedSlot(int index, ItemStack stack) {

        public static void write(PacketBuffer buffer, SeedSlot seedSlot) throws IOException {
            buffer.writeVarIntToBuffer(seedSlot.index());
            buffer.writeItemStackToBuffer(seedSlot.stack());
        }

        public static SeedSlot read(PacketBuffer buffer) throws IOException {
            return new SeedSlot(buffer.readVarIntFromBuffer(), buffer.readItemStackFromBuffer());
        }
    }

    public record DropEntry(ItemStack stack, double chance) {

        public static void write(PacketBuffer buffer, DropEntry entry) throws IOException {
            buffer.writeItemStackToBuffer(entry.stack());
            buffer.writeDouble(entry.chance());
        }

        public static DropEntry read(PacketBuffer buffer) throws IOException {
            return new DropEntry(buffer.readItemStackFromBuffer(), buffer.readDouble());
        }
    }
}
