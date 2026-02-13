package com.science.gtnl.api.mixinHelper;

import net.minecraft.util.ChunkCoordinates;

public interface IAnchorRespawn {

    void setAnchorRespawn(int dim, int x, int y, int z);

    int getAnchorDim();

    ChunkCoordinates getAnchorPos();

    void clearAnchorRespawn();
}
