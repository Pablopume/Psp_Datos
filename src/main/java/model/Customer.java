package model;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Customer {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private LocalDate dob;
}
