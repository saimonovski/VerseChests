package io.github.saimonovski.versechest.service;

import io.github.saimonovski.versechest.VerseChest;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

public class ChestCooldownService {
    private final Map<UUID, Cooldown> cooldowns = new ConcurrentHashMap<>();

    public void clearCooldowns(){
        this.cooldowns.clear();
    }
    public void clearCooldowns(UUID id){
        this.cooldowns.remove(id);
    }
    public void setCooldown(@NotNull UUID id,   int duration, @NotNull TimeUnit timeUnit) {
        if (duration <= 0) {
            cooldowns.remove(id);
            return;
        }

        cooldowns.put(id, new Cooldown(Instant.now().plus(timeUnit.toDuration(duration))));
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
