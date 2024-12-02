package io.smcode.bedwars;

import io.smcode.bedwars.commands.SetLobbyCommand;
import io.smcode.bedwars.game.GameManager;
import io.smcode.bedwars.listeners.LobbyListener;
import org.bukkit.plugin.java.JavaPlugin;

public class BedWarsPlugin extends JavaPlugin {
    // TODO: Add messages

    @Override
    public void onEnable() {
        final GameManager gameManager = new GameManager(this);

        getCommand("setlobby").setExecutor(new SetLobbyCommand(gameManager));
        getServer().getPluginManager().registerEvents(new LobbyListener(gameManager), this);
    }
}
