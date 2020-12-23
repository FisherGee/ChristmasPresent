package me.fishergee.christmaspresent.listeners;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTCompoundList;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTTileEntity;
import me.fishergee.christmaspresent.util.NBTUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PresentExplode implements Listener {

    @EventHandler
    public void onBlockExplode(EntityExplodeEvent e) {

        List<Block> blocks = e.blockList();
        Block targetBlock = null;
        NBTCompound persistentDataContainer = null;

        for(Block block : blocks){
            if(!block.getType().equals(Material.CHEST)){
                continue;
            }

            if(NBTUtil.isPresent(block) != null){
               persistentDataContainer = NBTUtil.isPresent(block);
               targetBlock = block;
            }
        }

        //return if the target block
        //does not exist || null
        if(targetBlock == null){
            return;
        }

        BlockState state = targetBlock.getState();

        Chest chestBlock = (Chest) state;

        NBTCompoundList compList = persistentDataContainer.getCompoundList("items");

        //adds the items from the chest to the nbt data.
        for (ItemStack item : chestBlock.getInventory()) {
            if (item == null) {
                continue;
            }

            compList.addCompound(NBTItem.convertItemtoNBT(item));
        }

        //get the item dropped by the event
        ItemStack itemDropped = (ItemStack) targetBlock.getDrops().toArray()[0];

        //get a new copy of the dropped item
        NBTItem nbtItem = new NBTItem(itemDropped);

        //merge the data of the chest into the item
        nbtItem.mergeCompound(persistentDataContainer);

        //remove the old item
        targetBlock.getDrops().clear();

        //drop the copied data item at the location of the chest
        targetBlock.getWorld().dropItem(targetBlock.getLocation(), nbtItem.getItem());
    }
}
