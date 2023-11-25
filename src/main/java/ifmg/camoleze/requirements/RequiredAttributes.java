package ifmg.camoleze.requirements;

public abstract class RequiredAttributes {
    protected int id;

    protected RequiredAttributes(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
