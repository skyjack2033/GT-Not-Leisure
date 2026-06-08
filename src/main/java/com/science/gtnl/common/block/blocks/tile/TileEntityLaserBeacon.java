package com.science.gtnl.common.block.blocks.tile;

import net.minecraft.nbt.NBTTagCompound;

import gregtech.common.tileentities.render.RenderingTileEntityLaser;
import lombok.Getter;

@Getter
public class TileEntityLaserBeacon extends RenderingTileEntityLaser {

    private double range;

    public TileEntityLaserBeacon() {
        super();
    }

    public void setRange(double r) {
        this.range = r;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setDouble("range", range);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        range = compound.getDouble("range");
    }
}
