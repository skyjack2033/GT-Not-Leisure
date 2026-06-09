package com.science.gtnl.common.gui.modularui;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.FluidSlotSyncHandler;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.slot.FluidSlot;
import com.science.gtnl.common.machine.hatch.DualOutputHatch;

import gregtech.api.modularui2.GTGuiTextures;
import gregtech.api.modularui2.GTGuis;

public class DualOutputHatchGui {

    private static final int[][] POSITIONS_FOUR = { { 70, 25 }, { 88, 25 }, { 70, 43 }, { 88, 43 } };
    private static final int[][] POSITIONS_NINE = { { 61, 16 }, { 79, 16 }, { 97, 16 }, { 61, 34 }, { 79, 34 },
        { 97, 34 }, { 61, 52 }, { 79, 52 }, { 97, 52 } };

    private final DualOutputHatch hatch;

    public DualOutputHatchGui(DualOutputHatch hatch) {
        this.hatch = hatch;
    }

    public ModularPanel build(PosGuiData guiData, PanelSyncManager syncManager, UISettings uiSettings) {
        ModularPanel panel = GTGuis.mteTemplatePanelBuilder(hatch, guiData, syncManager, uiSettings)
            .doesAddGregTechLogo(false)
            .doesBindPlayerInventory(false)
            .build();
        int[][] positions = hatch.getMaxType() == 4 ? POSITIONS_FOUR : POSITIONS_NINE;
        for (int i = 0; i < hatch.getFluidTanksForGui().length && i < positions.length; i++) {
            panel.child(createFluidSlot(i).pos(positions[i][0], positions[i][1]));
        }
        return panel;
    }

    private FluidSlot createFluidSlot(int index) {
        return new FluidSlot()
            .syncHandler(
                new FluidSlotSyncHandler(hatch.getFluidTanksForGui()[index]).canFillSlot(false)
                    .canDrainSlot(true))
            .background(GTGuiTextures.SLOT_FLUID_TANK);
    }
}
