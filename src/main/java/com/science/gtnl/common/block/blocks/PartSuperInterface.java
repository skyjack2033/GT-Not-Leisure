package com.science.gtnl.common.block.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

import com.science.gtnl.CommonProxy;
import com.science.gtnl.api.mixinHelper.IDualityInterface;
import com.science.gtnl.mixins.late.AppliedEnergistics.AccessorPartInterface;
import com.science.gtnl.utils.enums.GuiType;

import appeng.api.storage.data.IAEItemStack;
import appeng.parts.automation.StackUpgradeInventory;
import appeng.parts.misc.PartInterface;
import appeng.tile.inventory.AppEngInternalAEInventory;
import appeng.tile.inventory.AppEngInternalInventory;
import appeng.util.inv.WrapperInvSlot;

public class PartSuperInterface extends PartInterface {

    public int configSlots = 18;
    public int storageSlots = 18;
    public int patternSlots = 108;
    public int upgradeSlots = 15;

    public PartSuperInterface(ItemStack is) {
        super(is);
        var duality = (IDualityInterface) ((AccessorPartInterface) this).getDuality();
        duality.setConfigSlots(18);
        duality.setStorageSlots(18);
        duality.setPatternSlots(108);
        duality.setUpgradeSlots(15);
        duality.gtnl$setConfig(new AppEngInternalAEInventory(this, configSlots));
        duality.gtnl$setStorage(new AppEngInternalInventory(this, storageSlots));
        duality.gtnl$setPatterns(new AppEngInternalInventory(this, patternSlots));
        duality.gtnl$setSlotInv(new WrapperInvSlot(duality.gtnl$getStorage()));
        duality.gtnl$setUpgrades(
            new StackUpgradeInventory(
                duality.gtnl$getGridProxy()
                    .getMachineRepresentation(),
                this,
                upgradeSlots));
        duality.gtnl$setRequireWork(new IAEItemStack[storageSlots]);
        duality.gtnl$setHasFuzzyConfig(new boolean[configSlots]);
    }

    @Override
    public boolean onPartActivate(final EntityPlayer p, final Vec3 pos) {
        if (p.isSneaking()) {
            return false;
        }
        CommonProxy.openGui(
            p,
            GuiType.SuperInterfaceGUI,
            this.getSide(),
            this.getHost()
                .getTile());
        return true;
    }
}
