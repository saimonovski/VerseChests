package io.github.saimonovski.versechest.service;

import io.github.saimonovski.versechest.config.ChestConfig;
//todo initialize configs and use config library
public class ConfigService {
    private ChestConfig chestConfig;

    public ChestConfig getChestConfig() {
        return this.chestConfig;
    }

}
