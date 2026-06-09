package com.science.gtnl.common.gui;

import com.cleanroommc.modularui.drawable.UITexture;
import com.science.gtnl.utils.enums.ModList;

import gregtech.api.enums.Mods;
import gregtech.api.modularui2.GTGuiTextures;

public class GTNLMui2Textures {

    private GTNLMui2Textures() {}

    public static final UITexture PICTURE_CIRCULATION = UITexture
        .fullImage(ModList.ScienceNotLeisure.ID, "gui/picture/circulation_");

    public static final UITexture PICTURE_GTNL_LOGO = UITexture
        .fullImage(ModList.ScienceNotLeisure.ID, "gui/picture/logo");

    public static final UITexture PICTURE_GTNL_STEAM_LOGO = UITexture
        .fullImage(ModList.ScienceNotLeisure.ID, "gui/picture/steam_logo");

    public static final UITexture OVERLAY_BUTTON_ARROW_GREEN_UP = UITexture
        .fullImage(Mods.GregTech.ID, "gui/overlay_button/arrow_green_up");

    public static final UITexture OVERLAY_BUTTON_MACHINEMODE_SLICING = GTGuiTextures.OVERLAY_BUTTON_MACHINEMODE_SLICING;
}
