package io.github.mooy1.infinitylib.common;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.implementation.Slimefun;

@ParametersAreNonnullByDefault
public final class StackUtils {

    private StackUtils() {}

    @Nullable
    public static String getId(ItemStack item) {
        SlimefunItem sfItem = SlimefunItem.getByItem(item);
        if (sfItem != null) {
            return sfItem.getId();
        } else if (item.hasItemMeta()) {
            return getId(item.getItemMeta());
        } else {
            return null;
        }
    }

    @Nullable
    public static String getId(ItemMeta meta) {
        // Version-safe id lookup: PDC on 1.14+, item NBT fallback below (handled by the core service).
        return Slimefun.getItemDataService().getItemData(meta).orElse(null);
    }

    private static boolean isAir(ItemStack item) {
        Material type = item.getType();
        return type == Material.AIR || type.name().endsWith("AIR");
    }

    @Nonnull
    public static String getIdOrType(ItemStack item) {
        SlimefunItem sfItem = SlimefunItem.getByItem(item);
        if (sfItem != null) {
            return sfItem.getId();
        } else if (item.hasItemMeta()) {
            String id = getId(item.getItemMeta());
            return id == null ? item.getType().name() : id;
        } else {
            return item.getType().name();
        }
    }

    @Nullable
    public static ItemStack itemById(String id) {
        SlimefunItem item = SlimefunItem.getById(id);
        return item == null ? null : item.getItem().clone();
    }

    @Nonnull
    public static ItemStack itemByIdOrType(String idOrType) {
        SlimefunItem item = SlimefunItem.getById(idOrType);
        return item == null ? new ItemStack(Material.valueOf(idOrType)) : item.getItem().clone();
    }

    /**
     * Returns true when both items:
     *  - Are null or air
     *  - Have the same slimefun id
     *  - Have the same type and display name or lack thereof
     */
    public static boolean isSimilar(@Nullable ItemStack first, @Nullable ItemStack second) {
        if (first == null || isAir(first)) {
            return second == null || isAir(second);
        } else if (second == null || isAir(second)) {
            return false;
        } else if (first.hasItemMeta()) {
            if (second.hasItemMeta()) {
                ItemMeta firstMeta = first.getItemMeta();
                ItemMeta secondMeta = second.getItemMeta();
                String firstId = getId(firstMeta);
                if (firstId == null) {
                    if (getId(secondMeta) == null) {
                        if (first.getType() == second.getType()) {
                            if (firstMeta.hasDisplayName()) {
                                return secondMeta.hasDisplayName()
                                        && firstMeta.getDisplayName().equals(secondMeta.getDisplayName());
                            } else {
                                return !secondMeta.hasDisplayName();
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return firstId.equals(getId(secondMeta));
                }
            } else {
                return false;
            }
        } else if (second.hasItemMeta()) {
            return false;
        } else {
            return first.getType() == second.getType();
        }
    }

}

