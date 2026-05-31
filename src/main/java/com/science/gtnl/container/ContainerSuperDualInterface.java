package com.science.gtnl.container;

import net.minecraft.entity.player.InventoryPlayer;

import com.science.gtnl.common.block.blocks.tile.TileEntitySuperDualInterface;

import appeng.api.config.Settings;
import appeng.api.config.SidelessMode;
import appeng.api.util.IConfigManager;
import appeng.helpers.IInterfaceHost;

public class ContainerSuperDualInterface extends ContainerSuperInterface {

    public SidelessMode sidelessMode = SidelessMode.SIDELESS;
    private final boolean isTile;

    public ContainerSuperDualInterface(InventoryPlayer inventoryPlayer, IInterfaceHost host) {
        super(inventoryPlayer, host);
        isTile = host instanceof TileEntitySuperDualInterface;
    }

    @Override
    public int getHeight() {
        return 211;
    }

    @Override
    protected void loadSettingsFromHost(IConfigManager cm) {
        super.loadSettingsFromHost(cm);
        sidelessMode = SidelessMode.SIDELESS;
        if (isTile) {
            try {
                sidelessMode = (SidelessMode) cm.getSetting(Settings.SIDELESS_MODE);
            } catch (IllegalStateException ignored) {
                sidelessMode = SidelessMode.SIDELESS;
            }
        }
    }
}
