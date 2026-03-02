package com.science.gtnl.mixins.late.Gregtech;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import gregtech.api.enums.MaterialBuilder;
import gregtech.api.enums.MaterialsUEVplus;

@Deprecated
@Mixin(value = MaterialsUEVplus.class, remap = false)
public class MixinMaterialsUEVPlus {

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 1 | 2 | 64 | 128 // , ordinal = 0
    ))
    private static int gtnl$modifyMaterials(int constant) {
        return 1 | 2 | 4 | 8 | 16 | 32 | 64 | 128;
    }

    @Redirect(
        method = "<clinit>",
        at = @At(
            value = "INVOKE",
            target = "Lgregtech/api/enums/MaterialBuilder;addFluid()Lgregtech/api/enums/MaterialBuilder;",
            ordinal = 3))
    private static MaterialBuilder gtnl$redirect(MaterialBuilder builder) {
        return builder.addOreItems();
    }
}
