package com.science.gtnl.mixins.late.RandomComplement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.Container;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.client.GTNLInputHandler;
import com.science.gtnl.common.packet.ContainerRollBACK;

import appeng.client.gui.AEBaseGui;
import appeng.client.gui.implementations.GuiAmount;
import appeng.core.sync.AppEngPacket;
import appeng.core.sync.network.NetworkHandler;

@Mixin(value = GuiAmount.class, remap = false)
public abstract class MixinGuiAmount extends AEBaseGui {

    public MixinGuiAmount(Container container) {
        super(container);
    }

    @Redirect(
        method = "actionPerformed",
        at = @At(
            value = "INVOKE",
            target = "Lappeng/core/sync/network/NetworkHandler;sendToServer(Lappeng/core/sync/AppEngPacket;)V"))
    public void onActionPerformed(NetworkHandler instance, AppEngPacket message) {
        GuiScreen oldGui;
        if ((oldGui = GTNLInputHandler.LAST_GUI_SCREEN) != null) {
            GTNLInputHandler.DELAY_METHOD = () -> Minecraft.getMinecraft()
                .displayGuiScreen(oldGui);
            ScienceNotLeisure.network.sendToServer(new ContainerRollBACK());
        } else {
            instance.sendToServer(message);
        }
    }
}
