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

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.registry.GameRegistry;
import fox.spiteful.avaritia.items.LudicrousItems;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Mods;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GTOreDictUnificator;

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
            "NeutronCollector",
            3600,
            Mods.Avaritia.isModLoaded() ? getNeutronDust()
                : GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.CosmicNeutronium, 1),
            "NeutronCollector",
            "NeutronCollector",
            ItemBlockNeutronCollector.class,
            ReAvaItemList.NeutronCollector);

        DenseNeutronCollector = new BlockNeutronCollector(
            "DenseNeutronCollector",
            3600,
            Mods.Avaritia.isModLoaded() ? getNeutronNugget()
                : GTOreDictUnificator.get(OrePrefixes.nugget, Materials.CosmicNeutronium, 1),
            "DenseNeutronCollector",
            "DenseNeutronCollector",
            ItemBlockNeutronCollector.ItemBlockDenseNeutronCollector.class,
            ReAvaItemList.DenseNeutronCollector);

        DenserNeutronCollector = new BlockNeutronCollector(
            "DenserNeutronCollector",
            3600,
            Mods.Avaritia.isModLoaded() ? getNeutronInot()
                : GTOreDictUnificator.get(OrePrefixes.ingot, Materials.CosmicNeutronium, 1),
            "DenserNeutronCollector",
            "DenserNeutronCollector",
            ItemBlockNeutronCollector.ItemBlockDenserNeutronCollector.class,
            ReAvaItemList.DenserNeutronCollector);

        DensestNeutronCollector = new BlockNeutronCollector(
            "DensestNeutronCollector",
            200,
            Mods.Avaritia.isModLoaded() ? getNeutronInot()
                : GTOreDictUnificator.get(OrePrefixes.ingot, Materials.CosmicNeutronium, 1),
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

    @Optional.Method(modid = "Avaritia")
    public static ItemStack getNeutronDust() {
        return new ItemStack(LudicrousItems.resource, 1, 2);
    }

    @Optional.Method(modid = "Avaritia")
    public static ItemStack getNeutronNugget() {
        return new ItemStack(LudicrousItems.resource, 1, 3);
    }

    @Optional.Method(modid = "Avaritia")
    public static ItemStack getNeutronInot() {
        return new ItemStack(LudicrousItems.resource, 1, 4);
    }

}
