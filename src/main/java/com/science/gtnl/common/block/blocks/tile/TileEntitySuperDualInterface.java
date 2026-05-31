package com.science.gtnl.common.block.blocks.tile;

import net.minecraft.item.ItemStack;

import com.glodblock.github.inventory.AEFluidInventory;
import com.glodblock.github.inventory.IAEFluidTank;
import com.glodblock.github.util.DualityFluidInterface;
import com.science.gtnl.api.ICustomGui;
import com.science.gtnl.api.mixinHelper.IDualityInterface;
import com.science.gtnl.common.me.dual.DualInterfaceHostSupport;
import com.science.gtnl.common.me.dual.IDualInterfaceHost;
import com.science.gtnl.mixins.late.AppliedEnergistics.AccessorTileInterface;
import com.science.gtnl.utils.enums.GTNLItemList;

import appeng.api.config.Settings;
import appeng.api.config.SidelessMode;
import appeng.api.networking.IGridNode;
import appeng.api.networking.ticking.TickRateModulation;
import appeng.api.networking.ticking.TickingRequest;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IAEItemStack;
import appeng.parts.automation.StackUpgradeInventory;
import appeng.tile.inventory.AppEngInternalAEInventory;
import appeng.tile.inventory.AppEngInternalInventory;
import appeng.tile.misc.TileInterface;
import appeng.util.inv.WrapperInvSlot;

public class TileEntitySuperDualInterface extends TileInterface implements ICustomGui, IDualInterfaceHost {

    public int configSlots = 27;
    public int storageSlots = 27;
    public int patternSlots = 108;
    public int upgradeSlots = 4;

    private final DualInterfaceHostSupport dualHostSupport;

    public TileEntitySuperDualInterface() {
        super();
        var duality = (IDualityInterface) ((AccessorTileInterface) this).getDuality();
        duality.setConfigSlots(configSlots);
        duality.setStorageSlots(storageSlots);
        duality.setPatternSlots(patternSlots);
        duality.setUpgradeSlots(upgradeSlots);
        duality.setConfig(new AppEngInternalAEInventory(this, configSlots));
        duality.setStorage(new AppEngInternalInventory(this, storageSlots));
        duality.setPatterns(new AppEngInternalInventory(this, patternSlots));
        duality.setSlotInv(new WrapperInvSlot(duality.getStorage()));
        duality.setUpgrades(
            new StackUpgradeInventory(
                duality.getGridProxy()
                    .getMachineRepresentation(),
                this,
                upgradeSlots));
        duality.setRequireWork(new IAEItemStack[storageSlots]);
        duality.setHasFuzzyConfig(new boolean[configSlots]);
        var fluidConfig = new AppEngInternalAEInventory(this, DualityFluidInterface.NUMBER_OF_TANKS);
        dualHostSupport = new DualInterfaceHostSupport(fluidConfig, this);
        getInterfaceDuality().getConfigManager()
            .registerSetting(Settings.SIDELESS_MODE, SidelessMode.SIDELESS);
    }

    @Override
    public int rows() {
        return 12;
    }

    @Override
    public ItemStack getOriginGuiIcon() {
        return GTNLItemList.SuperDualInterface.get(1);
    }

    @Override
    public DualityFluidInterface getDualityFluid() {
        return dualHostSupport.getDualityFluid();
    }

    @Override
    public AEFluidInventory getInternalFluid() {
        return dualHostSupport.getInternalFluid();
    }

    @Override
    public AppEngInternalAEInventory getConfig() {
        return dualHostSupport.getConfig();
    }

    @Override
    public void setConfig(int slot, IAEFluidStack stack) {
        dualHostSupport.setConfig(slot, stack);
    }

    @Override
    public void setFluidInv(int slot, IAEFluidStack stack) {
        dualHostSupport.setFluidInv(slot, stack);
    }

    @Override
    public void onFluidInventoryChanged(IAEFluidTank tank, int slot) {
        dualHostSupport.onFluidInventoryChanged(tank, slot);
    }

    @Override
    public TickingRequest getTickingRequest(IGridNode node) {
        return getDualityFluid().getTickingRequest(node);
    }

    @Override
    public TickRateModulation tickingRequest(IGridNode node, int ticksSinceLastCall) {
        return getDualityFluid().tickingRequest(node, ticksSinceLastCall);
    }

    @Override
    public int fill(net.minecraftforge.common.util.ForgeDirection from, net.minecraftforge.fluids.FluidStack resource,
        boolean doFill) {
        return dualHostSupport.fill(from, resource, doFill);
    }

    @Override
    public net.minecraftforge.fluids.FluidStack drain(net.minecraftforge.common.util.ForgeDirection from,
        net.minecraftforge.fluids.FluidStack resource, boolean doDrain) {
        return dualHostSupport.drain(from, resource, doDrain);
    }

    @Override
    public net.minecraftforge.fluids.FluidStack drain(net.minecraftforge.common.util.ForgeDirection from, int maxDrain,
        boolean doDrain) {
        return dualHostSupport.drain(from, maxDrain, doDrain);
    }

    @Override
    public boolean canFill(net.minecraftforge.common.util.ForgeDirection from, net.minecraftforge.fluids.Fluid fluid) {
        return dualHostSupport.canFill(from, fluid);
    }

    @Override
    public boolean canDrain(net.minecraftforge.common.util.ForgeDirection from, net.minecraftforge.fluids.Fluid fluid) {
        return dualHostSupport.canDrain(from, fluid);
    }

    @Override
    public net.minecraftforge.fluids.FluidTankInfo[] getTankInfo(net.minecraftforge.common.util.ForgeDirection from) {
        return dualHostSupport.getTankInfo(from);
    }
}
