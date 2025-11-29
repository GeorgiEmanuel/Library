package view.model;

import com.sun.javafx.beans.IDProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserDTO {
    private StringProperty username;

    public void setUsername(String username) { usernameProperty().set(username); }

    public String getUsername(){ return usernameProperty().get();}

    public StringProperty usernameProperty(){
        if (username == null){
            username = new SimpleStringProperty(this, "username");
        }
        return username;
    }

    private LongProperty id;

    public void setId(Long id){ idProperty().set(id); }

    public Long getId() { return idProperty().get(); }

    public LongProperty idProperty() {
        if (id == null) {
            id = new SimpleLongProperty(this, "id");
        }
        return id;
    }
}
