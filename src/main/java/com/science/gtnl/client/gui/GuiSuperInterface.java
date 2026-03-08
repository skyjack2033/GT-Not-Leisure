package com.science.gtnl.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.common.packet.InterfacePagePacket;
import com.science.gtnl.container.ContainerSuperInterface;

import appeng.api.config.AdvancedBlockingMode;
import appeng.api.config.FuzzyMode;
import appeng.api.config.InsertionMode;
import appeng.api.config.LockCraftingMode;
import appeng.api.config.Settings;
import appeng.api.config.Upgrades;
import appeng.api.config.YesNo;
import appeng.client.gui.implementations.GuiUpgradeable;
import appeng.client.gui.widgets.GuiImgButton;
import appeng.client.gui.widgets.GuiSimpleImgButton;
import appeng.client.gui.widgets.GuiTabButton;
import appeng.client.gui.widgets.GuiToggleButton;
import appeng.core.AELog;
import appeng.core.localization.ButtonToolTips;
import appeng.core.localization.GuiText;
import appeng.core.sync.GuiBridge;
import appeng.core.sync.network.NetworkHandler;
import appeng.core.sync.packets.PacketConfigButton;
import appeng.core.sync.packets.PacketSwitchGuis;
import appeng.core.sync.packets.PacketValueConfig;
import appeng.helpers.IInterfaceHost;

public class GuiSuperInterface extends GuiUpgradeable {

    public GuiTabButton priority;
    public GuiImgButton BlockMode, SmartBlockMode, fuzzyMode, insertionMode, advancedBlockingMode, lockCraftingMode;
    public GuiToggleButton interfaceMode, patternOptimization;
    public GuiSimpleImgButton doublePatterns;

    public GuiButton btnPrevPage;
    public GuiButton btnNextPage;

    public GuiSuperInterface(InventoryPlayer inventoryPlayer, IInterfaceHost te) {
        super(new ContainerSuperInterface(inventoryPlayer, te));
        this.ySize = ((ContainerSuperInterface) this.inventorySlots).getHeight();
    }

    @Override
    public void addButtons() {
        super.addButtons();
        int btnX = this.guiLeft - 18;
        int offset = 8;

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

        offset += 18 * 2;
        this.btnPrevPage = new GuiButton(101, btnX - 18, this.guiTop + offset, 16, 16, "<");

        this.btnNextPage = new GuiButton(102, btnX, this.guiTop + offset, 16, 16, ">");
        this.buttonList.add(btnPrevPage);
        this.buttonList.add(btnNextPage);
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

        this.fontRendererObj.drawString(GuiText.Interface.getLocal(), 8, 6, 4210752);
        String pageLabel = String.format("Page: %d/%d", container.currentPage + 1, container.getMaxPages());
        this.fontRendererObj.drawString(pageLabel, 110, 6, 4210752);

        this.btnPrevPage.enabled = container.currentPage > 0;
        this.btnNextPage.enabled = container.currentPage < container.getMaxPages() - 1;
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY);
        this.drawTexturedModalRect(offsetX + 7, offsetY + 14, 7, 71, 162, 18);
        this.drawTexturedModalRect(offsetX + 7, offsetY + 32, 7, 107, 162, 18);
        int patternStartY = 14 + 36 + 4;
        for (int i = 4; i > 0; i--) {
            this.drawTexturedModalRect(offsetX + 7, offsetY + patternStartY + (4 - i) * 18, 7, 107, 162, 18);
        }
    }

    @Override
    public void actionPerformed(final GuiButton btn) {
        super.actionPerformed(btn);
        ContainerSuperInterface container = (ContainerSuperInterface) this.cvb;
        final boolean backwards = Mouse.isButtonDown(1);

        if (btn == btnPrevPage) {
            container.previousPage();
        } else if (btn == btnNextPage) {
            container.nextPage();
        } else if (btn == this.priority) {
            NetworkHandler.instance.sendToServer(new PacketSwitchGuis(GuiBridge.GUI_PRIORITY));
        } else if (btn == this.interfaceMode) {
            NetworkHandler.instance.sendToServer(new PacketConfigButton(Settings.INTERFACE_TERMINAL, backwards));
        } else if (btn == this.BlockMode) {
            NetworkHandler.instance.sendToServer(new PacketConfigButton(this.BlockMode.getSetting(), backwards));
        } else if (btn == this.SmartBlockMode) {
            NetworkHandler.instance.sendToServer(new PacketConfigButton(this.SmartBlockMode.getSetting(), backwards));
        } else if (btn == this.insertionMode) {
            NetworkHandler.instance.sendToServer(new PacketConfigButton(this.insertionMode.getSetting(), backwards));
        } else if (btn == this.doublePatterns) {
            try {
                int val = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ? 1 : 0;
                if (backwards) val |= 0b10;
                NetworkHandler.instance
                    .sendToServer(new PacketValueConfig("Interface.DoublePatterns", String.valueOf(val)));
            } catch (final Throwable e) {
                AELog.debug(e);
            }
        } else if (btn == this.patternOptimization) {
            NetworkHandler.instance.sendToServer(new PacketConfigButton(Settings.PATTERN_OPTIMIZATION, backwards));
        } else if (btn == this.advancedBlockingMode) {
            NetworkHandler.instance
                .sendToServer(new PacketConfigButton(this.advancedBlockingMode.getSetting(), backwards));
        } else if (btn == this.lockCraftingMode) {
            NetworkHandler.instance.sendToServer(new PacketConfigButton(this.lockCraftingMode.getSetting(), backwards));
        } else if (btn == this.fuzzyMode) {
            NetworkHandler.instance.sendToServer(new PacketConfigButton(this.fuzzyMode.getSetting(), backwards));
        }
    }

    public void sendPageChangePacket(int page) {
        ScienceNotLeisure.network.sendToServer(new InterfacePagePacket(page));
    }

    @Override
    public String getBackground() {
        return "guis/super_interface.png";
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
