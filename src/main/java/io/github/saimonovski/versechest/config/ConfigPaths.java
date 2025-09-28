package io.github.saimonovski.versechest.config;

public final class ConfigPaths {

    public static final String IS_DEBUG_ENABLED = "debug-enabled";

    private ConfigPaths() {
    }

    public static final String CONFIG_VERSION = "config-version";
    public static final String SERVER_ID = "server-id";
    public static final String CHEST_COOLDOWN = "chestCooldown";

    public static final class BlockCreator {
        public static final String SECTION = "blockCreator";
        public static final String MATERIAL = SECTION + ".material";
        public static final String NAME = SECTION + ".name";
        public static final String LORE = SECTION + ".lore";
    }

    public static final class ChestConfiguration {
        public static final String SECTION = "chestConfiguration";
        public static final String MATERIAL = SECTION + ".material";
        public static final String TITLE = SECTION + ".title";
        public static final String MIN_ITEMS = SECTION + ".minItems";
        public static final String MAX_ITEMS = SECTION + ".maxItems";
        public static final String CHEST_SIZE = SECTION + ".chestSize";
    }

    public static final class Rarity {
        public static final String SECTION = "rarity";
        public static final String MIN_ITEMS = "minItems";
        public static final String MAX_ITEMS = "maxItems";
        public static final String CHANCE_TO_DROP = "chanceToDrop";
    }

    public static final class Messages {
        public static final String MESSAGE_TYPE = "type";
        public static final String MESSAGE_CONTENT = "message";

        public static final String CHEST_CREATOR_GIVEN = "chest-creator-message";
        public static final String CHANGES_SAVED = "changes-saved-message";
        public static final String COOLDOWN = "cooldown-message";
        public static final String CHEST_CREATED = "chest-created-message";
        public static final String CHEST_LOOTED = "chest-looted-message";
        public static final String CHEST_REMOVED = "chest-removed-message";
    }

}

