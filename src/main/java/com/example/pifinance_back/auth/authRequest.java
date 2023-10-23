package com.example.pifinance_back.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class authRequest {
    private String email;
    private String pwd_user;

}
