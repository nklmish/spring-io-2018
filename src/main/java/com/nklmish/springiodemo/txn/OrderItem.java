package com.nklmish.springiodemo.txn;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OrderItem {

    @Id
    private long id;

    private String description;

    public OrderItem() {
    }

    public OrderItem(long id, String description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}