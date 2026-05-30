package com.science.gtnl.common.item.items.bauble;

public enum DraconicArmorProjectionType {

    WYVERN("wyvern"),
    DRACONIC("draconic");

    private final String id;

    DraconicArmorProjectionType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
