package com.science.gtnl.common.gui;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.cleanroommc.modularui.api.MCHelper;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.factory.PlayerInventoryGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.science.gtnl.common.item.items.CircuitIntegratedPlus;

import gregtech.api.enums.GTValues;
import gregtech.api.modularui2.GTGuiTextures;
import gregtech.api.modularui2.GTGuis;
import gregtech.api.net.GTPacketUpdateItem;
import gregtech.common.modularui2.factory.SelectItemGuiBuilder;

public class CircuitIntegratedPlusGui {

    private final PlayerInventoryGuiData data;
    private final ItemStack configurator;

    public CircuitIntegratedPlusGui(PlayerInventoryGuiData data, ItemStack configurator) {
        this.data = data;
        this.configurator = configurator;

        ItemStack usedItemStack = data.getUsedItemStack();

        if (!(usedItemStack.getItem() instanceof CircuitIntegratedPlus)) throw new RuntimeException(
            "Tried to open the circuit integrated plus GUI with no circuit in main hand or offhand");

    }

    public ModularPanel build() {
        return new SelectItemGuiBuilder(
            GTGuis.createPopUpPanel("programmed_circuit_plus"),
            CircuitIntegratedPlus.ALL_VARIANTS).setHeaderItem(configurator)
                .setTitle(IKey.lang("GT5U.item.programmed_circuit.select.header"))
                .setSelected(
                    data.getUsedItemStack()
                        .getItemDamage())
                .setOnSelectedClientAction((selected, $) -> {
                    onConfigured(selected);
                    MCHelper.closeScreen();
                })
                .setCurrentItemSlotOverlay(GTGuiTextures.OVERLAY_SLOT_INT_CIRCUIT)
                .build();
    }

    private void onConfigured(int meta) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setByte("meta", (byte) meta);
        GTValues.NW.sendToServer(new GTPacketUpdateItem(tag));
    }
}
