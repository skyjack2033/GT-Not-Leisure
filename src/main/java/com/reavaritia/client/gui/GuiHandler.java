package com.reavaritia.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.reavaritia.common.blocks.tile.TileEntityExtremeAnvil;
import com.reavaritia.common.blocks.tile.TileEntityNeutronCollector;
import com.reavaritia.container.ContainerExtremeAnvil;
import com.reavaritia.container.ContainerNeutronItem;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0)
            return new NeutronCollectorGUI(player.inventory, (TileEntityNeutronCollector) world.getTileEntity(x, y, z));
        if (ID == 1) {
            TileEntityExtremeAnvil tileEntity = (TileEntityExtremeAnvil) world.getTileEntity(x, y, z);
            return new ExtremeAnvilGUI(player.inventory, world, x, y, z, tileEntity, tileEntity, player);
        }
        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0) return new ContainerNeutronItem(
            player.inventory,
            (TileEntityNeutronCollector) world.getTileEntity(x, y, z));
        if (ID == 1) {
            TileEntityExtremeAnvil tileEntity = (TileEntityExtremeAnvil) world.getTileEntity(x, y, z);
            return new ContainerExtremeAnvil(player.inventory, world, x, y, z, tileEntity, tileEntity, player);
        }
        return null;
    }

}
