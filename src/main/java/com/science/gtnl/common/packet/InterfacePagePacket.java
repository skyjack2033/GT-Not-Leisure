package com.science.gtnl.common.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import com.science.gtnl.container.ContainerSuperInterface;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class InterfacePagePacket implements IMessage, IMessageHandler<InterfacePagePacket, IMessage> {

    private int page;

    public InterfacePagePacket() {}

    public InterfacePagePacket(int page) {
        this.page = page;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.page = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.page);
    }

    @Override
    public IMessage onMessage(InterfacePagePacket message, MessageContext ctx) {
        if (ctx.side.isServer()) {
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            Container container = player.openContainer;

            if (container instanceof ContainerSuperInterface csi) {
                csi.currentPage = message.page;
                csi.refreshSlots(player.inventory);
            }
        }
        return null;
    }
}
