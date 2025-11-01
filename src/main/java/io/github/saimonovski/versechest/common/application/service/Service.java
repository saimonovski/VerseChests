package io.github.saimonovski.versechest.common.application.service;

public interface Service {
    default void load(){}
    default void unload(){}
    void reload();

}
