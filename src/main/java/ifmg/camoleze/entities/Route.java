package ifmg.camoleze.entities;

import ifmg.camoleze.requirements.RequiredAttributes;

public class Route extends RequiredAttributes {
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
    public String toString() {
        return "(Rota " + id + ", " + distance + " km)";
    }
}
