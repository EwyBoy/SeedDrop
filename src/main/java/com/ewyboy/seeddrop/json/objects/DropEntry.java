package com.ewyboy.seeddrop.json.objects;

public class DropEntry {

    private String item;
    private double chance;

    public DropEntry() {}

    public DropEntry(String item, double chance) {
        this.item = item;
        this.chance = chance;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    @Override
    public String toString() {
        return "DropEntry{" + "item='" + item + '\'' + ", chance=" + chance + '}';
    }

}
