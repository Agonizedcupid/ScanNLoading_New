package com.aariyan.scannloading.Model;

public class PostLinesModel {
    private int ids;
    private String types,instrunctions;

    public PostLinesModel(){}

    public PostLinesModel(int ids, String types, String instrunctions) {
        this.ids = ids;
        this.types = types;
        this.instrunctions = instrunctions;
    }

    public int getIds() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getInstrunctions() {
        return instrunctions;
    }

    public void setInstrunctions(String instrunctions) {
        this.instrunctions = instrunctions;
    }
}
