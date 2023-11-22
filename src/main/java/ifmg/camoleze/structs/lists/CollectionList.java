package ifmg.camoleze.structs.lists;

import ifmg.camoleze.requirements.RequiredAttributes;
import ifmg.camoleze.requirements.RequiredMethods;
import ifmg.camoleze.structs.lists.ArrayList;

public class CollectionList<T extends RequiredMethods> extends RequiredAttributes implements RequiredMethods {
    private ArrayList<T> collection;

    public CollectionList() {
        super(1);
        this.collection = new ArrayList<T>();
    }

    @Override
    public String showInGraph() {
        StringBuilder buffer = new StringBuilder();
        for (T element : this.collection) {
            buffer.append(element.showInGraph());
        }
        return buffer.toString();
    }

    public ArrayList<T> getCollection() {
        return collection;
    }
}
