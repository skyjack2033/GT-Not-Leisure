package com.science.gtnl.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.input.Mouse;

import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.common.block.blocks.tile.TileEntitySuperDualInterface;
import com.science.gtnl.common.packet.SwitchSuperInterfaceViewPacket;
import com.science.gtnl.common.part.PartSuperDualInterface;
import com.science.gtnl.container.ContainerSuperDualInterface;
import com.science.gtnl.utils.enums.GTNLItemList;
import com.science.gtnl.utils.enums.GuiType;

import appeng.api.config.Settings;
import appeng.api.config.SidelessMode;
import appeng.client.gui.AEBaseGui;
import appeng.client.gui.widgets.GuiImgButton;
import appeng.client.gui.widgets.GuiTabButton;
import appeng.core.sync.network.NetworkHandler;
import appeng.core.sync.packets.PacketConfigButton;
import appeng.helpers.IInterfaceHost;
import appeng.parts.AEBasePart;

public class GuiSuperDualInterface extends GuiSuperInterface {

    private GuiImgButton sidelessMode;

    public GuiSuperDualInterface(InventoryPlayer inventoryPlayer, IInterfaceHost host) {
        super(new ContainerSuperDualInterface(inventoryPlayer, host));
        this.ySize = 211;
    }

    @Override
    public void addButtons() {
        super.addButtons();
        if (switcher != null) {
            buttonList.remove(switcher);
        }

        ItemStack switchStack = host instanceof PartSuperDualInterface ? GTNLItemList.PartSuperInterface.get(1)
            : GTNLItemList.SuperInterface.get(1);
        switcher = new GuiTabButton(
            guiLeft + 132,
            guiTop,
            switchStack,
            StatCollector.translateToLocal("text.SuperDualInterface.tooltip.switch"),
            itemRender);
        buttonList.add(switcher);

        if (host instanceof TileEntitySuperDualInterface) {
            sidelessMode = new GuiImgButton(guiLeft - 18, guiTop + 116, Settings.SIDELESS_MODE, SidelessMode.SIDELESS);
            buttonList.add(sidelessMode);
        }
    }

    @Override
    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        ContainerSuperDualInterface container = (ContainerSuperDualInterface) cvb;
        if (BlockMode != null) BlockMode.set(container.bMode);
        if (SmartBlockMode != null) SmartBlockMode.set(container.sbMode);
        if (interfaceMode != null) interfaceMode.setState(container.iTermMode == appeng.api.config.YesNo.YES);
        if (insertionMode != null) insertionMode.set(container.insertionMode);
        if (patternOptimization != null)
            patternOptimization.setState(container.patternOptimization == appeng.api.config.YesNo.YES);
        if (advancedBlockingMode != null) advancedBlockingMode.set(container.advancedBlockingMode);
        if (lockCraftingMode != null) lockCraftingMode.set(container.lockCraftingMode);
        if (fuzzyMode != null) fuzzyMode.set(container.fuzzyMode);
        if (sidelessMode != null) {
            sidelessMode.set(container.sidelessMode);
        }
        if (doublePatterns != null) {
            doublePatterns.enabled = container.isAllowedToMultiplyPatterns;
            doublePatterns.setTooltip(
                appeng.core.localization.ButtonToolTips.DoublePatterns.getLocal() + "\n"
                    + (doublePatterns.enabled ? appeng.core.localization.ButtonToolTips.DoublePatternsHint.getLocal()
                        : appeng.core.localization.ButtonToolTips.OptimizePatternsNoReq.getLocal()));
        }
        fontRendererObj.drawString(
            getGuiDisplayName(StatCollector.translateToLocal("container.SuperDualInterface")),
            8,
            6,
            4210752);
        String pageLabel = StatCollector
            .translateToLocalFormatted("text.SuperInterface.page", container.currentPage + 1, container.getMaxPages());
        fontRendererObj.drawString(pageLabel, 110, 6, 4210752);
        prevPage.enabled = container.currentPage > 0;
        nextPage.enabled = container.currentPage < container.getMaxPages() - 1;
    }

    @Override
    public String getBackground() {
        return super.getBackground();
    }

    @Override
    public void actionPerformed(GuiButton btn) {
        super.actionPerformed(btn);
        boolean backwards = Mouse.isButtonDown(1);
        if (btn == switcher) {
            ForgeDirection side = ForgeDirection.UNKNOWN;
            if (bc instanceof AEBasePart part) {
                side = part.getSide();
            }
            AEBaseGui.setSwitchingGuis(true);
            ScienceNotLeisure.network.sendToServer(
                new SwitchSuperInterfaceViewPacket(
                    GuiType.SuperInterfaceGUI,
                    side,
                    ((ContainerSuperDualInterface) cvb).currentPage));
        } else if (btn == sidelessMode) {
            NetworkHandler.instance.sendToServer(new PacketConfigButton(Settings.SIDELESS_MODE, backwards));
        }
    }

    @Override
    public void handleButtonVisibility() {
        super.handleButtonVisibility();
        if (switcher != null) {
            switcher.visible = true;
        }
        if (sidelessMode != null) {
            sidelessMode.setVisibility(true);
        }
    }
}
