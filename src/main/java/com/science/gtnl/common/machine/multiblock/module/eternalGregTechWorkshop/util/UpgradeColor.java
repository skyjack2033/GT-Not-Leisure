package com.science.gtnl.common.machine.multiblock.module.eternalGregTechWorkshop.util;

import com.gtnewhorizons.modularui.api.drawable.UITexture;

import lombok.Getter;

@Getter
public enum UpgradeColor {

    // spotless:off

    BLUE(
        EternalGregTechWorkshopTextures.BACKGROUND_GLOW_BLUE,
        EternalGregTechWorkshopTextures.PICTURE_OVERLAY_BLUE,
        EternalGregTechWorkshopTextures.PICTURE_UPGRADE_CONNECTOR_BLUE_OPAQUE,
        EternalGregTechWorkshopTextures.PICTURE_UPGRADE_CONNECTOR_BLUE),

    PURPLE(
        EternalGregTechWorkshopTextures.BACKGROUND_GLOW_PURPLE,
        EternalGregTechWorkshopTextures.PICTURE_OVERLAY_PURPLE,
        EternalGregTechWorkshopTextures.PICTURE_UPGRADE_CONNECTOR_PURPLE_OPAQUE,
        EternalGregTechWorkshopTextures.PICTURE_UPGRADE_CONNECTOR_PURPLE),

    ORANGE(
        EternalGregTechWorkshopTextures.BACKGROUND_GLOW_ORANGE,
        EternalGregTechWorkshopTextures.PICTURE_OVERLAY_ORANGE,
        EternalGregTechWorkshopTextures.PICTURE_UPGRADE_CONNECTOR_ORANGE_OPAQUE,
        EternalGregTechWorkshopTextures.PICTURE_UPGRADE_CONNECTOR_ORANGE),

    GREEN(
        EternalGregTechWorkshopTextures.BACKGROUND_GLOW_GREEN,
        EternalGregTechWorkshopTextures.PICTURE_OVERLAY_GREEN,
        EternalGregTechWorkshopTextures.PICTURE_UPGRADE_CONNECTOR_GREEN_OPAQUE,
        EternalGregTechWorkshopTextures.PICTURE_UPGRADE_CONNECTOR_GREEN),

    RED(
        EternalGregTechWorkshopTextures.BACKGROUND_GLOW_RED,
        EternalGregTechWorkshopTextures.PICTURE_OVERLAY_RED,
        EternalGregTechWorkshopTextures.PICTURE_UPGRADE_CONNECTOR_RED_OPAQUE,
        EternalGregTechWorkshopTextures.PICTURE_UPGRADE_CONNECTOR_RED),

    ;

    // spotless:on

    private final UITexture background;
    private final UITexture overlay;
    private final UITexture opaqueConnector;
    private final UITexture connector;

    UpgradeColor(UITexture background, UITexture overlay, UITexture opaqueConnector, UITexture connector) {
        this.background = background;
        this.overlay = overlay;
        this.opaqueConnector = opaqueConnector;
        this.connector = connector;
    }

}
