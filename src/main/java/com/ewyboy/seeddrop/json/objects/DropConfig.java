package com.ewyboy.seeddrop.json.objects;

import java.util.List;

public class DropConfig {

    private List<DropEntry> dropConfig;

    public DropConfig(List<DropEntry> dropConfig) {
        this.dropConfig = dropConfig;
    }

    public List<DropEntry> getDropConfig() {
        return dropConfig;
    }

    public void setDropConfig(List<DropEntry> dropConfig) {
        this.dropConfig = dropConfig;
    }

    @Override
    public String toString() {
        return "DropConfig{" + "dropConfig=" + dropConfig + '}';
    }

}
