package io.github.saimonovski.versechest.util.message;

import io.github.saimonovski.versechest.util.message.replacers.Replacer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.entity.Player;

public class Message {
    private  Component component;
    private final MessageType type;


    public Message(Component component, MessageType type) {
        this.component = component;
        this.type = type;

    }
    public void send(Player player){
        type.send(player,this.component.replaceText(Replacer.replacePlayer(player)));
    }
    public void send(Player player, TextReplacementConfig replacement){
        this.type.send(player,this.component.replaceText(replacement));
    }
    public void send(Player player, TextReplacementConfig... replacements){
        Component comp = Component.text().append(component).build();
        for (TextReplacementConfig replacement : replacements) {
             comp = comp.replaceText(replacement);
        }
        this.type.send(player,comp);
    }
    public Component getText(){
        return component;
    }
    public void setText(Component component){
        this.component = component;
    }
}
