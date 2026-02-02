package com.science.gtnl.api.mixinHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.science.gtnl.common.machine.hatch.ParallelControllerHatch;

import gregtech.api.interfaces.IHatchElement;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.IGTHatchAdder;
import tectech.thing.metaTileEntity.multi.base.TTMultiblockBase;

public class TTMultiblockBaseHelper {

    public enum CustomHatchElement implements IHatchElement<TTMultiblockBase> {

        ParallelCon(TTMultiblockBaseHelper::addParallelControllerToMachineList, ParallelControllerHatch.class) {

            @Override
            public long count(TTMultiblockBase tileEntity) {
                return ((ITTMultiblockBase) tileEntity).getParallelControllerHatches()
                    .size();
            }
        };

        public final List<Class<? extends IMetaTileEntity>> mteClasses;
        public final IGTHatchAdder<TTMultiblockBase> adder;

        @SafeVarargs
        CustomHatchElement(IGTHatchAdder<TTMultiblockBase> adder, Class<? extends IMetaTileEntity>... mteClasses) {
            this.mteClasses = Collections.unmodifiableList(Arrays.asList(mteClasses));
            this.adder = adder;
        }

        @Override
        public List<? extends Class<? extends IMetaTileEntity>> mteClasses() {
            return mteClasses;
        }

        public IGTHatchAdder<? super TTMultiblockBase> adder() {
            return adder;
        }
    }

    public static boolean addParallelControllerToMachineList(TTMultiblockBase multi, IGregTechTileEntity aTileEntity,
        int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        }
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) {
            return false;
        }
        if (aMetaTileEntity instanceof ParallelControllerHatch hatch) {
            hatch.updateTexture(aBaseCasingIndex);
            hatch.updateCraftingIcon(multi.getMachineCraftingIcon());
            return ((ITTMultiblockBase) multi).getParallelControllerHatches()
                .add(hatch);
        }
        return false;
    }
}
