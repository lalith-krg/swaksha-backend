package com.swaksha.patientservice.controller;

import com.swaksha.patientservice.entity.Patient;
import com.swaksha.patientservice.entity.PatientCred;
import com.swaksha.patientservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;



import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/patient")
@CrossOrigin(origins= {"*"})
public class PatientController {

//    private RestOperations restTemplate;
    private final RestTemplate restTemplate = new RestTemplateBuilder().build();
    record ConsentObj(String doctorSSID, String hiuSSID, String patientSSID, String hipSSID,
                      String dataAccessStartDate, String dataAccessEndDate,
                      String requestInitiatedDate, String consentApprovedDate,
                      String consentEndDate, String consentID, boolean selfConsent, boolean isApproved){}


    record ApproveConsentBody( String encPin, ConsentObj consentObj){}


    record ApproveConsentResponse(String response){}

    record VerifyConsentResponse(String response){}

    record VerifyConsentBody(String reqSSID, ConsentObj consentObj){}

    record RevokeConsentBody (String consentID, String reqSSID) {}

    record OnFetchConsentsBody(String SSID, ArrayList<ConsentObj> consentObjs){}

    record OnFetchConsentsResponse(String response, String SSID, ArrayList<ConsentObj> consentObjs){}

    public record PatientSSIDBody(String patientSSID){}

    @Autowired
    private final PatientService patientService;

    @PostMapping(path="/register/mobile")
//    public ResponseEntity<Patient> registerMobile(@RequestBody Patient newPatient, @RequestBody PatientCred patientCred){
    public ResponseEntity<Patient> registerMobile(@RequestBody Patient newPatient){
        Patient patient = patientService.registerMobile(newPatient);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<String> register( HttpEntity<SendOtp> request){
        String url="http://localhost:9005/api/v1/auth/send-otp";
        RestTemplate restTemplate=new RestTemplate();

        ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.POST,request,String.class);
        return response;

    }
    @PostMapping("/verify-otp")
    public ResponseEntity<AuthResponse> verifyOtp(HttpEntity<RegisterRequest> request) {
        String url="http://localhost:9005/api/v1/auth/verify-otp";
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<AuthResponse> response =restTemplate.exchange(url, HttpMethod.POST,request,AuthResponse.class);
        return response;
    }



    @PostMapping(path="/register/govId")
//    public ResponseEntity<Patient> registerGovId(@RequestBody Patient newPatient, @RequestBody PatientCred patientCred){
    public ResponseEntity<Patient> registerGovId(@RequestBody Patient newPatient){
        Patient patient = patientService.registerGovId(newPatient);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PostMapping(path="/logout")
//    public ResponseEntity<Patient> registerGovId(@RequestBody Patient newPatient, @RequestBody PatientCred patientCred){
    public void logout(HttpEntity<String> request){

        String url="http://localhost:9005/api/v1/auth/logout";
        RestTemplate restTemplate=new RestTemplate();

        ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.POST,request,String.class);

    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(HttpEntity<AuthRequest> request) {
        //    var user=repository.findBySsid(request.getSsid());

        //  if(user.isEmpty())user=repository.findByAbhaId(request.getAbhaId()).orElseThrow();

        String url="http://localhost:9005/api/v1/auth/authenticate";
        RestTemplate restTemplate=new RestTemplate();
        System.out.println(request.getBody().getSsid());

        ResponseEntity< AuthResponse>response =restTemplate.exchange(url, HttpMethod.POST,request,AuthResponse.class);
        return response;

    }
    @GetMapping("/demo")
    public ResponseEntity<String> sayHello(HttpEntity<String> request){

        String url="http://localhost:9005/gateway/patient/demo";
        RestTemplate restTemplate=new RestTemplate();

        ResponseEntity< String>response =restTemplate.exchange(url, HttpMethod.GET,request,String.class);
        return response;
    }

    @GetMapping(path ="/dashboard")
    public ResponseEntity<Patient> dashboard(@RequestBody Patient patient){

        boolean response = patientService.dashboard(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path ="/viewProfile")
    public ResponseEntity<Patient> viewProfile(@RequestBody Patient patient){

        boolean response = patientService.viewProfile(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path ="/updateProfile")
    public ResponseEntity<Patient> updateProfile(@RequestBody Patient patient){

        boolean response = patientService.updateProfile(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path ="/deleteProfile")
    public ResponseEntity<Patient> deleteProfile(@RequestBody Patient patient){

        boolean response = patientService.deleteProfile(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path ="/resetPassword")
    public ResponseEntity<Patient> resetPassword(@RequestBody Patient patient){

        boolean response = patientService.resetPassword(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/approveConsent")
    public ResponseEntity<ApproveConsentResponse> approveConsent(HttpEntity<ApproveConsentBody> acb){
        String url = "http://localhost:9005/gateway/request/approveConsent";
        RestTemplate restTemplate1=new RestTemplate();

        ResponseEntity<ApproveConsentResponse> response=restTemplate1.exchange(url, HttpMethod.POST,acb,ApproveConsentResponse.class);
        return response;
    }

    @PostMapping(path = "/rejectConsent")
    public String rejectConsent(@RequestBody RevokeConsentBody rcb){

        String url = "http://localhost:9005/gateway/request/rejectConsent";
        ResponseEntity<VerifyConsentResponse> vcr = this.restTemplate.postForEntity(url, rcb,
                VerifyConsentResponse.class);

        return Objects.requireNonNull(vcr.getBody()).response;

    }

    @PostMapping(path = "/revokeConsent")
    public String revokeConsent(@RequestBody RevokeConsentBody rcb){
        System.out.println("Receive revoke consent from patient");
        System.out.println(rcb.reqSSID);
        System.out.println(rcb.consentID);
        String url = "http://localhost:9005/gateway/request/revokeConsent";
        ResponseEntity<VerifyConsentResponse> vcr = this.restTemplate.postForEntity(url, rcb,
                VerifyConsentResponse.class);

        return Objects.requireNonNull(vcr.getBody()).response;
    }

    @PostMapping("/fetchConsents")
    public ResponseEntity<OnFetchConsentsResponse> fetchConsents( HttpEntity<String> request){

        String url = "http://localhost:9005/gateway/request/fetchConsents";
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<OnFetchConsentsResponse> response=restTemplate.exchange(url, HttpMethod.POST,request,OnFetchConsentsResponse.class);
        return response;
    }


}
