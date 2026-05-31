package com.science.gtnl.common.packet;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.science.gtnl.common.item.items.bauble.DraconicArmorProjectionHitEffectState;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class DraconicArmorProjectionHitEffectPacket implements IMessage {

    private int playerId;
    private byte shieldPowerByte;

    public DraconicArmorProjectionHitEffectPacket() {}

    public DraconicArmorProjectionHitEffectPacket(EntityPlayer player, float shieldPower) {
        playerId = player.getEntityId();
        shieldPowerByte = (byte) (Math.max(0.0F, Math.min(1.0F, shieldPower)) * Byte.MAX_VALUE);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        playerId = buf.readInt();
        shieldPowerByte = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(playerId);
        buf.writeByte(shieldPowerByte);
    }

    public float getShieldPower() {
        return (float) shieldPowerByte / (float) Byte.MAX_VALUE;
    }

    public static class Handler implements IMessageHandler<DraconicArmorProjectionHitEffectPacket, IMessage> {

        @Override
        public IMessage onMessage(DraconicArmorProjectionHitEffectPacket message, MessageContext ctx) {
            World world = FMLClientHandler.instance()
                .getClient().theWorld;
            if (world == null) {
                return null;
            }

            Entity entity = world.getEntityByID(message.playerId);
            if (!(entity instanceof EntityPlayer player)) {
                return null;
            }

            DraconicArmorProjectionHitEffectState.trigger(player, message.getShieldPower());
            return null;
        }
    }
}
