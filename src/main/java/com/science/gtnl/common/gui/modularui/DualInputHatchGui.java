package com.science.gtnl.common.gui.modularui;

import com.cleanroommc.modularui.api.drawable.IDrawable;
import com.cleanroommc.modularui.drawable.UITexture;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.FluidSlotSyncHandler;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.layout.Grid;
import com.cleanroommc.modularui.widgets.slot.FluidSlot;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.science.gtnl.common.gui.GTNLMui2Textures;
import com.science.gtnl.common.machine.hatch.DualInputHatch;

import gregtech.api.modularui2.GTGuiTextures;
import gregtech.api.modularui2.GTGuis;
import gregtech.common.gui.modularui.hatch.base.MTEHatchBaseGui;
import gregtech.common.modularui2.widget.builder.ItemSlotGridBuilder;

public class DualInputHatchGui extends MTEHatchBaseGui<DualInputHatch> {

    public static final String ITEM_SLOT_GROUP = "gtnl_dual_input_hatch_item_inv";

    public DualInputHatchGui(DualInputHatch hatch) {
        super(hatch);
    }

    @Override
    public ModularPanel build(PosGuiData guiData, PanelSyncManager syncManager, UISettings uiSettings) {
        registerSyncValues(syncManager);
        ModularPanel panel = GTGuis.mteTemplatePanelBuilder(machine, guiData, syncManager, uiSettings)
            .setWidth(machine.getGUIWidth())
            .setHeight(machine.getGUIHeight())
            .doesAddGregTechLogo(false)
            .build();

        SlotLayout layout = SlotLayout.of(machine);
        addItemSlots(panel, syncManager, layout);
        addFluidSlots(panel, layout);
        panel.child(createLogo());
        return panel;
    }

    public void addItemSlots(ModularPanel panel, PanelSyncManager syncManager, SlotLayout layout) {
        Grid itemSlots = new ItemSlotGridBuilder(machine.inventoryHandler, syncManager)
            .size(layout.itemColumns, layout.itemRows)
            .slotGroupKey(ITEM_SLOT_GROUP)
            .itemSlotSupplier(() -> new ItemSlot().background(GTGuiTextures.SLOT_ITEM_STANDARD))
            .build();

        panel.child(itemSlots.pos(layout.centerX + 5, layout.centerY));
    }

    public void addFluidSlots(ModularPanel panel, SlotLayout layout) {
        for (int i = 0; i < machine.getFluidTanksForGui().length; i++) {
            panel.child(
                new FluidSlot().syncHandler(new FluidSlotSyncHandler(machine.getFluidTanksForGui()[i]))
                    .pos(layout.centerX + 18 * layout.itemColumns + 5, layout.centerY + i * 18));
        }
    }

    @Override
    protected IDrawable.DrawableWidget createLogo() {
        return new IDrawable.DrawableWidget(getLogoTexture()).size(SLOT_SIZE)
            .pos(machine.getCircuitSlotX() - 1, machine.getCircuitSlotY() + SLOT_SIZE);
    }

    @Override
    protected UITexture getLogoTexture() {
        return GTNLMui2Textures.PICTURE_GTNL_LOGO;
    }

    public record SlotLayout(int itemColumns, int itemRows, int centerX, int centerY) {

        public static SlotLayout of(DualInputHatch hatch) {
            int itemColumns = Math.max(1, hatch.mTier);
            int itemRows = Math.max(1, hatch.mTier);
            int totalWidth = 9 * itemColumns + 36;
            int totalHeight = 5 * itemRows + 81;
            return new SlotLayout(itemColumns, itemRows, (176 - totalWidth) / 2, (166 - totalHeight) / 2);
        }
    }
}
