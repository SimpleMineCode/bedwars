package io.smcode.bedwars.listeners;

import io.smcode.bedwars.game.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import static io.smcode.bedwars.game.GameManager.GameState.IN_LOBBY;

public class LobbyListener implements Listener {
    private final GameManager gameManager;

    public LobbyListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onJoin(PlayerSpawnLocationEvent event) {
        if (this.gameManager.getGameState() != IN_LOBBY)
            return;

        if (this.gameManager.getLobby() != null)
            event.setSpawnLocation(this.gameManager.getLobby());

        this.gameManager.join(event.getPlayer());
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (this.gameManager.getGameState() == IN_LOBBY)
            event.setCancelled(true);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (this.gameManager.getGameState() == IN_LOBBY)
            event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (this.gameManager.getGameState() == IN_LOBBY)
            event.setCancelled(true);
    }
}
