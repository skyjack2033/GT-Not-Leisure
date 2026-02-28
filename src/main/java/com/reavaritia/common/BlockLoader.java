package com.reavaritia.common;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.reavaritia.common.blocks.BlockExtremeAnvil;
import com.reavaritia.common.blocks.BlockNeutronCollector;
import com.reavaritia.common.blocks.BlockSoulFarmland;
import com.reavaritia.common.blocks.item.ItemBlockNeutronCollector;
import com.reavaritia.common.blocks.tile.TileEntityNeutronCollector;
import com.reavaritia.utils.enums.ReAvaItemList;
import com.science.gtnl.config.MainConfig;

import cpw.mods.fml.common.registry.GameRegistry;

public class BlockLoader {

    public static Block ExtremeAnvil;
    public static Block BlockSoulFarmland;
    public static Block NeutronCollector;
    public static Block DenseNeutronCollector;
    public static Block DenserNeutronCollector;
    public static Block DensestNeutronCollector;

    public static void registryBlocks() {

        ExtremeAnvil = new BlockExtremeAnvil();
        NeutronCollector = new BlockNeutronCollector(
            "BlockNeutronCollector",
            3600,
            2,
            "BlockNeutronCollector",
            "BlockNeutronCollector",
            ItemBlockNeutronCollector.class,
            ReAvaItemList.NeutronCollector);

        DenseNeutronCollector = new BlockNeutronCollector(
            "DenseNeutronCollector",
            3600,
            3,
            "DenseNeutronCollector",
            "DenseNeutronCollector",
            ItemBlockNeutronCollector.ItemBlockDenseNeutronCollector.class,
            ReAvaItemList.DenseNeutronCollector);

        DenserNeutronCollector = new BlockNeutronCollector(
            "DenserNeutronCollector",
            3600,
            4,
            "DenserNeutronCollector",
            "DenserNeutronCollector",
            ItemBlockNeutronCollector.ItemBlockDenserNeutronCollector.class,
            ReAvaItemList.DenserNeutronCollector);

        DensestNeutronCollector = new BlockNeutronCollector(
            "DensestNeutronCollector",
            200,
            4,
            "DensestNeutronCollector",
            "DensestNeutronCollector",
            ItemBlockNeutronCollector.ItemBlockDensestNeutronCollector.class,
            ReAvaItemList.DensestNeutronCollector);

        GameRegistry.registerTileEntity(TileEntityNeutronCollector.class, "NeutronCollectorTileEntity");

        BlockSoulFarmland = new BlockSoulFarmland();
    }

    public static void registryAnotherData() {
        ItemStack ExtremeAnvilBlock = new ItemStack(ExtremeAnvil, 1, 0);
        ItemStack Bedrock = new ItemStack(Blocks.bedrock, 1, 0);
        ItemStack EndPortal = new ItemStack(Blocks.end_portal, 1, 0);
        ItemStack EndPortalFrame = new ItemStack(Blocks.end_portal_frame, 1, 0);
        ItemStack CommandBlock = new ItemStack(Blocks.command_block, 1, 0);

        OreDictionary.registerOre(MainConfig.re_avaritia.unbreakOre, CommandBlock);
        OreDictionary.registerOre(MainConfig.re_avaritia.unbreakOre, EndPortal);
        OreDictionary.registerOre(MainConfig.re_avaritia.unbreakOre, EndPortalFrame);
        OreDictionary.registerOre(MainConfig.re_avaritia.unbreakOre, Bedrock);
        OreDictionary.registerOre(MainConfig.re_avaritia.unbreakOre, ExtremeAnvilBlock);

    }

}
