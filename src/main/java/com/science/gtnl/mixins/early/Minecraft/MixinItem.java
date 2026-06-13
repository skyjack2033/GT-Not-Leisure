package com.science.gtnl.mixins.early.Minecraft;

import net.minecraft.item.Item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.loader.RecordLoader;

@Mixin(value = Item.class, remap = true)
public abstract class MixinItem {

    @Inject(
        method = "registerItems",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/ItemRecord;<init>(Ljava/lang/String;)V",
            ordinal = 11,
            shift = At.Shift.AFTER))
    private static void afterLastRecordInjection(CallbackInfo ci) {
        RecordLoader.registryForMinecraft();
    }
}
