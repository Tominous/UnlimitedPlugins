package com.noah.ulpl.plugins;

import java.io.File;

public class UPlugin {

    private boolean loaded;
    private boolean enabled;
    private String name;
    private File file;

    public UPlugin(String name, File file) {
        this.loaded = false;
        this.enabled = false;
        this.name = name;
        this.file = file;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
