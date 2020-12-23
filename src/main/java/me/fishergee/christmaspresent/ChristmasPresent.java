package me.fishergee.christmaspresent;

import me.fishergee.christmaspresent.cmds.PresentCmd;
import me.fishergee.christmaspresent.listeners.PresentBreak;
import me.fishergee.christmaspresent.listeners.PresentExplode;
import me.fishergee.christmaspresent.listeners.PresentPlace;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ChristmasPresent extends JavaPlugin {

    @Override
    public void onEnable(){

        registerEvents();
        registerCmds();
    }

    public void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new PresentBreak(), this);
        Bukkit.getPluginManager().registerEvents(new PresentExplode(), this);
        Bukkit.getPluginManager().registerEvents(new PresentPlace(), this);
    }

    public void registerCmds(){
        getCommand("present").setExecutor(new PresentCmd());
    }

}
