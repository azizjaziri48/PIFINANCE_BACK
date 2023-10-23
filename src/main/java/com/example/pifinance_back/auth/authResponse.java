package com.example.pifinance_back.auth;

import com.example.pifinance_back.Entities.UserEnum;
import lombok.*;

import javax.persistence.Table;

@Data
@Builder
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class authResponse {
    private String token;
    private String welcome;
    private int id_user; // Add this field

    private String message;
    private String errorMessage;
    private UserEnum role ;
    private String firstname ;
    private String lastname ;

    private String cin ;
    public String getFullName() {
        return firstname + " " + lastname;
    }
    // getter and setter methods
}
