package com.swaksha.gatewayservice.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {


    private String ssid;
    private String password;

    public String getPassword() {
        return this.password;
    }
}
