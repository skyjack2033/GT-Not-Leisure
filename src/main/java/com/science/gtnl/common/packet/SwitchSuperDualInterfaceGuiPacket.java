package com.science.gtnl.common.packet;

import com.science.gtnl.common.packet.base.ServerboundPacket;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

import com.science.gtnl.CommonProxy;
import com.science.gtnl.utils.enums.GuiType;

import appeng.container.AEBaseContainer;
import appeng.container.ContainerOpenContext;
import io.netty.buffer.ByteBuf;

public class SwitchSuperDualInterfaceGuiPacket
    extends ServerboundPacket {

    private int targetGuiId;

    public SwitchSuperDualInterfaceGuiPacket() {}

    public SwitchSuperDualInterfaceGuiPacket(GuiType targetGuiType) {
        this.targetGuiId = targetGuiType.ordinal();
    }

    @Override
    protected void read(ByteBuf buf) {
        this.targetGuiId = buf.readInt();
    }

    @Override
    protected void write(ByteBuf buf) {
        buf.writeInt(this.targetGuiId);
    }

    @Override
    public void handleServer(EntityPlayerMP player) {
        Container container = player.openContainer;
        if (!(container instanceof AEBaseContainer aeContainer)) {
            return;
        }

        ContainerOpenContext openContext = aeContainer.getOpenContext();
        if (openContext == null) {
            return;
        }

        TileEntity tile = openContext.getTile();
        if (tile == null) {
            return;
        }

        CommonProxy.openGui(
            player,
            GuiType.values()[targetGuiId],
            openContext.getSide(),
            tile.getWorldObj(),
            tile.xCoord,
            tile.yCoord,
            tile.zCoord);
    }
}
