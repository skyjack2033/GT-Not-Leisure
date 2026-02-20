package com.reavaritia.client;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.reavaritia.common.ItemLoader;

public class ReAvaCreativeTabs {

    public static CreativeTabs ReAvaritia = new CreativeTabs("ReAvaritia") {

        @Override
        public Item getTabIconItem() {
            return ItemLoader.InfinityPickaxe;
        }
    };

}
