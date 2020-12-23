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
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.inventory.ItemStack;


public class PresentBreak implements Listener {

    @EventHandler
    public void onPlayerBreakEvent(BlockBreakEvent e){

        Block block = e.getBlock();

        if(block.getType() != Material.CHEST){
            return;
        }

        BlockState state = block.getState();

        NBTTileEntity tileEnt = new NBTTileEntity(state);

        NBTCompound persistentDataContainer = tileEnt.getPersistentDataContainer();

        //checks if its a present
        if(persistentDataContainer.hasKey("id")){
            if(!persistentDataContainer.getString("id").equalsIgnoreCase("present")){
                return;
            }
        }else{
            return;
        }

        Chest chestBlock = (Chest) state;

        NBTCompoundList compList = persistentDataContainer.getCompoundList("items");

        //adds the items from the chest to the nbt data.
        for(ItemStack item : chestBlock.getInventory()){
            if(item == null){
                continue;
            }

            compList.addCompound(NBTItem.convertItemtoNBT(item));
        }

        //make sure items do not drop.
        chestBlock.getInventory().clear();

        //get the item dropped by the event
        ItemStack itemDropped = (ItemStack) e.getBlock().getDrops().toArray()[0];

        //get a new copy of the dropped item
        NBTItem nbtItem = new NBTItem(itemDropped);

        //merge the data of the chest into the item
        nbtItem.mergeCompound(persistentDataContainer);

        //get rid of the original item.
        block.setType(Material.AIR);

        Player p = e.getPlayer();

        //give the updated item back to the player
        if(p.getInventory().firstEmpty() == -1){
            p.getWorld().dropItem(p.getLocation(), nbtItem.getItem());
        }
        else{
            p.getInventory().addItem(nbtItem.getItem());
        }
    }
}
