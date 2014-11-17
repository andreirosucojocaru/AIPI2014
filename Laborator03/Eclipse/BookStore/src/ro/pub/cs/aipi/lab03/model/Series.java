package ro.pub.cs.aipi.lab03.model;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;

public class Series extends Model {

    final private SimpleStringProperty id;
    final private SimpleStringProperty name;
    final private SimpleStringProperty description;

    public Series(String id, String name, String description) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
    }

    public Series(ArrayList<String> collection) {
        this.id = new SimpleStringProperty(collection.get(0));
        this.name = new SimpleStringProperty(collection.get(1));
        this.description = new SimpleStringProperty(collection.get(2));
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    @Override
    public ArrayList<String> getValues() {
        ArrayList<String> values = new ArrayList<>();
        values.add(id.get());
        values.add(name.get());
        values.add(description.get());
        return values;
    }
}
