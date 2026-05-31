package com.science.gtnl.common.packet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.science.gtnl.client.gui.GuiSuperDualInterfaceFluid;

import appeng.api.storage.data.IAEFluidStack;
import appeng.util.item.AEFluidStack;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class SuperDualInterfaceFluidSyncPacket
    implements IMessage, IMessageHandler<SuperDualInterfaceFluidSyncPacket, IMessage> {

    private Map<Integer, IAEFluidStack> stacks = new HashMap<>();

    public SuperDualInterfaceFluidSyncPacket() {}

    public SuperDualInterfaceFluidSyncPacket(Map<Integer, IAEFluidStack> stacks) {
        this.stacks = stacks;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            int key = buf.readInt();
            boolean present = buf.readBoolean();
            try {
                stacks.put(key, present ? AEFluidStack.loadFluidStackFromPacket(buf) : null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(stacks.size());
        for (Map.Entry<Integer, IAEFluidStack> entry : stacks.entrySet()) {
            buf.writeInt(entry.getKey());
            if (entry.getValue() == null) {
                buf.writeBoolean(false);
            } else {
                buf.writeBoolean(true);
                try {
                    entry.getValue()
                        .writeToPacket(buf);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public IMessage onMessage(SuperDualInterfaceFluidSyncPacket message, MessageContext ctx) {
        if (net.minecraft.client.Minecraft.getMinecraft().currentScreen instanceof GuiSuperDualInterfaceFluid gui) {
            for (Map.Entry<Integer, IAEFluidStack> entry : message.stacks.entrySet()) {
                gui.update(entry.getKey(), entry.getValue());
            }
        }
        return null;
    }
}
