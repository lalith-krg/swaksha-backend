package com.swaksha.gatewayservice.auth;

import com.swaksha.gatewayservice.entity.Role;
import com.swaksha.gatewayservice.repository.PatientCredRepo;
import com.swaksha.gatewayservice.repository.PatientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.swaksha.gatewayservice.entity.Patient;
import com.swaksha.gatewayservice.entity.PatientCred;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import com.swaksha.gatewayservice.config.JwtService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final PatientCredRepo repository;
    private final PatientRepo prepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public static String generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        String res=new String(digits);
        return res;
    }
    public AuthResponse register(RegisterRequest request) {

        
        String id=generateRandom(12);
        var user= PatientCred.builder().
                 ssid(id)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        repository.save(user);
        var jwtToken=jwtService.generateToken(user);

        var user1=Patient.builder().
                ssid(id)
                .address(request.getAddress())
                .phone_number(request.getPhone_number())
                .city(request.getCity())
                .state(request.getState())
                .first_name(request.getFirst_name())
                .last_name(request.getLast_name())
                .gender(request.getGender())
                .cmPinPassword(request.getCmPinPassword())
                .email(request.getEmail()).build();
        prepository.save(user1);
        return AuthResponse.builder().
                token(jwtToken)
                .ssid(id)
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {
    //    var user=repository.findBySsid(request.getSsid());
      //  System.out.println(request.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getSsid(),request.getPassword())
        );

      //  if(user.isEmpty())user=repository.findByAbhaId(request.getAbhaId()).orElseThrow();

        var user=repository.findBySsid(request.getSsid()).orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        return AuthResponse.builder().
                token(jwtToken)
                .build();
    }
}
