package com.reavaritia.common.packet;

import net.minecraft.util.ChatAllowedCharacters;

import com.reavaritia.container.ContainerExtremeAnvil;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class ExtremeAnvilPacket implements IMessage, IMessageHandler<ExtremeAnvilPacket, IMessage> {

    private String itemName;

    public ExtremeAnvilPacket() {}

    public ExtremeAnvilPacket(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.itemName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, itemName == null ? "" : itemName);
    }

    @Override
    public IMessage onMessage(ExtremeAnvilPacket message, MessageContext ctx) {

        if (ctx.getServerHandler().playerEntity.openContainer instanceof ContainerExtremeAnvil container) {

            String name = message.itemName == null ? "" : message.itemName;

            name = ChatAllowedCharacters.filerAllowedCharacters(name);

            if (name.length() > 30) {
                name = name.substring(0, 30);
            }

            container.updateItemName(name);
        }

        return null;
    }
}
