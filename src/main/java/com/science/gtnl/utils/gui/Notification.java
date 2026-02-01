
package com.science.gtnl.utils.gui;

import java.util.Objects;

import net.minecraft.item.ItemStack;

import lombok.Getter;

/**
 * Original source copied from BeyondRealityCore. All credits go to pauljoda for this code
 *
 * @author pauljoda
 */
@Getter
public class Notification {

    private final ItemStack icon;
    private final String title;
    private final String description;

    public Notification(ItemStack stack, String title, String desc) {
        icon = stack;
        this.title = title;
        description = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification that)) return false;
        return title.equals(that.title) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description);
    }
}
