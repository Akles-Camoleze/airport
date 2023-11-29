package ifmg.camoleze.entities;

import ifmg.camoleze.requirements.RequiredAttributes;

import java.math.BigDecimal;

public class Route extends RequiredAttributes {
    private BigDecimal distance;

    public Route(int id, BigDecimal distance) {
        super(id);
        this.distance = distance;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "(Rota " + id + ", " + distance + " km)";
    }
}
