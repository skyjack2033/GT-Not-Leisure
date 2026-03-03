package com.lootgames.sudoku;

import com.lootgames.sudoku.block.SudokuTile;
import com.lootgames.sudoku.sudoku.SudokuRenderer;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import ru.timeconqueror.timecore.api.util.Hacks;

public class ClientProxy extends CommonProxy {

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    @Optional.Method(modid = "lootgames")
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        ClientRegistry.bindTileEntitySpecialRenderer(SudokuTile.class, Hacks.safeCast(new SudokuRenderer()));
    }
}
