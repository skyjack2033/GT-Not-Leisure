package com.science.gtnl.mixins.late.NotEnoughItems;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import codechicken.nei.api.ItemFilter;
import codechicken.nei.util.ItemStackFilterParser;

@Mixin(value = ItemStackFilterParser.class, remap = false)
public class MixinItemStackFilterParser {

    @Inject(method = "getTagFilter", at = @At("HEAD"), cancellable = true)
    private static void injectTagExistsRule(String rule, CallbackInfoReturnable<ItemFilter> cir) {
        if ("*".equals(rule) || rule.isEmpty()) {
            cir.setReturnValue(stack -> stack != null && stack.getTagCompound() != null);
        }
    }
}
