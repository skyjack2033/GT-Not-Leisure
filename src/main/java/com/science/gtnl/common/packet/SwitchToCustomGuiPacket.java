package com.science.gtnl.common.packet;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.science.gtnl.CommonProxy;
import com.science.gtnl.utils.enums.GuiType;

import appeng.container.AEBaseContainer;
import appeng.container.ContainerOpenContext;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class SwitchToCustomGuiPacket implements IMessage, IMessageHandler<SwitchToCustomGuiPacket, IMessage> {

    private int guiID;
    private int sideOrdinal;

    public SwitchToCustomGuiPacket() {}

    public SwitchToCustomGuiPacket(GuiType guiType, ForgeDirection side) {
        this.guiID = guiType.ordinal();
        this.sideOrdinal = side.ordinal();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.guiID = buf.readInt();
        this.sideOrdinal = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.guiID);
        buf.writeInt(this.sideOrdinal);
    }

    @Override
    public IMessage onMessage(SwitchToCustomGuiPacket message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().playerEntity;

        Container c = player.openContainer;

        if (c instanceof AEBaseContainer bc) {
            ContainerOpenContext context = bc.getOpenContext();
            if (context != null) {
                TileEntity te = context.getTile();
                if (te != null) {
                    CommonProxy.openGui(
                        player,
                        GuiType.values()[message.guiID],
                        ForgeDirection.getOrientation(message.sideOrdinal),
                        te.getWorldObj(),
                        te.xCoord,
                        te.yCoord,
                        te.zCoord);
                }
            }
        }

        return null;
    }
}
