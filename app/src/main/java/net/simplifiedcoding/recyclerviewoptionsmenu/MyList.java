package net.simplifiedcoding.recyclerviewoptionsmenu;

/**
 * Created by Belal on 05/10/16.
 */
public class MyList {
    private String head;
    private String quantity;
    private String id;
    private String tablename;
    //constructor initializing values
    public MyList(String head, String quantity,String tablename) {
        this.head = head;
        this.quantity = quantity;
        this.tablename=tablename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //getters
    public String getHead() {
        return head;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }
}
