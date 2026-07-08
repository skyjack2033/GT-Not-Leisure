package com.science.gtnl.mixins.late.randomComplement;

import appeng.client.gui.implementations.GuiCraftConfirm;
import appeng.client.gui.widgets.GuiAeButton;
import appeng.core.sync.AppEngPacket;
import appeng.core.sync.network.NetworkHandler;
import appeng.core.sync.packets.PacketSwitchGuis;
import com.llamalad7.mixinextras.expression.Definition;import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.client.GTNLInputHandler;
import com.science.gtnl.common.packet.ContainerRollBACK;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiCraftConfirm.class, remap = false)
public abstract class MixinGuiCraftConfirm {

    @Shadow
    private GuiAeButton start;

    @Shadow
    private GuiAeButton startWithFollow;

    @Definition(id = "instance", field = "Lappeng/core/sync/network/NetworkHandler;instance:Lappeng/core/sync/network/NetworkHandler;")
    @Definition(id = "sendToServer", method = "Lappeng/core/sync/network/NetworkHandler;sendToServer(Lappeng/core/sync/AppEngPacket;)V")
    @Definition(id = "PacketSwitchGuis", type = PacketSwitchGuis.class)
    @Expression("instance.sendToServer(new PacketSwitchGuis())")
    @WrapOperation(method = "actionPerformed", at = @At("MIXINEXTRAS:EXPRESSION"))
    public void onActionPerformed0(NetworkHandler instance, AppEngPacket message, Operation<Void> original) {
        GuiScreen oldGui;
        if ((oldGui = GTNLInputHandler.LAST_GUI_SCREEN) != null) {
            ScienceNotLeisure.network.sendToServer(new ContainerRollBACK());
            return;
        }
        original.call(instance, message);
    }

    @Inject(method = "actionPerformed", at = @At("HEAD"))
    public void onActionPerformed1(GuiButton btn, CallbackInfo ci) {
        if (btn == this.start || btn == this.startWithFollow) {
            GuiScreen oldGui;
            if ((oldGui = GTNLInputHandler.LAST_GUI_SCREEN) != null) {
                GTNLInputHandler.DELAY_METHOD = () -> Minecraft.getMinecraft()
                                                               .displayGuiScreen(oldGui);
            }
        }
    }

}
