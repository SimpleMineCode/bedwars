package io.smcode.bedwars.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public class GameManager {
    private static final ItemStack teamSelector;

    static {
        teamSelector = new ItemStack(Material.COMPASS);
        final ItemMeta meta = teamSelector.getItemMeta();
        meta.displayName(Component.text("Team Selector", NamedTextColor.BLUE));
        teamSelector.setItemMeta(meta);
    }

    private final Plugin plugin;
    private Location lobby;
    private GameState gameState;
    private Set<Player> players = new HashSet<>();

    public GameManager(Plugin plugin) {
        this.plugin = plugin;
        this.gameState = GameState.IN_LOBBY;
    }

    public void join(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(4, teamSelector.clone());
        this.players.add(player);

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new Countdown(this, 30), 0, 20);
    }

    public void sendMessage(String message) {
        for (Player all : players)
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
    }

    public enum GameState {
        IN_LOBBY,
        IN_GAME
    }
}
