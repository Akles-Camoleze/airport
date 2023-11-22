package ifmg.camoleze.entities;

import ifmg.camoleze.entities.requirements.RequiredAttributes;
import ifmg.camoleze.entities.requirements.RequiredMethods;

public class Route extends RequiredAttributes implements RequiredMethods {
    private Integer distance;

    public Route(int id, int distance) {
        super(id);
        this.distance = distance;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    @Override
    public String showInGraph() {
        return this.distance.toString();
    }

    @Override
    public String toString() {
        return "Route{" +
                "distance=" + distance +
                ", id=" + id +
                '}';
    }
}
