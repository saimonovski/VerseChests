package io.github.saimonovski.versechest.common;

public interface Service {
    default void load(){}
    default void unload(){}
    void reload();

}
