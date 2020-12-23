package me.fishergee.christmaspresent.listeners;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTCompoundList;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTTileEntity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.inventory.ItemStack;

public class PresentExplode implements Listener {

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e) {
        System.out.println("adsdadas");
        Bukkit.getServer().broadcastMessage(" extremely bad triggered");

        Block block = e.getBlock();

        if (block.getType() != Material.CHEST) {
            return;
        }

        BlockState state = block.getState();

        NBTTileEntity tileEnt = new NBTTileEntity(state);

        NBTCompound persistentDataContainer = tileEnt.getPersistentDataContainer();

        //checks if its a present
        if (persistentDataContainer.hasKey("id")) {
            if (!persistentDataContainer.getString("id").equalsIgnoreCase("present")) {
                Bukkit.broadcastMessage(" bad triggered");

                return;
            }
        } else {
            Bukkit.broadcastMessage(" very bad triggered");
            return;
        }

        Bukkit.broadcastMessage("triggered");
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
        ItemStack itemDropped = (ItemStack) block.getDrops().toArray()[0];

        //get a new copy of the dropped item
        NBTItem nbtItem = new NBTItem(itemDropped, true);

        //merge the data of the chest into the item
        nbtItem.mergeCompound(persistentDataContainer);

        //get rid of the original item.
        //block.setType(Material.AIR);
    }
}
