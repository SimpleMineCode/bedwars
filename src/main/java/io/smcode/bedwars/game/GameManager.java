package io.smcode.bedwars.game;

import io.smcode.bedwars.config.Config;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GameManager {
    private static final ItemStack teamSelector;

    static {
        teamSelector = new ItemStack(Material.COMPASS);
        final ItemMeta meta = teamSelector.getItemMeta();
        meta.displayName(Component.text("Team Selector", NamedTextColor.BLUE));
        teamSelector.setItemMeta(meta);
    }

    private final Config config;
    private final Plugin plugin;
    private final Set<UUID> players = new HashSet<>();
    private Location lobby;
    private GameState gameState;

    public GameManager(Plugin plugin) {
        this.plugin = plugin;
        this.config = new Config(plugin, "locations.yml");
        this.gameState = GameState.IN_LOBBY;

        if (config.getConfig().isSet("Lobby-Location"))
            this.lobby = config.getConfig().getLocation("Lobby-Location");
    }

    public void join(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(4, teamSelector.clone());
        player.setHealth(player.getHealthScale());
        player.setFoodLevel(20);
        this.players.add(player.getUniqueId());

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new Countdown(this, 30), 0, 20);
    }

    public void sendMessage(String message) {
        for (Player all : players.stream().map(Bukkit::getOfflinePlayer)
                .filter(OfflinePlayer::isOnline).map(op -> (Player) op).toList())
            all.sendRichMessage(message);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Location getLobby() {
        return lobby;
    }

    public void setLobby(Location lobby) {
        this.lobby = lobby;
        this.config.getConfig().set("Lobby-Location", lobby);
        this.config.save();
    }

    public enum GameState {
        IN_LOBBY,
        IN_GAME
    }
}
