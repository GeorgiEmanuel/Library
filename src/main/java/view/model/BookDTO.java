package view.model;


import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.sound.midi.spi.MidiDeviceProvider;

public class BookDTO {
    private StringProperty author;

    public void setAuthor(String author) {
        authorProperty().set(author);
    }

    public String getAuthor(){
        return authorProperty().get();
    }
    public StringProperty authorProperty(){
        if (author == null){
            author = new SimpleStringProperty(this, "author");
        }
        return author;
    }


    private StringProperty title;

    public void setTitle(String title){
        titleProperty().set(title);
    }

    public String getTitle(){
        return  titleProperty().get();
    }

    public StringProperty titleProperty(){
        if (title == null){
            title = new SimpleStringProperty(this, "title");
        }
        return title;
    }

    private LongProperty quantity;

    public void setQuantity(Long quantity) { quantityProperty().set(quantity);}

    public Long getQuantity() { return quantityProperty().get(); }

    public LongProperty quantityProperty(){
        if (quantity == null){
            quantity = new SimpleLongProperty(this, "quantity");
        }
        return quantity;
    }

    private LongProperty price;
    public void setPrice(Long price) { priceProperty().set(price); }

    public Long getPrice() { return priceProperty().get();}

    public LongProperty priceProperty() {
        if (price == null){
            price = new SimpleLongProperty(this, "price");
        }
        return price;
    }

    public void decrementQuantity(){
        this.setQuantity(this.getQuantity() - 1);
    }

    private LongProperty id;
    public void setId(Long id) { idProperty().set(id); }

    public Long getId() { return idProperty().get(); }

    public LongProperty idProperty(){
        if (id == null){
            id = new SimpleLongProperty(this, "id");
        }
        return id;
    }

}
