package io.github.saimonovski.versechest.util.message;

import io.github.saimonovski.versechest.util.ChatUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public enum MessageType {
   ACTIONBAR(message -> {
       ChatUtil.sendActionBar(message.player, message.buildComponent());
   }),
    TITLE(message -> {
        ChatUtil.sendTitle(message.player, message.buildComponent());
    }),
    SUBTITLE(message -> {
        ChatUtil.sendSubtitle(message.player, message.buildComponent());
    }),
    CHAT(message -> {
        message.player.sendMessage(message.buildComponent());
    }),
    DISABLED(messageToSend -> {});

    private final  Consumer<MessageToSend> object;

    MessageType(Consumer<MessageToSend> object) {
        this.object = object;
    }
    public void send(Player player, String message){
        MessageToSend messageToSend = new MessageToSend(message, player);
        this.object.accept(messageToSend);
    }
    

    private record MessageToSend(String message, Player player) {
        Component buildComponent() {
                return ChatUtil.fixWithPlaceholderApi(message, player);
            }
        }
}
