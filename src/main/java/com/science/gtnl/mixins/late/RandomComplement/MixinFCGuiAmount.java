package com.science.gtnl.mixins.late.RandomComplement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.glodblock.github.client.gui.base.FCGuiAmount;
import com.glodblock.github.inventory.InventoryHandler;
import com.glodblock.github.inventory.gui.GuiType;
import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.client.GTNLInputHandler;
import com.science.gtnl.common.packet.ContainerRollBACK;

@Mixin(value = FCGuiAmount.class, remap = false)
public class MixinFCGuiAmount {

    @Redirect(
        method = "actionPerformed",
        at = @At(
            value = "INVOKE",
            target = "Lcom/glodblock/github/inventory/InventoryHandler;switchGui(Lcom/glodblock/github/inventory/gui/GuiType;)V"))
    public void onActionPerformed(GuiType guiType) {
        GuiScreen oldGui;
        if ((oldGui = GTNLInputHandler.LAST_GUI_SCREEN) != null) {
            GTNLInputHandler.DELAY_METHOD = () -> Minecraft.getMinecraft()
                .displayGuiScreen(oldGui);
            ScienceNotLeisure.network.sendToServer(new ContainerRollBACK());
        } else {
            InventoryHandler.switchGui(guiType);
        }
    }
}
