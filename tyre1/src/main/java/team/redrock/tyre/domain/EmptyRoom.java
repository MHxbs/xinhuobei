package team.redrock.tyre.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


public class EmptyRoom implements Serializable {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
