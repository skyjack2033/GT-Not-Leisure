package com.science.gtnl.api.mixinHelper;

import appeng.api.storage.data.IAEStack;
import gregtech.common.tileentities.machines.outputme.base.MTEHatchOutputMEBase;
import gregtech.common.tileentities.machines.outputme.filter.MEFilterBase;

public interface IOutputMEProviderTransfer<T extends IAEStack<T>, F extends MEFilterBase<T, ?, I>, I> {

    boolean gtnl$transferCacheTo(MTEHatchOutputMEBase<T, F, I> targetProvider);
}
