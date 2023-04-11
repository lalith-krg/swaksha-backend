package com.swaksha.hospitalservice.service;

import com.swaksha.hospitalservice.config.JwtService;

import com.swaksha.hospitalservice.dto.AuthRequest;
import com.swaksha.hospitalservice.dto.AuthResponse;
import com.swaksha.hospitalservice.dto.RegisterRequest;
import com.swaksha.hospitalservice.entity.DoctorCred;
import com.swaksha.hospitalservice.entity.Doctor;

import com.swaksha.hospitalservice.repository.DoctorCredRepo;
import com.swaksha.hospitalservice.repository.DoctorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final DoctorCredRepo doctorCredRepo;
    private final DoctorRepo doctorRepo;
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
        var user= DoctorCred.builder().
                 ssid(id)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        doctorCredRepo.save(user);
        var jwtToken=jwtService.generateToken(user);

        var user1=Doctor.builder().
                ssid(id)
                .address(request.getAddress())
                .phone_number(request.getPhone_number())
                .city(request.getCity())
                .state(request.getState())
                .firstName(request.getFirst_name())
                .lastName(request.getLast_name())
                .gender(request.getGender())
                .email(request.getEmail()).build();
        doctorRepo.save(user1);
        return AuthResponse.builder().
                token(jwtToken)
                .ssid(id)
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {
    //    var user=repository.findBySsid(request.getSsid());


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getSsid(),request.getPassword())
        );

      //  if(user.isEmpty())user=repository.findByAbhaId(request.getAbhaId()).orElseThrow();

        var user=doctorCredRepo.findBySsid(request.getSsid()).orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        return AuthResponse.builder().
                token(jwtToken)
                .build();
    }
}
