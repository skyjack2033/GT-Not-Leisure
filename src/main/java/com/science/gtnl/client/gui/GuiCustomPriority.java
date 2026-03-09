package com.science.gtnl.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.common.util.ForgeDirection;

import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.common.block.blocks.tile.TileEntitySuperInterface;
import com.science.gtnl.common.item.PartSuperInterface;
import com.science.gtnl.common.packet.SwitchToCustomGuiPacket;
import com.science.gtnl.utils.enums.GTNLItemList;
import com.science.gtnl.utils.enums.GuiType;

import appeng.client.gui.AEBaseGui;
import appeng.client.gui.implementations.GuiPriority;
import appeng.client.gui.widgets.GuiTabButton;
import appeng.container.AEBaseContainer;
import appeng.helpers.IPriorityHost;

public class GuiCustomPriority extends GuiPriority {

    private final GuiType customReturnGui;

    public GuiCustomPriority(InventoryPlayer inventoryPlayer, IPriorityHost te, GuiType returnGui) {
        super(inventoryPlayer, te);
        this.customReturnGui = returnGui;
    }

    @Override
    public void initGui() {
        super.initGui();
        if (this.originalGui == null && this.customReturnGui != null && myIcon != null) {
            this.buttonList.add(
                this.originalGuiBtn = new GuiTabButton(
                    this.guiLeft + 154,
                    this.guiTop,
                    this.myIcon,
                    this.myIcon.getDisplayName(),
                    itemRender));
        }
    }

    @Override
    public void setOriginGUI(Object target) {
        super.setOriginGUI(target);
        if (target instanceof TileEntitySuperInterface) {
            this.myIcon = GTNLItemList.SuperInterface.get(1);
            this.originalGui = null;
        } else if (target instanceof PartSuperInterface) {
            this.myIcon = GTNLItemList.PartSuperInterface.get(1);
            this.originalGui = null;
        }
    }

    @Override
    public void actionPerformed(GuiButton btn) {
        if (btn == this.originalGuiBtn && this.originalGui == null && this.customReturnGui != null) {
            AEBaseGui.setSwitchingGuis(true);

            ForgeDirection side = ForgeDirection.UNKNOWN;
            if (this.inventorySlots instanceof AEBaseContainer bc) {
                if (bc.getOpenContext() != null) {
                    side = bc.getOpenContext()
                        .getSide();
                }
            }

            ScienceNotLeisure.network.sendToServer(new SwitchToCustomGuiPacket(this.customReturnGui, side));
            return;
        }
        super.actionPerformed(btn);
    }
}
