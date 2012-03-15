package com.acyrid.VisitCounter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;

public class VisitCounter extends org.bukkit.plugin.java.JavaPlugin implements org.bukkit.event.Listener {

    File configFile = new File("plugins/VisitCounter/config.yml");
    FileConfiguration config;

    public void onDisable(){
        System.out.println("[" + getName() + "] disabled!");
    }

    public void onEnable() {
        if (!this.configFile.exists())
        {
            saveDefaultConfig();
        }
        this.config = getConfig();
        getServer().getPluginManager().registerEvents(this, this);
        System.out.println("[" + getName() + "] enabled!");
        
    }
    
    public void load(){
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        List list = this.config.getList("players");
        boolean first = false;
        if (list == null)
        {
            list = new ArrayList();
            first = true;
        }
        else if (!list.contains(player.getName()))
        {
            first = true;
        }
        if (first == true)
        {
            event.setJoinMessage(null);
            int count = this.config.getInt("counter");
            count++;
            this.config.set("counter", Integer.valueOf(count));
            list.add(player.getName());
            this.config.set("players", list);
            getServer().broadcastMessage(ChatColor.GOLD + player.getName() + ChatColor.LIGHT_PURPLE + " joined the server for the first time.");
            getServer().broadcastMessage(ChatColor.GOLD + player.getName() + ChatColor.LIGHT_PURPLE +" is guest "+ChatColor.GOLD +"#"+ count + ChatColor.LIGHT_PURPLE+" to our server!");
            try {
                this.config.save(this.configFile);
            } catch (IOException ex) {
                Logger.getLogger(VisitCounter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}