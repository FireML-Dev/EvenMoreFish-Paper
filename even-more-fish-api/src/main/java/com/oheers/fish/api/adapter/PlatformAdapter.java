package com.oheers.fish.api.adapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class PlatformAdapter {

    public PlatformAdapter() {
        logLoadedMessage();
    }

    public abstract void logLoadedMessage();

}
