package com.science.gtnl.container;

import net.minecraft.entity.player.InventoryPlayer;

import appeng.container.implementations.ContainerPriority;
import appeng.helpers.IPriorityHost;

public class ContainerCustomPriority extends ContainerPriority {

    public ContainerCustomPriority(InventoryPlayer ip, IPriorityHost host) {
        super(ip, host);
    }

    @Override
    public IPriorityHost getTarget() {
        return (IPriorityHost) this.getInventoryPlayer().player.openContainer;
    }
}
