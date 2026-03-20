package com.science.gtnl.common.render.tile;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import com.science.gtnl.common.block.blocks.BlockSuperInterface;
import com.science.gtnl.common.block.blocks.tile.TileEntitySuperInterface;

import appeng.block.misc.BlockInterface;
import appeng.client.render.BlockRenderInfo;
import appeng.client.render.blocks.RenderBlockInterface;

public class SuperInterfaceRenderer extends RenderBlockInterface {

    public SuperInterfaceRenderer() {
        super();
    }

    @Override
    public boolean renderInWorld(final BlockInterface block, final IBlockAccess world, final int x, final int y,
        final int z, final RenderBlocks renderer) {
        if (!(block instanceof BlockSuperInterface superInterface)) return false;
        final TileEntitySuperInterface ti = superInterface.getTileEntity(world, x, y, z);
        final BlockRenderInfo info = superInterface.getRendererInstance();

        if (ti != null && ti.getForward() != ForgeDirection.UNKNOWN) {
            final IIcon side = superInterface.iconAlternateArrow;
            info.setTemporaryRenderIcons(
                superInterface.iconAlternate,
                superInterface.getIcon(0, 0),
                side,
                side,
                side,
                side);
        }

        this.preRenderInWorld(superInterface, world, x, y, z, renderer);

        final boolean fz = renderer.renderStandardBlock(superInterface, x, y, z);

        this.postRenderInWorld(renderer);

        info.setTemporaryRenderIcon(null);

        return fz;
    }
}
