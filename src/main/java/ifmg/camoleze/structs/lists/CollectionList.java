package ifmg.camoleze.structs.lists;

import ifmg.camoleze.requirements.RequiredMethods;

@SuppressWarnings("unchecked")
public class CollectionList<T extends RequiredMethods> extends ArrayList<T> implements RequiredMethods {

    public CollectionList() {
        super();
    }

    @Override
    public String showInGraph() {
        StringBuilder buffer = new StringBuilder();
        int i = 0;
        while (this.elements[i] != null) {
            T element = (T) this.elements[i++];
            buffer.append(element.showInGraph());
        }
        return buffer.toString();
    }

    public static <T extends RequiredMethods> CollectionList<T> newInstance() {
        return new CollectionList<>();
    }

}
