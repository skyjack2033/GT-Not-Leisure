package com.reavaritia.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ExtremeAnvilModel extends ModelBase {

    private final ModelRenderer bone;

    public ExtremeAnvilModel() {
        textureWidth = 64;
        textureHeight = 64;

        bone = new ModelRenderer(this);
        bone.setRotationPoint(8.0F, 24.0F, -8.0F);
        bone.cubeList.add(new ModelBox(bone, 0, 16, -14.0F, -4.0F, 2.0F, 12, 4, 12, 0.0F));
        bone.cubeList.add(new ModelBox(bone, 0, 32, -13.0F, -5.0F, 4.0F, 10, 1, 8, 0.0F));
        bone.cubeList.add(new ModelBox(bone, 16, 41, -10.0F, -10.0F, 6.0F, 4, 5, 4, 0.0F));
        bone.cubeList.add(new ModelBox(bone, 36, 32, -6.0F, -10.0F, 5.0F, 2, 5, 6, 0.0F));
        bone.cubeList.add(new ModelBox(bone, 0, 41, -12.0F, -10.0F, 5.0F, 2, 5, 6, 0.0F));
        bone.cubeList.add(new ModelBox(bone, 0, 0, -16.0F, -16.0F, 3.0F, 16, 6, 10, 0.0F));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        bone.render(f5);
    }
}
