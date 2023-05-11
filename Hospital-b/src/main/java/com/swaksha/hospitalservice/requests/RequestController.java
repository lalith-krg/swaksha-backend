package com.swaksha.hospitalservice.requests;

import com.swaksha.hospitalservice.entity.Ehr;
import com.swaksha.hospitalservice.entity.Patient;
import com.swaksha.hospitalservice.repository.EhrRepo;
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
import java.util.ArrayList;
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
    public record ConsentObj1(String doctorSSID, String hiuSSID, String patientSSID, String hipSSID,
                             String dataAccessStartDate, String dataAccessEndDate,
                             String requestInitiatedDate, String consentApprovedDate,
                             String consentEndDate, String consentID, boolean selfConsent, boolean isApproved) {
    }
    public record ConsentObj(String doctorSSID, String hiuSSID, String patientSSID, String hipSSID,
                             LocalDate dataAccessStartDate, LocalDate dataAccessEndDate,
                             LocalDate requestInitiatedDate, LocalDate consentApprovedDate,
                             LocalDate consentEndDate, String consentID, boolean selfConsent, boolean isApproved) {
    }

//    record EhrData(String data,String patientSSID){}
    record HiuPlaceRequest(String patientSSID){}

//    record SendRequestedData(String data,String patientSSID){}

    record SendRequestedData(LocalDate creationDate, String patientSSID,
                             String type, String observationCode, String observationValue,
                             String conditionCode, String procedureCode){}

    record HiuPlaceRequestWithConsent(String docSSID, String patientSSID, String consentID){}

    record HiuRequestBody(String docSSID, String hiuSSID, String patientSSID, String dataPostUrl) {}

    record HiuRequestWithConsent(String docSSID, String hiuSSID, String patientSSID, ConsentObj consentObj, String dataPostUrl){}

    record OnHiuRequestBody(String response, String docSSID, String hiuSSID){}

    record HipRequestBody(ConsentObj consentObj, String dataPostUrl){}

    record VerifyConsentResponse(String response){}

    record VerifyConsentBody(String reqSSID, ConsentObj1 consentObj){}

    record OnVerifyConsentBody(String response, String reqSSID, ConsentObj consentObj){}

    @PostMapping("/demo")
    public String demo(){
        return "hello !";
    }
    @PostMapping("/newRequest")
    public HttpEntity<OnHiuRequestBody> hiuRequest(@RequestBody HiuPlaceRequest hiuPlaceRequest, Authentication authentication) {
            String ssid= authentication.getName();
//        System.out.println(hiuPlaceRequest.docSSID);
//        System.out.println(hiuPlaceRequest.patientSSID);
        // call /gateway/request/hiu/request
        String url = "http://localhost:9005/gateway/request/hiu/request";

        HttpEntity<HiuRequestBody> reqEntity = new HttpEntity<>(new HiuRequestBody(ssid,
                "123456789", hiuPlaceRequest.patientSSID, "URL"));

        ResponseEntity<OnHiuRequestBody> ohr = this.restTemplate.postForEntity(url, reqEntity, OnHiuRequestBody.class);

        return new ResponseEntity<>(ohr.getBody(), HttpStatus.OK);
    }

    @PostMapping("/requestWithConsent")
    public void hiuRequestWithConsentBody(@RequestBody HiuPlaceRequestWithConsent hiuPlaceRequestWithConsent){

        // call /gateway/request/hiu/requestWithConsent
        String url = "http://localhost:9005/gateway/request/hiu/requestWithConsent";

        HttpEntity<HiuRequestWithConsent> reqEntity =
                new HttpEntity<>(new HiuRequestWithConsent(hiuPlaceRequestWithConsent.docSSID, "hiussid",
                        hiuPlaceRequestWithConsent.patientSSID,
                        this.requestService.findConsentWithID(hiuPlaceRequestWithConsent.consentID), "URL"));

        ResponseEntity<OnHiuRequestBody> ohr = this.restTemplate.postForEntity(url, reqEntity, OnHiuRequestBody.class);
    }

