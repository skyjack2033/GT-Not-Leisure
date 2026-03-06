package com.science.gtnl.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.science.gtnl.container.ContainerSuperInterface;

import appeng.api.config.*;
import appeng.client.gui.implementations.GuiUpgradeable;
import appeng.client.gui.widgets.*;
import appeng.core.AELog;
import appeng.core.localization.*;
import appeng.core.sync.GuiBridge;
import appeng.core.sync.network.NetworkHandler;
import appeng.core.sync.packets.*;
import appeng.helpers.IInterfaceHost;

public class GuiSuperInterface extends GuiUpgradeable {

    public GuiTabButton priority;
    public GuiImgButton BlockMode, SmartBlockMode, fuzzyMode, insertionMode, advancedBlockingMode, lockCraftingMode;
    public GuiToggleButton interfaceMode, patternOptimization;
    public GuiSimpleImgButton doublePatterns;

    public GuiSuperInterface(InventoryPlayer inventoryPlayer, IInterfaceHost te) {
        super(new ContainerSuperInterface(inventoryPlayer, te));
    }

    @Override
    public void addButtons() {
        int offset = 8;
        int btnX = this.guiLeft - 18;

        this.priority = new GuiTabButton(
            this.guiLeft + 154,
            this.guiTop,
            2 + 4 * 16,
            GuiText.Priority.getLocal(),
            itemRender);
        this.buttonList.add(this.priority);

        this.BlockMode = new GuiImgButton(btnX, this.guiTop + offset, Settings.BLOCK, YesNo.NO);
        this.buttonList.add(this.BlockMode);
        this.SmartBlockMode = new GuiImgButton(btnX - 18, this.guiTop + offset, Settings.SMART_BLOCK, YesNo.NO);
        this.buttonList.add(this.SmartBlockMode);

        offset += 18;
        this.interfaceMode = new GuiToggleButton(
            btnX,
            this.guiTop + offset,
            84,
            85,
            GuiText.InterfaceTerminal.getLocal(),
            GuiText.InterfaceTerminalHint.getLocal());
        this.buttonList.add(this.interfaceMode);

        offset += 18;
        this.insertionMode = new GuiImgButton(
            btnX,
            this.guiTop + offset,
            Settings.INSERTION_MODE,
            InsertionMode.DEFAULT);
        this.buttonList.add(this.insertionMode);

        offset += 18;
        this.doublePatterns = new GuiSimpleImgButton(btnX, this.guiTop + offset, 71, "");
        this.buttonList.add(this.doublePatterns);

        offset += 18;
        this.patternOptimization = new GuiToggleButton(
            btnX,
            this.guiTop + offset,
            178,
            194,
            GuiText.PatternOptimization.getLocal(),
            GuiText.PatternOptimizationHint.getLocal());
        this.buttonList.add(this.patternOptimization);

        offset += 18;
        this.advancedBlockingMode = new GuiImgButton(
            btnX,
            this.guiTop + offset,
            Settings.ADVANCED_BLOCKING_MODE,
            AdvancedBlockingMode.DEFAULT);
        this.buttonList.add(advancedBlockingMode);

        offset += 18;
        this.lockCraftingMode = new GuiImgButton(
            btnX,
            this.guiTop + offset,
            Settings.LOCK_CRAFTING_MODE,
            LockCraftingMode.NONE);
        this.buttonList.add(lockCraftingMode);

        offset += 18;
        this.fuzzyMode = new GuiImgButton(btnX, this.guiTop + offset, Settings.FUZZY_MODE, FuzzyMode.IGNORE_ALL);
        this.buttonList.add(fuzzyMode);
    }

    @Override
    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        ContainerSuperInterface container = (ContainerSuperInterface) this.cvb;

        if (this.BlockMode != null) this.BlockMode.set(container.bMode);
        if (this.SmartBlockMode != null) this.SmartBlockMode.set(container.sbMode);
        if (this.interfaceMode != null) this.interfaceMode.setState(container.iTermMode == YesNo.YES);
        if (this.insertionMode != null) this.insertionMode.set(container.insertionMode);
        if (this.patternOptimization != null)
            this.patternOptimization.setState(container.patternOptimization == YesNo.YES);
        if (this.advancedBlockingMode != null) this.advancedBlockingMode.set(container.advancedBlockingMode);
        if (this.lockCraftingMode != null) this.lockCraftingMode.set(container.lockCraftingMode);
        if (this.fuzzyMode != null) this.fuzzyMode.set(container.fuzzyMode);

