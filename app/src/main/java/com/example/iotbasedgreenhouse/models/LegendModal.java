package com.example.iotbasedgreenhouse.models;

public class LegendModal {


    private String label;
    private String value;

    public LegendModal(String label, String value) {
        this.label = label;
        this.value = value;
    }


    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }
}
