package model;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Data
public class Order {
    private int id;
    private LocalDateTime date;
    private int customer_id;
    private int table_id;


    public Order(String fileLine) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String[] elemArray = fileLine.split(";");
        this.id = Integer.parseInt(elemArray[0]);
        this.date = LocalDateTime.parse(elemArray[1], formatter);
        this.customer_id = Integer.parseInt(elemArray[2]);
        this.table_id = Integer.parseInt(elemArray[3]);

    }

    public String toStringTextFile() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return id + ";" + date.format(formatter) + ";" + customer_id + ";" + table_id;
    }
}
