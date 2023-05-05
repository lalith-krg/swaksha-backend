package com.swaksha.gatewayservice.auth;

import com.swaksha.gatewayservice.entity.*;

import com.swaksha.gatewayservice.repository.APIRepo;
import com.swaksha.gatewayservice.repository.HospitalUrlRepo;
import com.swaksha.gatewayservice.repository.PatientCredRepo;
import com.swaksha.gatewayservice.repository.PatientRepo;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;

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

import java.util.List;
import java.util.UUID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Service
@RequiredArgsConstructor
public class AuthService {


    private final PatientCredRepo repository;
    private final PatientRepo prepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final HospitalUrlRepo hospitalUrlRepo;
    private final APIRepo arepository;


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


    public String assignAPIKey(String ssid){
        String api_key = AuthService.createAPIKey();
        String api_key_hashed = AuthService.generateSHA256(api_key);
        ApiKeys key = new ApiKeys();
        key.setSsid(ssid);
        key.setApiKey(api_key_hashed);
        arepository.save(key);
        return api_key;
    }

    public boolean verifyAPIKey(String ssid, String api_key){
        List<ApiKeys> keys = arepository.findBySsid(ssid);
        if(keys.size() > 0 && AuthService.verifySHA256(api_key, keys.get(0).getApiKey())){
            return true;
        }
        return false;
    }

    public static String createAPIKey(){
        return UUID.randomUUID().toString();
    }

    public static String generateSHA256(String message) {
        try {
          MessageDigest digest = MessageDigest.getInstance("SHA-256");
          byte[] hash = digest.digest(message.getBytes());
          StringBuffer hexString = new StringBuffer();
          for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
          }
          return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
          throw new RuntimeException(e);
        }
    }

    public static boolean verifySHA256(String message, String hash) {
        return generateSHA256(message).equals(hash);
    }

}