        if (this.doublePatterns != null) {
            this.doublePatterns.enabled = container.isAllowedToMultiplyPatterns;
            this.doublePatterns.setTooltip(
                ButtonToolTips.DoublePatterns.getLocal() + "\n"
                    + (this.doublePatterns.enabled ? ButtonToolTips.DoublePatternsHint.getLocal()
                        : ButtonToolTips.OptimizePatternsNoReq.getLocal()));
        }

        this.fontRendererObj.drawString(GuiText.Interface.getLocal(), 8, 6, GuiColors.InterfaceTitle.getColor());
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY);

        var csi = (ContainerSuperInterface) this.cvb;
        final int slotSize = ContainerSuperInterface.SLOT_SIZE;
        final int titleHeight = ContainerSuperInterface.TITLE_HEIGHT;

        final int capacity = csi.getPatternCapacityCardsInstalled();

        int pairs = (int) Math.ceil((double) Math.max(csi.configSlots, csi.storageSlots) / 9);

        for (int i = 0; i < pairs; i++) {
            int rowY = offsetY + titleHeight + (i * 2 * slotSize);

            int configUVY = (capacity == -1) ? 89 : 71;
            this.drawTexturedModalRect(offsetX + 7, rowY, 7, configUVY, 162, 18);

            this.drawTexturedModalRect(offsetX + 7, rowY + slotSize, 7, 107, 162, 18);
        }

        int patternRows = (int) Math.ceil((double) csi.patternSlots / 9);
        int patternAreaStartY = titleHeight + (pairs * 2 * slotSize) + ContainerSuperInterface.SECTION_GAP;

        for (int i = patternRows; i > 0; i--) {
            int currentRowY = offsetY + patternAreaStartY + (patternRows - i) * slotSize;

            if (i > capacity + 1) {
                this.drawTexturedModalRect(offsetX + 7, currentRowY, 7, 89, 162, 18);
            } else {
                this.drawTexturedModalRect(offsetX + 7, currentRowY, 7, 107, 162, 18);
            }
        }
    }

    @Override
    public String getBackground() {
        return "guis/super_interface.png";
    }

    @Override
    public void actionPerformed(final GuiButton btn) {
        super.actionPerformed(btn);

        final boolean backwards = Mouse.isButtonDown(1);

        if (btn == this.priority) {
            NetworkHandler.instance.sendToServer(new PacketSwitchGuis(GuiBridge.GUI_PRIORITY));
        }

        if (btn == this.interfaceMode) {
            NetworkHandler.instance.sendToServer(new PacketConfigButton(Settings.INTERFACE_TERMINAL, backwards));
        }

        if (btn == this.BlockMode) {
            NetworkHandler.instance.sendToServer(new PacketConfigButton(this.BlockMode.getSetting(), backwards));
        }
        if (btn == this.SmartBlockMode) {
            NetworkHandler.instance.sendToServer(new PacketConfigButton(this.SmartBlockMode.getSetting(), backwards));
        }

        if (btn == this.insertionMode) {
            NetworkHandler.instance.sendToServer(new PacketConfigButton(this.insertionMode.getSetting(), backwards));
        }

        if (btn == this.doublePatterns) {
            try {
                int val = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ? 1 : 0;
                if (backwards) val |= 0b10;
                NetworkHandler.instance
                    .sendToServer(new PacketValueConfig("Interface.DoublePatterns", String.valueOf(val)));
            } catch (final Throwable e) {
                AELog.debug(e);
            }
        }

        if (btn == this.patternOptimization) {
            NetworkHandler.instance.sendToServer(new PacketConfigButton(Settings.PATTERN_OPTIMIZATION, backwards));
        }

        if (btn == this.advancedBlockingMode) {
            NetworkHandler.instance
                .sendToServer(new PacketConfigButton(this.advancedBlockingMode.getSetting(), backwards));
        }

        if (btn == this.lockCraftingMode) {
            NetworkHandler.instance.sendToServer(new PacketConfigButton(this.lockCraftingMode.getSetting(), backwards));
        }

        if (btn == this.fuzzyMode) {
            NetworkHandler.instance.sendToServer(new PacketConfigButton(this.fuzzyMode.getSetting(), backwards));
        }
    }

    @Override
    public void handleButtonVisibility() {
        super.handleButtonVisibility();
        if (this.advancedBlockingMode != null)
            this.advancedBlockingMode.setVisibility(this.bc.getInstalledUpgrades(Upgrades.ADVANCED_BLOCKING) > 0);
        if (this.lockCraftingMode != null)
            this.lockCraftingMode.setVisibility(this.bc.getInstalledUpgrades(Upgrades.LOCK_CRAFTING) > 0);
        if (this.fuzzyMode != null) this.fuzzyMode.setVisibility(this.bc.getInstalledUpgrades(Upgrades.FUZZY) > 0);
    }
}
