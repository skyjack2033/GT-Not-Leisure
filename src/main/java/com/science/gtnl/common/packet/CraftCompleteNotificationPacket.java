package com.science.gtnl.common.packet;

import java.util.concurrent.TimeUnit;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.apache.commons.lang3.time.DurationFormatUtils;

import com.science.gtnl.utils.gui.NotificationTickHandler;

import appeng.core.localization.GuiText;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eu.usrv.yamcore.client.Notification;
import io.netty.buffer.ByteBuf;

public class CraftCompleteNotificationPacket
    implements IMessage, IMessageHandler<CraftCompleteNotificationPacket, IMessage> {

    private ItemStack stack;
    private long numsOfOutput;
    private long elapsedTime;

    public CraftCompleteNotificationPacket() {}

    public CraftCompleteNotificationPacket(ItemStack stack, long numsOfOutput, long elapsedTime) {
        this.stack = stack;
        this.numsOfOutput = numsOfOutput;
        this.elapsedTime = elapsedTime;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.stack = ByteBufUtils.readItemStack(buf);
        this.numsOfOutput = buf.readLong();
        this.elapsedTime = buf.readLong();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, this.stack);
        buf.writeLong(this.numsOfOutput);
        buf.writeLong(this.elapsedTime);
    }

    @Override
    public IMessage onMessage(CraftCompleteNotificationPacket message, MessageContext ctx) {
        if (ctx.side.isServer()) return null;
        apply(message.stack, message.numsOfOutput, message.elapsedTime);
        return null;
    }

    @SideOnly(Side.CLIENT)
    public void apply(ItemStack finalOutput, long numsOfOutput, long elapsedTime) {
        String elapsedTimeText = DurationFormatUtils.formatDuration(
            TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS),
            GuiText.ETAFormat.getLocal());
        NotificationTickHandler.guiNotification.queueNotification(
            new Notification(
                finalOutput,
                StatCollector.translateToLocal("text.CraftComplete.tooltip.0"),
                StatCollector.translateToLocalFormatted(
                    "text.CraftComplete.tooltip.1",
                    numsOfOutput,
                    finalOutput.getDisplayName(),
                    elapsedTimeText)));
    }
}
