package com.aariyan.scannloading.Model;

public class QueueModel {
    private int id;
    private String type;
    private String instructions;

    public QueueModel(){}

    public QueueModel(int id, String type, String instructions) {
        this.id = id;
        this.type = type;
        this.instructions = instructions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
