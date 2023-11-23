package ifmg.camoleze.requirements;

public interface RequiredMethods {
    default String showInGraph() {
        return "";
    }

    static <T> T newInstance() {
        return null;
    }
}