//    @PostMapping("/getRequestedData")
//    public String storeRequestedData(@RequestBody ArrayList<EhrData> ehrData)
//    {
//        if(ehrData.size()<0){
//            return "No data.";
//        }
//
//        Patient patient = this.requestService.findPatientById(ehrData.get(0).patientSSID);
//        for(int i=0;i<ehrData.size();i++){
//            System.out.println(ehrData.get(i).patientSSID);
//            System.out.println(ehrData.get(i).data);
//            Ehr ehr=new Ehr();
//            ehr.setData(ehrData.get(i).data);
//            ehr.setPatient(patient);
//            this.requestService.save(ehr);
//        }
//
//
//        //    Ehr.builder().data(ehrData.getData()).patient(ehrData.getPatient());
//
//
//        return "data saved";
//    }

    @PostMapping("/hip/sendRequest")
    public HttpEntity<String> hipSendRequest(@RequestBody HipRequestBody hipRequestBody){
        System.out.println("here i am ");
        // check if consentObj is valid in gateway
        // call /gateway/request/verifyRequest
        ConsentObj consentObj = hipRequestBody.consentObj;

        ConsentObj1 consentObj1 = new ConsentObj1(consentObj.doctorSSID, consentObj.hiuSSID, consentObj.patientSSID, consentObj.hipSSID,
                String.valueOf(consentObj.dataAccessStartDate), String.valueOf(consentObj.dataAccessEndDate),
                String.valueOf(consentObj.requestInitiatedDate), String.valueOf(consentObj.consentApprovedDate),
                String.valueOf(consentObj.consentEndDate), consentObj.consentID, consentObj.selfConsent, consentObj.isApproved);

        String url = "http://localhost:9005/gateway/request/verifyConsent";
        VerifyConsentBody verifyConsentBody = new VerifyConsentBody(consentObj1.hipSSID, consentObj1);
        HttpEntity<VerifyConsentBody> consentEntity = new HttpEntity<>(verifyConsentBody);
        System.out.println(hipRequestBody.consentObj.patientSSID.getClass().getName());
        System.out.println(hipRequestBody.consentObj.dataAccessStartDate.getClass().getName());
        System.out.println(hipRequestBody.consentObj.dataAccessEndDate);

        boolean saved = this.requestService.updateConsentObj(Objects.requireNonNull(consentObj));
        System.out.println(saved);

        ResponseEntity<VerifyConsentResponse> vc_re = this.restTemplate.postForEntity(url, consentEntity,
                VerifyConsentResponse.class);


//         if verified send data
        if(Objects.equals(Objects.requireNonNull(vc_re.getBody()).response, "Verified")){
            saved = this.requestService.updateConsentObj(Objects.requireNonNull(consentObj));
            System.out.println(saved);

            url = hipRequestBody.dataPostUrl();

            ArrayList<Ehr> ehrData = this.requestService.findPatientEhrData(hipRequestBody.consentObj.patientSSID,
                    hipRequestBody.consentObj.dataAccessStartDate,
                    hipRequestBody.consentObj.dataAccessEndDate);
            System.out.println(ehrData.size());
            System.out.println(hipRequestBody.consentObj.patientSSID);
            System.out.println(hipRequestBody.consentObj.dataAccessStartDate);
            System.out.println(hipRequestBody.consentObj.dataAccessEndDate);
         //   ehrData=ehrRepo
            ArrayList<SendRequestedData> data = new ArrayList<>();

            for (Ehr ehrDatum : ehrData) {
//                SendRequestedData sendRequestedData = new SendRequestedData(ehrDatum.getData(), ehrDatum.getPatient().getSsID());
                SendRequestedData sendRequestedData = new SendRequestedData(
                        ehrDatum.getCreationDate(),
                        ehrDatum.getPatient().getSsid(),
                        ehrDatum.getType(),
                        ehrDatum.getObservationCode(),
                        ehrDatum.getObservationValue(),
                        ehrDatum.getConditionCode(),
                        ehrDatum.getProcedureCode());
                data.add(sendRequestedData);
//                System.out.println(ehrDatum.getData());
            }

            if(ehrData.size()>0)
                System.out.println(ehrData.get(0).getPatient().getSsid());
            else
                System.out.println("No data records available");

            ResponseEntity<String> response=this.restTemplate.postForEntity(url,data,String.class);
            System.out.println(response.getBody());
        }
        else{
            // notify error

        }
        return new HttpEntity<>("data sent");
    }

    @PostMapping("/consentUpdate")
    public HttpEntity<Boolean> consentUpdate(@RequestBody ConsentObj consentObj){
        boolean update = this.requestService.updateConsentObj(consentObj);
        return new HttpEntity<>(update);
    }

}