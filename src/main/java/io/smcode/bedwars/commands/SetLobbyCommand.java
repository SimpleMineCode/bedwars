package io.smcode.bedwars.commands;

import io.smcode.bedwars.game.GameManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetLobbyCommand implements CommandExecutor {
    private final GameManager gameManager;

    public SetLobbyCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof final Player player)) {
            sender.sendMessage("§cOnly players can execute this command!");
            return true;
        }

        final Location lobbyLocation = player.getLocation();
        this.gameManager.setLobby(lobbyLocation);
        lobbyLocation.clone().subtract(0, 1, 0).getBlock().setType(Material.EMERALD_BLOCK);

        player.sendRichMessage("<green>Lobby location has been set.");

        return true;
    }
}
