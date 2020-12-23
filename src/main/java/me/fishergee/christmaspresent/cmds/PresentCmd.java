package me.fishergee.christmaspresent.cmds;

import de.tr7zw.nbtapi.NBTItem;
import me.fishergee.christmaspresent.util.NBTUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PresentCmd implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(!command.getName().equalsIgnoreCase("present")){
            return true;
        }

        if(!(sender instanceof Player)){
            return true;
        }

        Player p = (Player) sender;

        if(args.length == 1){
            if(args[0].equalsIgnoreCase("give")){

                ItemStack presentItem = new ItemStack(Material.CHEST);

                ItemMeta im = presentItem.getItemMeta();
                im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lSANTA &f&lPRESENT &7(Place down)"));

                presentItem.setItemMeta(im);

                NBTItem presentNBT = new NBTItem(presentItem, true);

                presentNBT.setString("id", "present");
                presentNBT.getCompoundList("items");

                p.getInventory().addItem(presentItem);
            }
        }
        else if (args.length >= 2){
            if(args[0].equalsIgnoreCase("message")){
                if(NBTUtil.isPresent(p.getInventory().getItemInMainHand())){

                    String message = "";

                    //message riley hey
                    for(int i = 1; i < args.length; i ++){
                        if(i == 1){
                            message = args[i];
                            continue;
                        }
                        message = message + " " + args[i];
                    }

                    NBTItem presentNBT = new NBTItem(p.getInventory().getItemInMainHand(), true);

                    presentNBT.setString("message", message);

                    p.sendMessage(ChatColor.GREEN + "Message has been set!");
                }
                else{
                    p.sendMessage(ChatColor.RED + "That is not a present.");
                }
            }
        }
        return true;
    }
}
