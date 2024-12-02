package io.smcode.bedwars.game;

import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class Countdown implements Consumer<BukkitTask> {
    private final GameManager gameManager;
    private int currentCount;

    public Countdown(GameManager gameManager, int seconds) {
        this.gameManager = gameManager;
        this.currentCount = seconds;
    }

    @Override
    public void accept(BukkitTask task) {
        if (currentCount == 0) {
            task.cancel();
            this.gameManager.sendMessage("<gold>The game has started");
            return;
        }

        this.gameManager.sendMessage("<gray><blue>%d</blue> remaining.".formatted(currentCount--));
    }
}
