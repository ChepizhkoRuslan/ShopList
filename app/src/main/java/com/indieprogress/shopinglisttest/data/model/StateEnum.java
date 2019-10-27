package com.indieprogress.shopinglisttest.data.model;

import androidx.annotation.NonNull;

public enum StateEnum {
    ALL("All"),
    NOT_BOUGHT("Not bought"),
    BOUGHT("Bought");
    private String state;

    StateEnum(String state) {
        this.state = state;
    }

    @NonNull
    public String toString() {
        return state;
    }
}
