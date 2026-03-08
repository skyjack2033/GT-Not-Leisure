package com.science.gtnl.container;

import net.minecraft.entity.player.InventoryPlayer;

import com.science.gtnl.api.mixinHelper.IDualityInterface;

import appeng.container.implementations.ContainerInterface;
import appeng.container.slot.*;
import appeng.helpers.DualityInterface;
import appeng.helpers.IInterfaceHost;
import appeng.util.Platform;

// TODO:再加个二合一ME接口
public class ContainerSuperInterface extends ContainerInterface {

    public final DualityInterface myDuality;
    public int configSlots, storageSlots, patternSlots, upgradeSlots;

    public static final int PATTERNS_PER_PAGE = 36;
    public static final int CONFIG_PER_PAGE = 9;
    public int currentPage = 0;
    public final int maxPage;
    public final OptionalSlotRestrictedInput[][] patternSlotPages;

    public static final int SLOT_SIZE = 18;
    public static final int TITLE_HEIGHT = 15;
    public static final int SECTION_GAP = 3;
    public static final int PLAYER_INV_HEIGHT = 82;

    public ContainerSuperInterface(InventoryPlayer ip, IInterfaceHost te) {
        super(ip, te);
        this.myDuality = te.getInterfaceDuality();
        var accessor = (IDualityInterface) myDuality;

        configSlots = accessor.getConfigSlots();
        storageSlots = accessor.getStorageSlots();
        patternSlots = accessor.getPatternSlots();
        upgradeSlots = accessor.getUpgradeSlots();

        maxPage = getMaxPages();
        patternSlotPages = new OptionalSlotRestrictedInput[maxPage][PATTERNS_PER_PAGE];

        this.refreshSlots(ip);
    }

    public void refreshSlots(InventoryPlayer ip) {
        this.inventorySlots.clear();
        this.inventoryItemStacks.clear();

        this.addUpgradeSlots();

        for (int i = 0; i < 9 * maxPage; i++) {
            var x = i % 9;
            this.addSlotToContainer(
                new OptionalSlotFake(this.myDuality.getConfig(), this, i, 8 + SLOT_SIZE * x, TITLE_HEIGHT, -1));
            this.addSlotToContainer(
                new SlotNormal(this.myDuality.getStorage(), i, 8 + SLOT_SIZE * i, TITLE_HEIGHT + SLOT_SIZE));
        }

        int patternStartY = TITLE_HEIGHT + (2 * SLOT_SIZE) + SECTION_GAP;

        for (int i = 0; i < 36 * maxPage; i++) {
            int x = i % 9;
            int y = (i / 9) % 4;
            var g = i / 36;
            this.addSlotToContainer(
                (patternSlotPages[g][i % 36] = new OptionalSlotRestrictedInput(
                    SlotRestrictedInput.PlacableItemType.ENCODED_PATTERN,
                    this.myDuality.getPatterns(),
                    this,
                    i,
                    8 + SLOT_SIZE * x,
                    patternStartY + y * SLOT_SIZE,
                    g,
                    ip)).setStackLimit(1));
            if (g > 0) {
                patternSlotPages[g][i % 36].xDisplayPosition = Integer.MIN_VALUE;
            }
        }

        this.bindPlayerInventory(ip, 0, this.getHeight() - PLAYER_INV_HEIGHT);
    }

    public void previousPage() {
        if (currentPage > 0) {
            for (var slot : patternSlotPages[currentPage--]) {
                slot.xDisplayPosition = Integer.MIN_VALUE;
            }
            for (var slot : patternSlotPages[currentPage]) {
                slot.xDisplayPosition = slot.getX();
            }
        }
    }

    public void nextPage() {
        if (currentPage < maxPage - 1) {
            for (var slot : patternSlotPages[currentPage++]) {
                slot.xDisplayPosition = Integer.MIN_VALUE;
            }
            for (var slot : patternSlotPages[currentPage]) {
                slot.xDisplayPosition = slot.getX();
            }
        }
    }

    public void addUpgradeSlots() {
        for (int i = 0; i < upgradeSlots; i++) {
            this.addSlotToContainer(
                new SlotRestrictedInput(
                    SlotRestrictedInput.PlacableItemType.UPGRADES,
                    this.myDuality.getUpgrades(),
                    i,
                    187 + (i / 5) * SLOT_SIZE,
                    8 + (i % 5) * SLOT_SIZE,
                    this.getInventoryPlayer()));
        }
    }

    private boolean init;

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (init) return;
        init = true;
    }

    @Override
    public int availableUpgrades() {
        return upgradeSlots;
    }

    @Override
    public boolean isSlotEnabled(final int idx) {
        if (Platform.isClient()) {
            if (!isEmpty && !isConfigEmpty) {
                return idx == currentPage || idx < 0 || !init;
            } else return false;
        } else return true;
    }

    @Override
    public int getHeight() {
        return 211;
    }

    public int getMaxPages() {
        int pagesConfig = (int) Math.ceil((double) configSlots / CONFIG_PER_PAGE);
        int pagesStorage = (int) Math.ceil((double) storageSlots / CONFIG_PER_PAGE);
        int pagesPattern = (int) Math.ceil((double) patternSlots / PATTERNS_PER_PAGE);

        return Math.max(1, Math.max(pagesConfig, Math.max(pagesStorage, pagesPattern)));
    }
}
