package ru.dancebunny98;

import java.util.List;

public class PluginConfig {
    private List<String> worldNames;
    private int x;
    private int y;
    private int z;
    private float yaw;
    private float pitch;

    private List<String> allowedMaterialsInventory;

    public List<String> getWorldNames() {
        return worldNames;
    }

    public void setWorldNames(List<String> worldNames) {
        this.worldNames = worldNames;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public List<String> getAllowedMaterialsInventory() {
        return allowedMaterialsInventory;
    }

    public void setAllowedMaterialsInventory(List<String> allowedMaterialsInventory) {
        this.allowedMaterialsInventory = allowedMaterialsInventory;
    }
    private List<String> allowedMaterials;

    public List<String> getAllowedMaterials() {
        return allowedMaterials;
    }

    private double minY;

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }
    public void setAllowedMaterials(List<String> allowedMaterials) {
        this.allowedMaterials = allowedMaterials;
    }
}
