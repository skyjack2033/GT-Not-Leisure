package com.science.gtnl.container;

import net.minecraft.entity.player.InventoryPlayer;

import com.science.gtnl.api.mixinHelper.IDualityInterface;

import appeng.container.implementations.ContainerInterface;
import appeng.container.slot.*;
import appeng.helpers.DualityInterface;
import appeng.helpers.IInterfaceHost;
import appeng.util.Platform;

public class ContainerSuperInterface extends ContainerInterface {

    public final DualityInterface myDuality;
    public int configSlots, storageSlots, patternSlots, upgradeSlots;

    public static final int PATTERNS_PER_PAGE = 36;
    public static final int CONFIG_PER_PAGE = 9;
    public int currentPage = 0;

    public static final int SLOT_SIZE = 18;
    public static final int TITLE_HEIGHT = 15;
    public static final int SECTION_GAP = 4;
    public static final int PLAYER_INV_HEIGHT = 82;

    public ContainerSuperInterface(InventoryPlayer ip, IInterfaceHost te) {
        super(ip, te);
        this.myDuality = te.getInterfaceDuality();
        var accessor = (IDualityInterface) myDuality;

        configSlots = accessor.getConfigSlots();
        storageSlots = accessor.getStorageSlots();
        patternSlots = accessor.getPatternSlots();
        upgradeSlots = accessor.getUpgradeSlots();

        this.refreshSlots(ip);
    }

    public void refreshSlots(InventoryPlayer ip) {
        this.inventorySlots.clear();
        this.inventoryItemStacks.clear();

        this.addUpgradeSlots();

        int configStartIdx = currentPage * CONFIG_PER_PAGE;
        for (int x = 0; x < 9; x++) {
            int idx = configStartIdx + x;
            if (idx < configSlots) {
                this.addSlotToContainer(
                    new OptionalSlotFake(this.myDuality.getConfig(), this, idx, 8 + SLOT_SIZE * x, TITLE_HEIGHT, 0));
            }
            if (idx < storageSlots) {
                this.addSlotToContainer(
                    new SlotNormal(this.myDuality.getStorage(), idx, 8 + SLOT_SIZE * x, TITLE_HEIGHT + SLOT_SIZE));
            }
        }

        int patternStartIdx = currentPage * PATTERNS_PER_PAGE;
        int patternStartY = TITLE_HEIGHT + (2 * SLOT_SIZE) + SECTION_GAP;

        for (int i = 0; i < PATTERNS_PER_PAGE; i++) {
            int idx = patternStartIdx + i;
            if (idx < patternSlots) {
                int x = i % 9;
                int y = i / 9;
                this.addSlotToContainer(
                    new OptionalSlotRestrictedInput(
                        SlotRestrictedInput.PlacableItemType.ENCODED_PATTERN,
                        this.myDuality.getPatterns(),
                        this,
                        idx,
                        8 + SLOT_SIZE * x,
                        patternStartY + y * SLOT_SIZE,
                        y,
                        ip).setStackLimit(1));
            }
        }

        this.bindPlayerInventory(ip, 8, this.getHeight() - PLAYER_INV_HEIGHT);
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

    @Override
    public int availableUpgrades() {
        return upgradeSlots;
    }

    @Override
    public boolean isSlotEnabled(final int idx) {
        return !Platform.isClient() || (!isEmpty && !isConfigEmpty);
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
