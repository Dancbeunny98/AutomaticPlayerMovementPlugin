package ru.dancebunny98;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class AutomaticPlayerMovementPlugin extends JavaPlugin implements Listener {
    private PluginConfig pluginConfig;

    @Override
    public void onEnable() {
        loadConfig ();
        getServer ().getPluginManager ().registerEvents (this, this);
    }

    private void loadConfig() {
        File configFile = new File (getDataFolder (), "config.yml");
        if (!configFile.exists ()) {
            saveDefaultConfig ();
        }

        YamlConfiguration yamlConfig = YamlConfiguration.loadConfiguration (configFile);
        pluginConfig = new PluginConfig ();

        pluginConfig.setWorldNames (yamlConfig.getStringList ("worldNames"));
        pluginConfig.setX (yamlConfig.getInt ("x", 0));
        pluginConfig.setY (yamlConfig.getInt ("y", -60));
        pluginConfig.setZ (yamlConfig.getInt ("z", 0));
        pluginConfig.setYaw ((float) yamlConfig.getDouble ("yaw", -180));
        pluginConfig.setPitch ((float) yamlConfig.getDouble ("pitch", 0));
        pluginConfig.setAllowedMaterials (yamlConfig.getStringList ("allowedMaterials"));
        pluginConfig.setMinY (yamlConfig.getDouble ("minY", -63.0));
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer ();
        Location playerLocation = player.getLocation ();

        Material blockType = playerLocation.getBlock ().getType ();
        if (pluginConfig.getAllowedMaterials ().contains (blockType.toString ()) || playerLocation.getY () < pluginConfig.getMinY ()) {
            teleportPlayer (player);
        }
    }

    private void teleportPlayer(Player player) {
        player.setNoDamageTicks (20);

        World world = player.getWorld ();

        List<String> worldNames = pluginConfig.getWorldNames ();
        String targetWorldName = worldNames.isEmpty () ? "world" : worldNames.get (0);
        Location teleportLocation = new Location (
                Bukkit.getWorld (targetWorldName),
                pluginConfig.getX (),
                pluginConfig.getY (),
                pluginConfig.getZ (),
                pluginConfig.getYaw (),
                pluginConfig.getPitch ()
        );

        player.teleport (teleportLocation);
    }
}