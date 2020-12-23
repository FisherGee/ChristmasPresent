package me.fishergee.christmaspresent.util;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTTileEntity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

public class NBTUtil {

    public static boolean isPresent(ItemStack item) {
        if (item.getType() == Material.AIR || item == null) {
            return false;
        }
        NBTItem nbtItem = new NBTItem(item);

        if (nbtItem.hasKey("id")) {
            if (nbtItem.getString("id").equalsIgnoreCase("present")) {
                return true;
            }
        }
        return false;
    }

    public static NBTCompound isPresent(Block block) {
        BlockState state = block.getState();

        NBTTileEntity tileEnt = new NBTTileEntity(state);

        NBTCompound persistentDataContainer = tileEnt.getPersistentDataContainer();

        if (persistentDataContainer.hasKey("id")) {
            if (persistentDataContainer.getString("id").equalsIgnoreCase("present")) {
                return persistentDataContainer;
            }
        }
        return null;
    }
}

