package com.science.gtnl.common.packet;

import static com.science.gtnl.utils.event.SubscribeEventUtils.CIRCUIT_NANITES_DATA_LOAD;

import com.science.gtnl.loader.RecipeLoader;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class SyncCircuitNanitesPacket implements IMessage, IMessageHandler<SyncCircuitNanitesPacket, IMessage> {

    public long worldSeed;

    public SyncCircuitNanitesPacket() {}

    public SyncCircuitNanitesPacket(long worldSeed) {
        this.worldSeed = worldSeed;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        worldSeed = buf.readLong();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(worldSeed);
    }

    @Override
    public IMessage onMessage(SyncCircuitNanitesPacket message, MessageContext ctx) {
        if (!CIRCUIT_NANITES_DATA_LOAD) {
            RecipeLoader.loadCircuitNanitesData(message.worldSeed);
        }
        CIRCUIT_NANITES_DATA_LOAD = true;
        return null;
    }

}
