package hr.foi.air.sportloc.service.model;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class LocationModel implements Serializable {

    private Integer id;
    private String name;

    public LocationModel() {
    }

    public LocationModel(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
