package com.science.gtnl.common.block.blocks.item;

import static com.science.gtnl.ScienceNotLeisure.*;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.science.gtnl.client.GTNLCreativeTabs;
import com.science.gtnl.common.block.blocks.PartSuperInterface;

import appeng.api.AEApi;
import appeng.api.parts.IPartItem;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

// TODO:添加方块版本的超级ME接口
// TODO:再加个二合一ME接口
public class ItemPartSuperInterface extends Item implements IPartItem {

    public ItemPartSuperInterface() {
        this.setMaxStackSize(64);
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureBlock);
        this.setUnlocalizedName("part_super_interface");
        this.setTextureName(RESOURCE_ROOT_ID + ":" + "SuperInterface");
        GameRegistry.registerItem(this, "part_super_interface");
        AEApi.instance()
            .partHelper()
            .setItemBusRenderer(this);
    }

    @Nullable
    @Override
    public PartSuperInterface createPartFromItemStack(ItemStack is) {
        return new PartSuperInterface(is);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float xOffset, float yOffset, float zOffset) {
        return AEApi.instance()
            .partHelper()
            .placeBus(player.getHeldItem(), x, y, z, side, player, world);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getSpriteNumber() {
        return 0;
    }
}
