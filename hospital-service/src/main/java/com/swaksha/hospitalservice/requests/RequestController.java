package com.swaksha.hospitalservice.requests;

import com.swaksha.hospitalservice.entity.Ehr;
import com.swaksha.hospitalservice.entity.Patient;
import com.swaksha.hospitalservice.repository.EhrRepo;
import com.swaksha.hospitalservice.repository.PatientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hospital/requests")
@RestController
@CrossOrigin(origins= {"*"})
public class RequestController {

    private final RestTemplate restTemplate = new RestTemplateBuilder().build();

    private final RequestService requestService;

    private final PatientRepo patientRepo;

    private final EhrRepo ehrRepo;
    public record ConsentObj(String doctorSSID, String hiuSSID, String patientSSID, String hipSSID,
                             LocalDate dataAccessStartDate, LocalDate dataAccessEndDate,
                             LocalDate requestInitiatedDate, LocalDate consentApprovedDate,
                             LocalDate consentEndDate, String consentID, boolean selfConsent, boolean isApproved) {
    }


    record EhrData(String data,String patientSSID){}
    record HiuPlaceRequestWithConsent(String docSSID, String patientSSID, String consentID){}

    record HiuRequestBody(String hipSSID,String patientSSID,String doctorSSID) {}

    record HiuRequestWithConsent(String docSSID, String hiuSSID, String patientSSID, ConsentObj consentObj,
                                 String dataPostUrl){}

    record OnHiuRequestBody(String response, String docSSID, String hiuSSID){}


    record HipRequestBody(ConsentObj consentObj, String dataPostUrl){}

    record VerifyConsentResponse(String response){}

    record VerifyConsentBody(String reqSSID, ConsentObj consentObj){}

    record OnVerifyConsentBody(String response, String reqSSID, ConsentObj consentObj){}

    @PostMapping("/demo")
    public String demo(){
        return "hello !";
    }
    @PostMapping("/newRequest")
    public HttpEntity<OnHiuRequestBody> hiuRequest(@RequestBody HiuRequestBody hiuPlaceRequest, Authentication authentication) {


        String ssid= authentication.getName();
//        System.out.println(hiuPlaceRequest.docSSID);
//        System.out.println(hiuPlaceRequest.patientSSID);
        // call /gateway/request/hiu/request
        String url = "http://localhost:9005/gateway/request/hiu/request";


        HiuRequestBody newHiuRequestBody=new HiuRequestBody(hiuPlaceRequest.hipSSID,hiuPlaceRequest.patientSSID,ssid);
        HttpEntity<HiuRequestBody> reqEntity = new HttpEntity<>(newHiuRequestBody);
        //set API key in header .

        ResponseEntity<OnHiuRequestBody> ohr = this.restTemplate.postForEntity(url, reqEntity, OnHiuRequestBody.class);

        return new ResponseEntity<>(ohr.getBody(), HttpStatus.OK);
    }

    @PostMapping("/requestWithConsent")
    public void hiuRequestWithConsentBody(@RequestBody HiuPlaceRequestWithConsent hiuPlaceRequestWithConsent){

        // call /gateway/request/hiu/requestWithConsent
        String url = "http://localhost:8999/gateway/request/hiu/requestWithConsent";

        HttpEntity<HiuRequestWithConsent> reqEntity =
                new HttpEntity<>(new HiuRequestWithConsent(hiuPlaceRequestWithConsent.docSSID, "hiussid",
                        hiuPlaceRequestWithConsent.patientSSID,
                        this.requestService.findConsentWithID(hiuPlaceRequestWithConsent.consentID), "URL"));

        ResponseEntity<OnHiuRequestBody> ohr = this.restTemplate.postForEntity(url, reqEntity, OnHiuRequestBody.class);
    }

    @PostMapping("/getRequestedData")
    public String storeRequestedData(@RequestBody List<EhrData> ehrData)
    {
        Patient patient=patientRepo.findPatientBySsID(ehrData.get(0).patientSSID);
        for(int i=0;i<ehrData.size();i++){
            System.out.println(ehrData.get(i).patientSSID);
            System.out.println(ehrData.get(i).data);
            Ehr ehr=new Ehr();
            ehr.setData(ehrData.get(i).data);
            ehr.setPatient(patient);
            ehrRepo.save(ehr);
        }


    //    Ehr.builder().data(ehrData.getData()).patient(ehrData.getPatient());


        return "data saved";
    }

    @PostMapping("/hip/sendRequest")
    public void hipSendRequest(@RequestBody HipRequestBody hipRequestBody){

        // check if consentObj is valid in gateway
        // call /gateway/request/verifyRequest
        String url = "http://localhost:8999/gateway/request/verifyConsent";
        VerifyConsentBody verifyConsentBody = new VerifyConsentBody(hipRequestBody.consentObj.hipSSID,
                hipRequestBody.consentObj);
        HttpEntity<VerifyConsentBody> consentEntity = new HttpEntity<>(verifyConsentBody);
        ResponseEntity<VerifyConsentResponse> vc_re = this.restTemplate.postForEntity(url, consentEntity,
                VerifyConsentResponse.class);

        // if verified send data
        if(Objects.equals(Objects.requireNonNull(vc_re.getBody()).response, "Verified")){
            url= hipRequestBody.dataPostUrl();
            List<Ehr> ehrData=ehrRepo.findByPatientSsID(hipRequestBody.consentObj.patientSSID);

            ResponseEntity<String> response=this.restTemplate.postForEntity(url,ehrData,String.class);
            System.out.println(response.getBody());
        }
        else{
            // notify error
        }
    }

}