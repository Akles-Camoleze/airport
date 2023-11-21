package ifmg.camoleze.entities;

import java.util.Date;

public class Flight extends RequiredAttributes {
    private Integer number;
    private String airline;
    private Date departure;
    private Date arrival;
    private Integer stops;

    Flight(int id) {
        super(id);
    }

}
