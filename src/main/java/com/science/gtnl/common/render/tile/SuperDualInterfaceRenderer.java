package com.science.gtnl.common.render.tile;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import com.science.gtnl.common.block.blocks.BlockSuperDualInterface;
import com.science.gtnl.common.block.blocks.tile.TileEntitySuperDualInterface;

import appeng.block.misc.BlockInterface;
import appeng.client.render.BlockRenderInfo;
import appeng.client.render.blocks.RenderBlockInterface;

public class SuperDualInterfaceRenderer extends RenderBlockInterface {

    @Override
    public boolean renderInWorld(BlockInterface block, IBlockAccess world, int x, int y, int z, RenderBlocks renderer) {
        if (!(block instanceof BlockSuperDualInterface superDualInterface)) {
            return false;
        }

        TileEntitySuperDualInterface tile = superDualInterface.getTileEntity(world, x, y, z);
        BlockRenderInfo info = superDualInterface.getRendererInstance();

        if (tile != null && tile.getForward() != ForgeDirection.UNKNOWN) {
            IIcon side = superDualInterface.iconAlternateArrow;
            info.setTemporaryRenderIcons(
                superDualInterface.iconAlternate,
                superDualInterface.getIcon(0, 0),
                side,
                side,
                side,
                side);
        }

        preRenderInWorld(superDualInterface, world, x, y, z, renderer);
        boolean rendered = renderer.renderStandardBlock(superDualInterface, x, y, z);
        postRenderInWorld(renderer);
        info.setTemporaryRenderIcon(null);
        return rendered;
    }
}
