package io.github.saimonovski.versechest.util.message;

import org.bukkit.entity.Player;

import java.util.Map;

public class Message {
    private final String message;
    private final MessageType type;


    public Message(String message, MessageType type) {
        this.message = message;
        this.type = type;

    }
    public Message replace(String old, String text){
        return new Message(this.message.replace(old,text), this.type);
    }
    public Message replace(Map<String, String> stringsToReplace){
        String finalMessage = this.message;
        for (var entry : stringsToReplace.entrySet()){
            finalMessage = finalMessage.replace(entry.getKey(), entry.getValue());
        }
        return new Message(finalMessage,this.type);
    }

    public void send(Player player){
        this.type.send(player,this.message);
    }


}
