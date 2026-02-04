package com.science.gtnl.api.mixinHelper;

import tectech.thing.metaTileEntity.multi.godforge.MTEForgeOfGods;

public interface IFOGModule {

    MTEForgeOfGods getMaster();

    void setMaster(MTEForgeOfGods master);
}
