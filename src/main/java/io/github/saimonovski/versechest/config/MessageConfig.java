package io.github.saimonovski.versechest.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import io.github.saimonovski.versechest.message.Message;
import io.github.saimonovski.versechest.message.MessageType;
import io.github.saimonovski.versechest.util.ChatUtil;

public class MessageConfig {
    private  YamlDocument yamlDocument;

    private  Message chestCreatorGivenMessage;
    private  Message changesSavedMessage;
    private  Message cooldownMessage;
    private  Message chestCreatedMessage;
    private  Message chestLootedMessage;
    private  Message chestRemovedMessage;

    public MessageConfig(YamlDocument yamlDocument) {
        loadMessages(yamlDocument);
    }
    public void loadMessages(YamlDocument yamlDocument){
        this.yamlDocument = yamlDocument;
        this.chestCreatorGivenMessage = loadMessage(ConfigPaths.Messages.CHEST_CREATOR_GIVEN);
        this.changesSavedMessage = loadMessage(ConfigPaths.Messages.CHANGES_SAVED);
        this.cooldownMessage = loadMessage(ConfigPaths.Messages.COOLDOWN);
        this.chestCreatedMessage = loadMessage(ConfigPaths.Messages.CHEST_CREATED);
        this.chestLootedMessage = loadMessage(ConfigPaths.Messages.CHEST_LOOTED);
        this.chestRemovedMessage = loadMessage(ConfigPaths.Messages.CHEST_REMOVED);
    }

    private Message loadMessage(String messagePath) {
        Section section = yamlDocument.getSection(messagePath);
        if (section == null) {
            return new Message(ChatUtil.fix(" "), MessageType.DISABLED);
        }

        MessageType messageType = section.getEnum(ConfigPaths.Messages.MESSAGE_TYPE, MessageType.class, MessageType.DISABLED);
        String content = section.getString(ConfigPaths.Messages.MESSAGE_CONTENT, " " + messagePath);

        return new Message(ChatUtil.fix(content), messageType);
    }

    public Message chestCreatorGivenMessage() {
        return chestCreatorGivenMessage;
    }

    public Message changesSavedMessage() {
        return changesSavedMessage;
    }

    public Message cooldownMessage() {
        return cooldownMessage;
    }

    public Message chestCreatedMessage() {
        return chestCreatedMessage;
    }

    public Message chestLootedMessage() {
        return chestLootedMessage;
    }

    public Message chestRemovedMessage() {
        return chestRemovedMessage;
    }
}
