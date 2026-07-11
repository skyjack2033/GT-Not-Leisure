package com.science.gtnl.mixins.late.appliedEnergistics;

import java.util.LinkedList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "appeng.me.cluster.implementations.CraftingCPUCluster$TaskProgress", remap = false)
public interface AccessorTaskProgress {

    @Accessor
    long getValue();

    @Accessor
    void setValue(long value);

    @Accessor("diagnosticSessionCrafts")
    LinkedList<?> getDiagnosticSessionCrafts();
}
