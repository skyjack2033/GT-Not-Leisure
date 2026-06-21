package com.science.gtnl.utils.detrav;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Arrays;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;

import org.lwjgl.opengl.GL11;

import com.gtnewhorizon.gtnhlib.util.CoordinatePacker;
import com.science.gtnl.common.packet.ProspectingPacket;

import gregtech.api.util.GTUtility;

public class DetravMapTexture extends AbstractTexture {

    public final ProspectingPacket packet;
    private String selected = "All";
    public int width = -1;
    public int height = -1;
    public boolean invert;

    public DetravMapTexture(ProspectingPacket packet) {
        this.packet = packet;
    }

    private BufferedImage getImage() {
        int backgroundColor = invert ? Color.GRAY.getRGB() : Color.WHITE.getRGB();
        int blockSize = packet.getSize();
        int chunkSize = packet.size * 2 + 1;

        BufferedImage image = new BufferedImage(blockSize, blockSize, BufferedImage.TYPE_INT_ARGB);
        WritableRaster raster = image.getRaster();

        int playerX = packet.posX - (packet.chunkX - packet.size) * 16 - 1;
        int playerZ = packet.posZ - (packet.chunkZ - packet.size) * 16 - 1;

        for (int z = 0; z < blockSize; z++) {
            for (int x = 0; x < blockSize; x++) {
                image.setRGB(x, z, backgroundColor);
            }
        }

        switch (packet.ptype) {
            case ProspectingPacket.MODE_BIG_ORES, ProspectingPacket.MODE_ALL_ORES -> drawOreTexture(image, blockSize);
            case ProspectingPacket.MODE_FLUIDS -> drawFluidTexture(image, chunkSize);
            case ProspectingPacket.MODE_POLLUTION -> drawPollutionTexture(raster, chunkSize);
            default -> {}
        }

        for (int z = 0; z < blockSize; z++) {
            for (int x = 0; x < blockSize; x++) {
                if (x % 16 == 0 || z % 16 == 0) {
                    raster.setSample(x, z, 0, raster.getSample(x, z, 0) / 2);
                    raster.setSample(x, z, 1, raster.getSample(x, z, 1) / 2);
                    raster.setSample(x, z, 2, raster.getSample(x, z, 2) / 2);
                }

                if (x == playerX || z == playerZ) {
                    raster.setSample(x, z, 0, (raster.getSample(x, z, 0) + 255) / 2);
                    raster.setSample(x, z, 1, raster.getSample(x, z, 1) / 2);
                    raster.setSample(x, z, 2, raster.getSample(x, z, 2) / 2);
                }
            }
        }

        return image;
    }

    private void drawOreTexture(BufferedImage image, int blockSize) {
        short[] depth = new short[blockSize * blockSize];
        Arrays.fill(depth, (short) 0);

        short selectedId = -1;
        if (!"All".equals(selected)) {
            for (var entry : packet.objects.short2ObjectEntrySet()) {
                if (selected.equals(
                    entry.getValue()
                        .left())) {
                    selectedId = entry.getShortKey();
                    break;
                }
            }
        }

        for (var entry : packet.map.long2ShortEntrySet()) {
            if (selectedId != -1 && selectedId != entry.getShortValue()) {
                continue;
            }
            long coord = entry.getLongKey();
            int x = CoordinatePacker.unpackX(coord);
            int y = CoordinatePacker.unpackY(coord);
            int z = CoordinatePacker.unpackZ(coord);
            int index = x + z * blockSize;
            if (y < depth[index]) {
                continue;
            }
            depth[index] = (short) y;
            image.setRGB(x, z, packet.getObjectColor(entry.getShortValue()));
        }
    }

    private void drawFluidTexture(BufferedImage image, int chunkSize) {
        for (int chunkZ = 0; chunkZ < chunkSize; chunkZ++) {
            for (int chunkX = 0; chunkX < chunkSize; chunkX++) {
                int amount = packet.getAmount(chunkX, chunkZ);
                if (amount <= 0) {
                    continue;
                }
                short objectId = packet.map.get(CoordinatePacker.pack(chunkX, 0, chunkZ));
                if (!packet.objects.containsKey(objectId)) {
                    continue;
                }
                String name = packet.getObjectName(objectId);
                if (!"All".equals(selected) && !selected.equals(name)) {
                    continue;
                }
                int color = packet.getObjectColor(objectId);
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        if ((x + z * 16) * 3 < amount + 48) {
                            image.setRGB(chunkX * 16 + x, chunkZ * 16 + z, color);
                        }
                    }
                }
            }
        }
    }

    private void drawPollutionTexture(WritableRaster raster, int chunkSize) {
        for (int chunkZ = 0; chunkZ < chunkSize; chunkZ++) {
            for (int chunkX = 0; chunkX < chunkSize; chunkX++) {
                int amount = packet.getAmount(chunkX, chunkZ);
                if (amount == 0) {
                    continue;
                }
                float multiplier = amount / 500000f;
                if (!invert) {
                    multiplier = 1f - multiplier;
                }
                multiplier = GTUtility.clamp(multiplier, 0, 1);
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        int imageX = chunkX * 16 + x;
                        int imageZ = chunkZ * 16 + z;
                        raster.setSample(imageX, imageZ, 0, (int) (raster.getSample(imageX, imageZ, 0) * multiplier));
                        raster.setSample(imageX, imageZ, 1, (int) (raster.getSample(imageX, imageZ, 1) * multiplier));
                        raster.setSample(imageX, imageZ, 2, (int) (raster.getSample(imageX, imageZ, 2) * multiplier));
                    }
                }
            }
        }
    }

    @Override
    public void loadTexture(IResourceManager resourceManager) {
        deleteGlTexture();
        if (packet != null) {
            int textureId = getGlTextureId();
            if (textureId < 0) {
                return;
            }
            TextureUtil.uploadTextureImageAllocate(textureId, getImage(), false, false);
            width = packet.getSize();
            height = packet.getSize();
        }
    }

    public void loadTexture(IResourceManager resourceManager, boolean invert) {
        this.invert = invert;
        loadTexture(resourceManager);
    }

    public void loadTexture(IResourceManager resourceManager, String selected, boolean invert) {
        this.selected = selected;
        loadTexture(resourceManager, invert);
    }

    public int glBindTexture() {
        if (glTextureId < 0) {
            return glTextureId;
        }
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, getGlTextureId());
        return glTextureId;
    }

    public void draw(int x, int y) {
        float textureWidth = 1F / width;
        float textureHeight = 1F / height;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + height, 0, 0, height * textureHeight);
        tessellator.addVertexWithUV(x + width, y + height, 0, width * textureWidth, height * textureHeight);
        tessellator.addVertexWithUV(x + width, y, 0, width * textureWidth, 0);
        tessellator.addVertexWithUV(x, y, 0, 0, 0);
        tessellator.draw();
    }
}
