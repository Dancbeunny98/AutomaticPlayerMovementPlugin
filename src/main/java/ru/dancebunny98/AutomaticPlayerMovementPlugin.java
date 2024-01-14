package ru.dancebunny98;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
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

        getCommand("apmp").setExecutor(this);
    }
    private void reloadPlugin() {
        reloadConfig(); // Перезагружаем конфигурацию
        loadConfig();   // Загружаем обновленные данные из конфига
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("apmp") && args.length >= 1 && args[0].equalsIgnoreCase("reload")) {
            // Проверяем, если команду ввели с аргументом "reload"
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("apmp.reload")) {
                    reloadPlugin();
                    player.sendMessage("Plugin reloaded successfully!");
                } else {
                    player.sendMessage("You don't have permission to reload the plugin!");
                }
            } else {
                // Команду можно использовать только игрокам
                sender.sendMessage("This command can only be used by players!");
            }
            return true;
        }
        return false;
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
        pluginConfig.setAllowedMaterialsInventory(yamlConfig.getStringList("allowedMaterialsInventory"));
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location playerLocation = player.getLocation();
        PlayerInventory inventory = player.getInventory();

        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack != null && pluginConfig.getAllowedMaterialsInventory().contains(itemStack.getType().toString())) {
                // Если найден хотя бы один предмет из списка allowedMaterialsInventory, не выполняем телепортацию
                return;
            }
        }

        // Проверяем, если инвентарь имеет предметы из списка allowedMaterialsInventory
        if (hasAllowedMaterialInInventory(inventory)) {
            return; // Не телепортируем
        }

        Material blockType = playerLocation.getBlock().getType();
        // Проверяем, если блок или высота не соответствуют условиям
        if (pluginConfig.getAllowedMaterials().contains(blockType.toString()) || playerLocation.getY() < pluginConfig.getMinY()) {
            teleportPlayer(player);
        }
    }

    private boolean hasAllowedMaterialInInventory(PlayerInventory inventory) {
        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack != null && pluginConfig.getAllowedMaterialsInventory().contains(itemStack.getType().toString())) {
                return true; // Если найден хотя бы один предмет из списка allowedMaterialsInventory, вернуть true
            }
        }
        return false;
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