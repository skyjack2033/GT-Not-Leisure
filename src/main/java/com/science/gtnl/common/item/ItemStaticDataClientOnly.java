package com.science.gtnl.common.item;

import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

@SideOnly(Side.CLIENT)
public class ItemStaticDataClientOnly {

    @SideOnly(Side.CLIENT)
    public static final Int2ObjectMap<IIcon> META_ITEM_01_ICONS = new Int2ObjectOpenHashMap<>();

    @SideOnly(Side.CLIENT)
    public static final Int2ObjectMap<IIcon> ELECTRIC_PROSPECTOR_TOOL_ICONS = new Int2ObjectOpenHashMap<>();

    @SideOnly(Side.CLIENT)
    public static final Int2ObjectMap<IIcon> IZUMIK_ICONS = new Int2ObjectOpenHashMap<>();

}
