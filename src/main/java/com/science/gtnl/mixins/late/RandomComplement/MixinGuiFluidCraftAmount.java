package com.science.gtnl.mixins.late.RandomComplement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.Container;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.glodblock.github.client.gui.GuiFluidCraftAmount;
import com.glodblock.github.client.gui.base.FCGuiAmount;
import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.client.GTNLInputHandler;
import com.science.gtnl.common.packet.ContainerRollBACK;

@Mixin(value = GuiFluidCraftAmount.class, remap = false)
public abstract class MixinGuiFluidCraftAmount extends FCGuiAmount {

    public MixinGuiFluidCraftAmount(Container container) {
        super(container);
    }

    @Inject(
        method = "actionPerformed",
        at = @At(
            value = "INVOKE",
            target = "Lcom/glodblock/github/network/wrapper/FCNetworkWrapper;sendToServer(Lcpw/mods/fml/common/network/simpleimpl/IMessage;)V",
            shift = At.Shift.AFTER))
    public void onActionPerformed(GuiButton btn, CallbackInfo ci) {
        if (isShiftKeyDown()) {
            GuiScreen oldGui;
            if ((oldGui = GTNLInputHandler.LAST_GUI_SCREEN) != null) {
                GTNLInputHandler.DELAY_METHOD = () -> Minecraft.getMinecraft()
                    .displayGuiScreen(oldGui);
                ScienceNotLeisure.network.sendToServer(new ContainerRollBACK());
            }
        }
    }
}
