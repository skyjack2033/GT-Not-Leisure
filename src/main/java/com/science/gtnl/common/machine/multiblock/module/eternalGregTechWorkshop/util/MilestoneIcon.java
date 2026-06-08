package com.science.gtnl.common.machine.multiblock.module.eternalGregTechWorkshop.util;

import net.minecraft.util.StatCollector;

import com.gtnewhorizons.modularui.api.drawable.UITexture;
import com.gtnewhorizons.modularui.api.math.Size;

public enum MilestoneIcon {

    CHARGE(EternalGregTechWorkshopTextures.PICTURE_GODFORGE_MILESTONE_CHARGE, 60, 75, "power"),
    CONVERSION(EternalGregTechWorkshopTextures.PICTURE_GODFORGE_MILESTONE_CONVERSION, 54, 75, "recipe"),
    CATALYST(EternalGregTechWorkshopTextures.PICTURE_GODFORGE_MILESTONE_CATALYST, 75, 75, "fuel"),
    COMPOSITION(EternalGregTechWorkshopTextures.PICTURE_GODFORGE_MILESTONE_COMPOSITION, 75, 75, "purchasable");

    public static final MilestoneIcon[] VALUES = values();

    private final UITexture symbol;
    private final Size size;
    private final String name;

    MilestoneIcon(UITexture symbol, int width, int height, String shortName) {
        this.symbol = symbol;
        this.size = new Size(width, height);
        this.name = "gt.blockmachines.multimachine.FOG." + shortName + "milestone";
    }

    public UITexture getSymbol() {
        return symbol;
    }

    public Size getSize() {
        return size;
    }

    public float getWidthRatio() {
        return (float) size.width / size.height;
    }

    public String getNameText() {
        return StatCollector.translateToLocal(name);
    }
}
