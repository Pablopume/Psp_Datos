package model;

import lombok.Data;


import java.time.LocalDateTime;

@Data
public class Order {
    private int id;
    private LocalDateTime date;
    private int customer_id;
    private int table_id;

    public Order(int id, LocalDateTime date, int customer_id, int table_id) {
        this.id = id;
        this.date = date;
        this.customer_id = customer_id;
        this.table_id = table_id;
    }
}
