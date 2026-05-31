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

public class SwitchSuperInterfaceViewPacket
    implements IMessage, IMessageHandler<SwitchSuperInterfaceViewPacket, IMessage> {

    private int targetGuiId;
    private int sideOrdinal;
    private int page;

    public SwitchSuperInterfaceViewPacket() {}

    public SwitchSuperInterfaceViewPacket(GuiType targetGuiType, ForgeDirection side, int page) {
        targetGuiId = targetGuiType.ordinal();
        sideOrdinal = side.ordinal();
        this.page = page;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        targetGuiId = buf.readInt();
        sideOrdinal = buf.readInt();
        page = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(targetGuiId);
        buf.writeInt(sideOrdinal);
        buf.writeInt(page);
    }

    @Override
    public IMessage onMessage(SwitchSuperInterfaceViewPacket message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().playerEntity;
        Container container = player.openContainer;
        if (!(container instanceof AEBaseContainer aeContainer)) {
            return null;
        }

        ContainerOpenContext context = aeContainer.getOpenContext();
        if (context == null) {
            return null;
        }

        TileEntity tile = context.getTile();
        if (tile == null) {
            return null;
        }

        CommonProxy.openGui(
            player,
            GuiType.values()[message.targetGuiId],
            ForgeDirection.getOrientation(message.sideOrdinal),
            tile.getWorldObj(),
            tile.xCoord,
            tile.yCoord,
            tile.zCoord);
        if (player.openContainer instanceof com.science.gtnl.container.ContainerSuperInterface superInterfaceContainer) {
            superInterfaceContainer.setCurrentPage(message.page);
        }
        return null;
    }
}
