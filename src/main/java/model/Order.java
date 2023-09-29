package model;

import lombok.Data;


import java.time.LocalDate;
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

    public Order(String fileLine){
        String [] elemArray =fileLine.split(";");
        this.id=Integer.parseInt(elemArray[0]);
        this.date=LocalDateTime.parse(elemArray[1]);
        this.customer_id=Integer.parseInt(elemArray[2]);
        this.table_id=Integer.parseInt(elemArray[3]);

    }
}
