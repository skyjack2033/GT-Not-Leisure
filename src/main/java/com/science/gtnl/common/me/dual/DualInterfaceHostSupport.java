package com.science.gtnl.common.me.dual;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import com.glodblock.github.common.item.ItemFluidPacket;
import com.glodblock.github.inventory.AEFluidInventory;
import com.glodblock.github.inventory.IAEFluidTank;
import com.glodblock.github.util.DualityFluidInterface;
import com.science.gtnl.api.mixinHelper.IDualityInterface;

import appeng.api.storage.data.IAEFluidStack;
import appeng.tile.inventory.AppEngInternalAEInventory;

public class DualInterfaceHostSupport {

    private final AppEngInternalAEInventory fluidConfig;
    private final DualityFluidInterface fluidDuality;

    public DualInterfaceHostSupport(AppEngInternalAEInventory fluidConfigHost, IDualInterfaceHost host) {
        fluidConfig = fluidConfigHost;
        fluidDuality = new DualityFluidInterface(((IDualityInterface) host.getInterfaceDuality()).getGridProxy(), host);
    }

    public AppEngInternalAEInventory getConfig() {
        return fluidConfig;
    }

    public DualityFluidInterface getDualityFluid() {
        return fluidDuality;
    }

    public AEFluidInventory getInternalFluid() {
        return fluidDuality.getInternalFluid();
    }

    public IInventory getInventoryByName(String name) {
        return fluidDuality.getInventoryByName(name);
    }

    public void setConfig(int slot, IAEFluidStack stack) {
        fluidConfig
            .setInventorySlotContents(slot, stack == null ? null : ItemFluidPacket.newStack(stack.getFluidStack()));
    }

    public void setFluidInv(int slot, IAEFluidStack stack) {
        fluidDuality.getTanks()
            .setFluidInSlot(slot, stack);
    }

    public void onFluidInventoryChanged(IAEFluidTank tank, int slot) {
        fluidDuality.onFluidInventoryChanged(tank, slot);
    }

    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return fluidDuality.fill(from, resource, doFill);
    }

    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return fluidDuality.drain(from, resource, doDrain);
    }

    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return fluidDuality.drain(from, maxDrain, doDrain);
    }

    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return fluidDuality.canFill(from, fluid);
    }

    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return fluidDuality.canDrain(from, fluid);
    }

    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return fluidDuality.getTankInfo(from);
    }
}
