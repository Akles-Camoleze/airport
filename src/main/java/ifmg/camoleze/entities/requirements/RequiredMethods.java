package ifmg.camoleze.entities.requirements;

public interface RequiredMethods {
    default String showInGraph() {
        return "";
    }
}
