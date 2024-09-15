package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SalaryGraphDTO implements Serializable {
    List<String> profiles = new ArrayList<>();
    List<Double> averagesIncomes = new ArrayList<>();

    public List<String> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<String> profiles) {
        this.profiles = profiles;
    }

    public List<Double> getAveragesIncomes() {
        return averagesIncomes;
    }

    public void setAveragesIncomes(List<Double> averagesIncomes) {
        this.averagesIncomes = averagesIncomes;
    }
}
