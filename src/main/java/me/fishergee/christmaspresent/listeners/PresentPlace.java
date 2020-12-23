package me.fishergee.christmaspresent.listeners;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTCompoundList;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTTileEntity;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;

public class PresentPlace implements Listener {

    @EventHandler
    public void onPlayerPlaceEvent(BlockPlaceEvent e){
        if(e.getBlock().getType() != Material.CHEST){
            return;
        }

        //get the block placed
        Block placedBlock = e.getBlockPlaced();

        Player p = e.getPlayer();

        BlockState state = placedBlock.getState();

        NBTTileEntity tileEnt = new NBTTileEntity(state);

        NBTItem itemInHand = new NBTItem(e.getItemInHand());

        NBTCompound persistentDataContainer = tileEnt.getPersistentDataContainer();

        persistentDataContainer.mergeCompound(itemInHand);

        Chest chestPlaced = (Chest) state;

        //checks if its a present
        if(persistentDataContainer.hasKey("id")){
            if(!persistentDataContainer.getString("id").equalsIgnoreCase("present")){
                return;
            }
        }else{
            return;
        }

        if(persistentDataContainer.hasKey("message")){
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', persistentDataContainer.getString("message")));
        }

        NBTCompoundList compList = persistentDataContainer.getCompoundList("items");

        //adds the items in the complist to chest inventory.
        for(NBTCompound item : compList){
            chestPlaced.getInventory().addItem(NBTItem.convertNBTtoItem(item));
        }

        //clears all added items.
        compList.clear();
    }
}
