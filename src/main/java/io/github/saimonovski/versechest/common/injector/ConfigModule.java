package io.github.saimonovski.versechest.common.injector;

import com.google.inject.AbstractModule;
import io.github.saimonovski.versechest.config.MainConfig;
import io.github.saimonovski.versechest.config.MessageConfig;
import io.github.saimonovski.versechest.config.service.ConfigService;

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
