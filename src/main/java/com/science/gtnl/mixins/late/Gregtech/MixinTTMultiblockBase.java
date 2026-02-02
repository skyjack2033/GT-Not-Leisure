package com.science.gtnl.mixins.late.Gregtech;

import java.util.ArrayList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import com.science.gtnl.api.mixinHelper.ITTMultiblockBase;
import com.science.gtnl.api.mixinHelper.TTMultiblockBaseHelper;
import com.science.gtnl.common.machine.hatch.ParallelControllerHatch;

import gregtech.api.interfaces.IHatchElement;
import tectech.thing.metaTileEntity.multi.base.TTMultiblockBase;

@Mixin(value = TTMultiblockBase.class, remap = false)
public class MixinTTMultiblockBase implements ITTMultiblockBase {

    @Unique
    public ArrayList<ParallelControllerHatch> mParallelControllerHatches = new ArrayList<>();

    public ArrayList<ParallelControllerHatch> getParallelControllerHatches() {
        return mParallelControllerHatches;
    }

    @Inject(method = "clearHatches_EM", at = @At(value = "HEAD"))
    public void injectClearHatches(CallbackInfo ci) {
        mParallelControllerHatches.clear();
    }

    @ModifyArgs(
        method = "classicHatches",
        at = @At(
            value = "INVOKE",
            target = "Lgregtech/api/util/HatchElementBuilder;atLeast([Lgregtech/api/interfaces/IHatchElement;)Lgregtech/api/util/HatchElementBuilder;"))
    private static void addExtraHatch(Args args) {
        IHatchElement<?>[] original = args.get(0);
        IHatchElement<?>[] modified = new IHatchElement<?>[original.length + 1];

        modified[0] = TTMultiblockBaseHelper.CustomHatchElement.ParallelCon;

        System.arraycopy(original, 0, modified, 1, original.length);
        args.set(0, modified);
    }

}
