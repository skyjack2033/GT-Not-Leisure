package com.science.gtnl.common.packet;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.science.gtnl.common.item.items.bauble.DraconicArmorProjectionState;
import com.science.gtnl.common.item.items.bauble.DraconicArmorProjectionType;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class DraconicArmorProjectionSyncPacket implements IMessage {

    private UUID playerId;
    private String projectionTypeId;

    public DraconicArmorProjectionSyncPacket() {}

    public DraconicArmorProjectionSyncPacket(EntityPlayer player, DraconicArmorProjectionType projectionType) {
        playerId = player.getUniqueID();
        projectionTypeId = projectionType == null ? "" : projectionType.getId();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        playerId = new UUID(buf.readLong(), buf.readLong());
        projectionTypeId = readString(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(playerId.getMostSignificantBits());
        buf.writeLong(playerId.getLeastSignificantBits());
        writeString(buf, projectionTypeId);
    }

    private static void writeString(ByteBuf buf, String value) {
        String safeValue = value == null ? "" : value;
        buf.writeInt(safeValue.length());
        for (int i = 0; i < safeValue.length(); i++) {
            buf.writeChar(safeValue.charAt(i));
        }
    }

    private static String readString(ByteBuf buf) {
        int length = buf.readInt();
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(buf.readChar());
        }
        return builder.toString();
    }

    public static class Handler implements IMessageHandler<DraconicArmorProjectionSyncPacket, IMessage> {

        @Override
        public IMessage onMessage(DraconicArmorProjectionSyncPacket message, MessageContext ctx) {
            World world = FMLClientHandler.instance()
                .getClient().theWorld;
            if (world == null) {
                return null;
            }

            EntityPlayer player = world.func_152378_a(message.playerId);
            if (player == null) {
                DraconicArmorProjectionState.clear(message.playerId);
                return null;
            }

            if (message.projectionTypeId.isEmpty()) {
                DraconicArmorProjectionState.clear(player);
                return null;
            }

            for (DraconicArmorProjectionType type : DraconicArmorProjectionType.values()) {
                if (type.getId()
                    .equals(message.projectionTypeId)) {
                    DraconicArmorProjectionState.set(player, type);
                    break;
                }
            }
            return null;
        }
    }
}
