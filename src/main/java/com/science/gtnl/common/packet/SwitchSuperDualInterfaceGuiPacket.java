package com.science.gtnl.common.packet;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

import com.science.gtnl.CommonProxy;
import com.science.gtnl.utils.enums.GuiType;

import appeng.container.AEBaseContainer;
import appeng.container.ContainerOpenContext;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class SwitchSuperDualInterfaceGuiPacket
    implements IMessage, IMessageHandler<SwitchSuperDualInterfaceGuiPacket, IMessage> {

    private int targetGuiId;

    public SwitchSuperDualInterfaceGuiPacket() {}

    public SwitchSuperDualInterfaceGuiPacket(GuiType targetGuiType) {
        this.targetGuiId = targetGuiType.ordinal();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.targetGuiId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.targetGuiId);
    }

    @Override
    public IMessage onMessage(SwitchSuperDualInterfaceGuiPacket message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().playerEntity;
        Container container = player.openContainer;
        if (!(container instanceof AEBaseContainer aeContainer)) {
            return null;
        }

        ContainerOpenContext openContext = aeContainer.getOpenContext();
        if (openContext == null) {
            return null;
        }

        TileEntity tile = openContext.getTile();
        if (tile == null) {
            return null;
        }

        CommonProxy.openGui(
            player,
            GuiType.values()[message.targetGuiId],
            openContext.getSide(),
            tile.getWorldObj(),
            tile.xCoord,
            tile.yCoord,
            tile.zCoord);
        return null;
    }
}
