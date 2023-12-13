package com.blank038.servermarket.internal.gui;

import com.blank038.servermarket.internal.plugin.ServerMarket;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Blank038
 */
public abstract class AbstractGui {
    protected static final Map<UUID, Long> COOLDOWN = new HashMap<>();

    static {
        Bukkit.getScheduler().runTaskTimerAsynchronously(ServerMarket.getInstance(), () -> {
            synchronized (COOLDOWN) {
                COOLDOWN.entrySet().removeIf((entry) -> System.currentTimeMillis() > entry.getValue());
            }
        }, 1200L, 1200L);
    }

    public boolean isCooldown(UUID uuid) {
        if (System.currentTimeMillis() <= COOLDOWN.getOrDefault(uuid, 0L)) {
            return true;
        }
        COOLDOWN.put(uuid, System.currentTimeMillis() + ServerMarket.getInstance().getConfig().getInt("cooldown.action"));
        return false;
    }
}
