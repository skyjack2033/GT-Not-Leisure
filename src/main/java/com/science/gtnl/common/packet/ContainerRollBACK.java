package com.science.gtnl.common.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ContainerPlayer;

import com.gtnewhorizon.gtnhlib.util.ServerThreadUtil;
import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.client.GTNLInputHandler;
import com.science.gtnl.common.packet.base.ClientboundPacket;
import com.science.gtnl.common.packet.base.ServerboundPacket;
import com.science.gtnl.utils.RCAEBaseContainer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerRollBACK extends ServerboundPacket {

    @Override
    public void handleServer(EntityPlayerMP player) {
        ServerThreadUtil.addScheduledTask(() -> {
            var newContainer = player.openContainer;
            if (newContainer instanceof RCAEBaseContainer rac) {
                var gtnl$oldContainer = rac.rc$getOldContainer();
                if (gtnl$oldContainer != null) {
                    if (gtnl$oldContainer instanceof ContainerPlayer) {
                        player.closeContainer();
                    } else {
                        player.getNextWindowId();
                        player.closeContainer();
                        int windowId = player.currentWindowId;
                        player.openContainer = gtnl$oldContainer;
                        player.openContainer.windowId = windowId;
                    }
                }
            }
            ScienceNotLeisure.network.sendTo(new Clientbound(), player);
        });
    }

    public static final class Clientbound extends ClientboundPacket {

        @Override
        public void handleClient(Minecraft minecraft) {
            clientRun();
        }

        @SideOnly(Side.CLIENT)
        private void clientRun() {
            if (GTNLInputHandler.DELAY_METHOD != null) {
                GTNLInputHandler.DELAY_METHOD.run();
                GTNLInputHandler.DELAY_METHOD = null;
                GTNLInputHandler.LAST_GUI_SCREEN = null;
            }
        }
    }
}
