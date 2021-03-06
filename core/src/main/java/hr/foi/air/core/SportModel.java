package hr.foi.air.core;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class SportModel implements Serializable {

    private int id;
    private String name;

    public SportModel() {
    }

    public SportModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
