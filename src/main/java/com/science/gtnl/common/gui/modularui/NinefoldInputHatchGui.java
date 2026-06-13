package com.science.gtnl.common.gui.modularui;

import com.cleanroommc.modularui.api.drawable.IDrawable;
import com.cleanroommc.modularui.drawable.UITexture;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.FluidSlotSyncHandler;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.slot.FluidSlot;
import com.science.gtnl.common.gui.GTNLMui2Textures;
import com.science.gtnl.common.machine.hatch.NinefoldInputHatch;

import gregtech.api.modularui2.GTGuiTextures;
import gregtech.api.modularui2.GTGuis;
import gregtech.common.gui.modularui.hatch.base.MTEHatchBaseGui;

public class NinefoldInputHatchGui extends MTEHatchBaseGui<NinefoldInputHatch> {

    private static final int[][] POSITIONS = { { 61, 16 }, { 79, 16 }, { 97, 16 }, { 61, 34 }, { 79, 34 }, { 97, 34 },
        { 61, 52 }, { 79, 52 }, { 97, 52 } };

    public NinefoldInputHatchGui(NinefoldInputHatch hatch) {
        super(hatch);
    }

    @Override
    public ModularPanel build(PosGuiData guiData, PanelSyncManager syncManager, UISettings uiSettings) {
        ModularPanel panel = GTGuis.mteTemplatePanelBuilder(machine, guiData, syncManager, uiSettings)
            .doesBindPlayerInventory(false)
            .doesAddGregTechLogo(false)
            .build();
        for (int i = 0; i < machine.getFluidTanksForGui().length && i < POSITIONS.length; i++) {
            panel.child(createFluidSlot(i).pos(POSITIONS[i][0], POSITIONS[i][1]));
        }
        return panel.child(createLogo());
    }

    @Override
    protected IDrawable.DrawableWidget createLogo() {
        return new IDrawable.DrawableWidget(getLogoTexture()).size(SLOT_SIZE);
    }

    @Override
    protected UITexture getLogoTexture() {
        return GTNLMui2Textures.PICTURE_GTNL_LOGO;
    }

    private FluidSlot createFluidSlot(int index) {
        return new FluidSlot().syncHandler(new FluidSlotSyncHandler(machine.getFluidTanksForGui()[index]))
            .background(GTGuiTextures.SLOT_FLUID_TANK);
    }
}
