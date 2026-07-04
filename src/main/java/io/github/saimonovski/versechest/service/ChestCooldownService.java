package io.github.saimonovski.versechest.service;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ChestCooldownService {
    private Map<UUID, Cooldown> cooldowns = new ConcurrentHashMap<>();

    public static ChestCooldownService deserialize(Map<UUID, Instant> serializedCooldowns) {
        ChestCooldownService chestCooldownService = new ChestCooldownService();
        Map<UUID, Cooldown> cooldowns = new ConcurrentHashMap<>();
        serializedCooldowns.forEach((id, instant) -> {
            if (instant.isAfter(Instant.now())) {
                cooldowns.put(id, new Cooldown(instant));
            }
        });
        chestCooldownService.setCooldownMap(cooldowns);
        return chestCooldownService;
    }

    public void clearCooldowns() {
        this.cooldowns.clear();
    }

    private void setCooldownMap(Map<UUID, Cooldown> cooldowns) {
        this.cooldowns = cooldowns;
    }

    public void clearCooldowns(UUID id) {
        this.cooldowns.remove(id);
    }

    public void setCooldown(@NotNull UUID id, int duration, @NotNull TimeUnit timeUnit) {
        if (duration <= 0) {
            cooldowns.remove(id);
            return;
        }

        cooldowns.put(id, new Cooldown(Instant.now().plus(timeUnit.toDuration(duration))));
    }

    public Map<UUID, Instant> serializeCooldowns() {
        Map<UUID, Instant> serialized = new HashMap<>();
        cooldowns.forEach((uuid, cooldown) -> serialized.put(uuid, cooldown.expiresAt));
        return serialized;
    }

    public boolean isStillOnCooldown(@NotNull UUID id) {

        Cooldown cooldown = cooldowns.get(id);
        if (cooldown == null) {
            return false;
        }

        if (cooldown.expiresAt().isAfter(Instant.now())) {
            return true;
        }

        cooldowns.remove(id);
        return false;
    }


    public enum TimeUnit {
        SECOND,
        MINUTE,
        HOUR,
        DAY;

        private Duration toDuration(int duration) {
            return switch (this) {
                case SECOND -> Duration.ofSeconds(duration);
                case MINUTE -> Duration.ofMinutes(duration);
                case HOUR -> Duration.ofHours(duration);
                case DAY -> Duration.ofDays(duration);
            };
        }
    }

    private record Cooldown(Instant expiresAt) {
    }

}
