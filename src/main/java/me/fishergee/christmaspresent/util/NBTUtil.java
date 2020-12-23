package me.fishergee.christmaspresent.util;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NBTUtil {

    public static boolean isPresent(ItemStack item){
        if(item.getType() == Material.AIR || item == null){
            return false;
        }
        NBTItem nbtItem = new NBTItem(item);

        if(nbtItem.hasKey("id")){
            if(nbtItem.getString("id").equalsIgnoreCase("present")){
                return true;
            }
        }
        return false;
    }
}
