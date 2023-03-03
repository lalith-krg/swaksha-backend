package com.swaksha.consentmanagerservice.consents;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/cm/consents")
public class ConsentController {

    // HIU places consent request
    @PostMapping("/hiu/request")
    public void hiuConsentRequest(String SSID){
        //
    }

    // Consent Manager's reply to consent request by hiu
    @PostMapping("/hiu/on-request")
    public void hiuConsentOnRequest(String SSID){
        //
    }

    // HIU places consent request
    @PostMapping("/hiu/confirm-request")
    public void hiuConfirmRequest(String SSID){
        //
    }

    // HIP fetches consent request
    @PostMapping("/hip/fetch")
    public void hipFetchConsentRequest(String SSID){
        //
    }

    // HIP consent notification
    @PostMapping("/hip/on-notify")
    public void hipConsentNotify(String SSID){
        //
    }

    // HIU consent notification
    @PostMapping("/hiu/on-notify")
    public void hiuConsentNotify(String SSID){
        //
    }

    // HIU requesting status on consent
    @PostMapping("/hiu/status")
    public void hiuConsentStatus(String SSID){
        //
    }

    // Consent Manager's reply to consent request
    @PostMapping("/hiu/on-status")
    public void hiuConsentOnStatus(String SSID){
        //
    }
}
