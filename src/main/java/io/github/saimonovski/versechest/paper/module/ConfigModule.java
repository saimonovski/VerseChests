package io.github.saimonovski.versechest.paper.module;

import com.google.inject.AbstractModule;
import io.github.saimonovski.versechest.paper.config.MainConfig;
import io.github.saimonovski.versechest.paper.config.MessageConfig;
import io.github.saimonovski.versechest.paper.config.service.ConfigService;

public class ConfigModule extends AbstractModule {
    private final ConfigService configService;

    public ConfigModule(ConfigService configService) {
        this.configService = configService;
    }

    @Override
    protected void configure() {
        bind(MainConfig.class).toInstance(this.configService.getMainConfig());
        bind(MessageConfig.class).toInstance(this.configService.getMessageConfig());
    }
}
