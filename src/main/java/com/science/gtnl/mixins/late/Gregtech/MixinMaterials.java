package com.science.gtnl.mixins.late.Gregtech;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import gregtech.loaders.materials.MaterialsInit1;

@Deprecated
@Mixin(value = MaterialsInit1.class, remap = false)
public class MixinMaterials {

    @ModifyArg(
        method = "load",
        at = @At(
            value = "INVOKE",
            target = "Lgregtech/api/enums/Materials;<init>(ILgregtech/api/enums/TextureSet;FIIIIIIILjava/lang/String;Ljava/lang/String;IIIIZZIIILgregtech/api/enums/Dyes;ILjava/util/List;)V",
            ordinal = 68),
        index = 5)
    private static int gtnl$modifyFlags(int flags) {
        return 1 | 2 | 4 | 8 | 16 | 32 | 64 | 128;
    }

    @ModifyConstant(method = "load", constant = @Constant(intValue = 16, ordinal = 54))
    private static int gtnl$modify16Materials(int constant) {
        return 1 | 2 | 4 | 8 | 16 | 32 | 64 | 128;
    }
}
