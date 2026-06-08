package com.science.gtnl.common.gui.modularui.widget;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import com.gtnewhorizons.modularui.common.widget.FluidNameHolderWidget;

import gregtech.api.interfaces.metatileentity.IFluidLockableMui2;

public class FluidLockWidget extends FluidNameHolderWidget {

    public FluidLockWidget(IFluidLockableMui2 fluidLockable) {
        super(
            () -> fluidLockable.getLockedFluid() == null ? null
                : fluidLockable.getLockedFluid()
                    .getName(),
            name -> {
                Fluid fluid = name == null ? null : FluidRegistry.getFluid(name);
                if (fluid == null || fluidLockable.acceptsFluidLock(fluid)) {
                    fluidLockable.setLockedFluid(fluid);
                    fluidLockable.lockFluid(fluid != null);
                }
            });
    }
}
