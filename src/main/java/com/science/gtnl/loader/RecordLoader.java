package com.science.gtnl.loader;

import net.minecraft.item.Item;

import com.science.gtnl.common.item.ItemRecord;

import cpw.mods.fml.common.registry.GameRegistry;

public class RecordLoader {

    public static Item recordSus = new ItemRecord("sus");
    public static Item recordNewHorizons = new ItemRecord("new_horizons");
    public static Item recordLavaChicken;

    public static void registryForMinecraft() {
        recordLavaChicken = new ItemRecord("lava_chicken");
        GameRegistry.registerItem(recordLavaChicken, "record_lava_chicken");
    }
}
