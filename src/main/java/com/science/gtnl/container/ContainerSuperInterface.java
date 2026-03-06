package com.science.gtnl.container;

import net.minecraft.entity.player.InventoryPlayer;

import com.science.gtnl.api.mixinHelper.IDualityInterface;

import appeng.container.implementations.ContainerInterface;
import appeng.container.slot.OptionalSlotFake;
import appeng.container.slot.OptionalSlotRestrictedInput;
import appeng.container.slot.SlotNormal;
import appeng.container.slot.SlotRestrictedInput;
import appeng.helpers.DualityInterface;
import appeng.helpers.IInterfaceHost;

public class ContainerSuperInterface extends ContainerInterface {

    public final DualityInterface myDuality;
    public int configSlots;
    public int storageSlots;
    public int patternSlots;
    public int upgradeSlots;

    public static final int SLOT_SIZE = 18;
    public static final int TITLE_HEIGHT = 15;
    public static final int SECTION_GAP = 4;
    public static final int PLAYER_INV_HEIGHT = 82;
    public static final int BOTTOM_PADDING = 8;

    public ContainerSuperInterface(InventoryPlayer ip, IInterfaceHost te) {
        super(ip, te);

        this.myDuality = te.getInterfaceDuality();
        var accessor = (IDualityInterface) myDuality;
        configSlots = accessor.getConfigSlots();
        storageSlots = accessor.getStorageSlots();
        patternSlots = accessor.getPatternSlots();
        upgradeSlots = accessor.getUpgradeSlots();

        this.inventorySlots.clear();
        this.inventoryItemStacks.clear();

        this.addUpgradeSlots();

        int pairs = (int) Math.ceil((double) Math.max(configSlots, storageSlots) / 9);

        for (int row = 0; row < pairs; row++) {
            for (int x = 0; x < 9; x++) {
                int index = row * 9 + x;
                if (index < configSlots) {
                    this.addSlotToContainer(
                        new OptionalSlotFake(
                            this.myDuality.getConfig(),
                            this,
                            index,
                            8 + SLOT_SIZE * x,
                            TITLE_HEIGHT + (row * 2 * SLOT_SIZE),
                            0));
                }
            }
            for (int x = 0; x < 9; x++) {
                int index = row * 9 + x;
                if (index < storageSlots) {
                    this.addSlotToContainer(
                        new SlotNormal(
                            this.myDuality.getStorage(),
                            index,
                            8 + SLOT_SIZE * x,
                            TITLE_HEIGHT + (row * 2 * SLOT_SIZE) + SLOT_SIZE));
                }
            }
        }

        int patternStartY = TITLE_HEIGHT + (pairs * 2 * SLOT_SIZE) + SECTION_GAP;
        for (int i = 0; i < patternSlots; i++) {
            int x = i % 9;
            int y = i / 9;
            this.addSlotToContainer(
                new OptionalSlotRestrictedInput(
                    SlotRestrictedInput.PlacableItemType.ENCODED_PATTERN,
                    this.myDuality.getPatterns(),
                    this,
                    i,
                    8 + SLOT_SIZE * x,
                    patternStartY + y * SLOT_SIZE,
                    y,
                    ip).setStackLimit(1));
        }

        this.bindPlayerInventory(ip, 8, this.getHeight() - PLAYER_INV_HEIGHT);
    }

    public void addUpgradeSlots() {
        final int MAX_ROWS = 5;
        for (int i = 0; i < upgradeSlots; i++) {
            int column = i / MAX_ROWS;
            int row = i % MAX_ROWS;
            this.addSlotToContainer(
                new SlotRestrictedInput(
                    SlotRestrictedInput.PlacableItemType.UPGRADES,
                    this.myDuality.getUpgrades(),
                    i,
                    187 + column * SLOT_SIZE,
                    8 + row * SLOT_SIZE,
                    this.getInventoryPlayer()));
        }
    }

    @Override
    public int availableUpgrades() {
        return upgradeSlots;
    }

    @Override
    protected int getHeight() {
        if (myDuality == null) return 211;
        int configRows = (int) Math.ceil((double) configSlots / 9);
        int storageRows = (int) Math.ceil((double) storageSlots / 9);
        int patternRows = (int) Math.ceil((double) patternSlots / 9);
        return TITLE_HEIGHT + (configRows * SLOT_SIZE)
            + SECTION_GAP
            + (storageRows * SLOT_SIZE)
            + SECTION_GAP
            + (patternRows * SLOT_SIZE)
            + PLAYER_INV_HEIGHT
            + BOTTOM_PADDING;
    }
}
