package io.github.saimonovski.versechest.paper.message;

import io.github.saimonovski.versechest.paper.util.ChatUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

import java.util.function.Consumer;
import java.util.function.Function;

public enum MessageType {
    ACTIONBAR(player -> message -> {
        message = ChatUtil.fix(PlaceholderAPI.setPlaceholders(player,MiniMessage.miniMessage().serialize(message)));
        ChatUtil.sendActionBar(player,message);
    }),
    TITLE(player -> message -> {
        message = ChatUtil.fix(PlaceholderAPI.setPlaceholders(player,MiniMessage.miniMessage().serialize(message)));
        ChatUtil.sendTitle(player,message);
    }),
    SUBTITLE(player -> message -> {
        message = ChatUtil.fix(PlaceholderAPI.setPlaceholders(player,MiniMessage.miniMessage().serialize(message)));
        ChatUtil.sendSubtitle(player,message);
    }),
    CHAT(player -> message -> {
        message = ChatUtil.fix(PlaceholderAPI.setPlaceholders(player,MiniMessage.miniMessage().serialize(message)));
        player.sendMessage(message);
    }),
    DISABLED(player -> message -> {
    });

    private final Function<Player, Consumer<Component>> object;

    MessageType(Function<Player, Consumer<Component>> object) {
        this.object = object;
    }
    public void send(Player player, Component message){
        this.object.apply(player).accept(message);
    }
}
