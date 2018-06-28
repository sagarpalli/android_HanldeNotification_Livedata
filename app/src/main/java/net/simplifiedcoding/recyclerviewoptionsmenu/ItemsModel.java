package net.simplifiedcoding.recyclerviewoptionsmenu;

/**
 * Created by Sekhar on 6/15/18.
 */

public class ItemsModel {
    String name;
    String quantity;
    String tablename;

    public ItemsModel(String name, String quantity, String tablename) {
        this.name = name;
        this.quantity = quantity;
        this.tablename = tablename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }
}
