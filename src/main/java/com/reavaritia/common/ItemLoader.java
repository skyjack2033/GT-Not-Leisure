package com.reavaritia.common;

import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;

import com.reavaritia.common.item.BlazeAxe;
import com.reavaritia.common.item.BlazeHoe;
import com.reavaritia.common.item.BlazePickaxe;
import com.reavaritia.common.item.BlazeShovel;
import com.reavaritia.common.item.BlazeSword;
import com.reavaritia.common.item.ChronarchsClock;
import com.reavaritia.common.item.CrystalAxe;
import com.reavaritia.common.item.CrystalHoe;
import com.reavaritia.common.item.CrystalPickaxe;
import com.reavaritia.common.item.CrystalShovel;
import com.reavaritia.common.item.CrystalSword;
import com.reavaritia.common.item.InfinityAxe;
import com.reavaritia.common.item.InfinityBucket;
import com.reavaritia.common.item.InfinityHoe;
import com.reavaritia.common.item.InfinityPickaxe;
import com.reavaritia.common.item.InfinityShovel;
import com.reavaritia.common.item.InfinitySword;
import com.reavaritia.common.item.InfinityTotem;
import com.reavaritia.common.item.MatterCluster;

import cpw.mods.fml.common.registry.GameRegistry;

public class ItemLoader {

    public static Item CrystalSword = new CrystalSword();
    public static Item CrystalAxe = new CrystalAxe();
    public static Item CrystalPickaxe = new CrystalPickaxe();
    public static Item CrystalShovel = new CrystalShovel();
    public static Item CrystalHoe = new CrystalHoe();
    public static Item BlazeSword = new BlazeSword();
    public static Item BlazeAxe = new BlazeAxe();
    public static Item BlazePickaxe = new BlazePickaxe();
    public static Item BlazeShovel = new BlazeShovel();
    public static Item BlazeHoe = new BlazeHoe();
    public static Item InfinitySword = new InfinitySword();
    public static Item InfinityPickaxe = new InfinityPickaxe();
    public static Item InfinityAxe = new InfinityAxe();
    public static Item InfinityShovel = new InfinityShovel();
    public static Item InfinityHoe = new InfinityHoe();
    public static Item InfinityTotem = new InfinityTotem();
    public static Item InfinityBucket = new InfinityBucket();
    public static Item MatterCluster = new MatterCluster();
    public static Item ChronarchsClock = new ChronarchsClock();

    public static void registerItems() {
        IRegistry(CrystalPickaxe, "CrystalPickaxe");
        IRegistry(CrystalHoe, "CrystalHoe");
        IRegistry(CrystalShovel, "CrystalShovel");
        IRegistry(CrystalAxe, "CrystalAxe");
        IRegistry(CrystalSword, "CrystalSword");
        IRegistry(BlazePickaxe, "BlazePickaxe");
        IRegistry(BlazeAxe, "BlazeAxe");
        IRegistry(BlazeHoe, "BlazeHoe");
        IRegistry(BlazeSword, "BlazeSword");
        IRegistry(BlazeShovel, "BlazeShovel");
        IRegistry(InfinitySword, "InfinitySword");
        IRegistry(InfinityAxe, "InfinityAxe");
        IRegistry(InfinityPickaxe, "InfinityPickaxe");
        IRegistry(InfinityShovel, "InfinityShovel");
        IRegistry(InfinityHoe, "InfinityHoe");
        IRegistry(InfinityTotem, "InfinityTotem");
        IRegistry(InfinityBucket, "InfinityBucket");
        IRegistry(MatterCluster, "MatterCluster");
        IRegistry(ChronarchsClock, "ChronarchsClock");

        BlockDispenser.dispenseBehaviorRegistry.putObject(ChronarchsClock, ChronarchsClock);
    }

    public static void IRegistry(Item item, String name) {
        GameRegistry.registerItem(item, name);
    }

}
