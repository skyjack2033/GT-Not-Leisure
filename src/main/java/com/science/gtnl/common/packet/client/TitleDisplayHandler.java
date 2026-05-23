package com.science.gtnl.common.packet.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TitleDisplayHandler {

    public static String CURRENT_TITLE = "";
    public static int TICKS_REMAINING = 0;
    public static int COLOR_TEXT = 0xFFFFFF;
    public static double SCALE_TEXT = 3;
    public static int FADE_IN = 10;
    public static int FADE_OUT = 20;
    public static int TICKS_ALLTIME = 0;

    public static void displayTitle(String text, int durationTicks, int color, double scale, int customFadeIn,
        int customFadeOut) {
        CURRENT_TITLE = text;
        COLOR_TEXT = color;
        SCALE_TEXT = scale;
        FADE_IN = customFadeIn;
        FADE_OUT = customFadeOut;
        TICKS_ALLTIME = durationTicks;
        TICKS_REMAINING = FADE_IN + durationTicks + FADE_OUT;
    }

    public static int getArgb() {
        float alpha;
        if (TICKS_REMAINING > TICKS_ALLTIME + FADE_OUT) {
            // Fade in
            alpha = 1.0f - (TICKS_REMAINING - (TICKS_ALLTIME + FADE_OUT)) / (float) FADE_IN;
        } else if (TICKS_REMAINING > FADE_OUT) {
            // Stay
            alpha = 1.0f;
        } else {
            // Fade out
            alpha = TICKS_REMAINING / (float) FADE_OUT;
        }
        alpha = Math.min(Math.max(alpha, 0f), 1f);

        int r = (COLOR_TEXT >> 16) & 0xFF;
        int g = (COLOR_TEXT >> 8) & 0xFF;
        int b = COLOR_TEXT & 0xFF;
        int a = (int) (alpha * 255);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        if (TICKS_REMAINING > 0 && CURRENT_TITLE != null && !CURRENT_TITLE.isEmpty()) {
            GL11.glPushMatrix();
            Minecraft mc = Minecraft.getMinecraft();
            ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            FontRenderer fr = mc.fontRenderer;
            String displayTitle = CURRENT_TITLE;

            if (StatCollector.canTranslate(displayTitle)) {
                displayTitle = StatCollector.translateToLocal(displayTitle);
            }

            int stringWidth = fr.getStringWidth(displayTitle);
            int stringHeight = 9;

            double scale = SCALE_TEXT;
            int x = (res.getScaledWidth() - (int) (stringWidth * scale)) / 2;
            int y = (res.getScaledHeight() - (int) (stringHeight * scale)) / 2;

            int argb = getArgb();

            GL11.glTranslated(x, y, 0);
            GL11.glScaled(scale, scale, 1);

            fr.drawStringWithShadow(displayTitle, 0, 0, argb);

            GL11.glPopMatrix();

            TICKS_REMAINING--;
        }
    }
}
