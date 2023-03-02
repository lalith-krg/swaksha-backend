package com.swaksha.gatewayservice.request;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gateway/request")
public class RequestController {

    // HIU places consent request
    @PostMapping("/hiu/request")
    public void hiuRequestRequest(String SSID){
        //
    }

    // Request Manager's reply to consent request by hiu
    @PostMapping("/hiu/on-request")
    public void hiuRequestOnRequest(String SSID){
        //
    }

    // HIU places consent request
    @PostMapping("/hiu/confirm-request")
    public void hiuConfirmRequest(String SSID){
        //
    }

    // HIP fetches consent request
    @PostMapping("/hip/fetch")
    public void hipFetchRequestRequest(String SSID){
        //
    }

    // HIP consent notification
    @PostMapping("/hip/on-notify")
    public void hipRequestNotify(String SSID){
        //
    }

    // HIU consent notification
    @PostMapping("/hiu/on-notify")
    public void hiuRequestNotify(String SSID){
        //
    }

    // HIU requesting status on consent
    @PostMapping("/hiu/status")
    public void hiuRequestStatus(String SSID){
        //
    }

    // Request Manager's reply to consent request
    @PostMapping("/hiu/on-status")
    public void hiuRequestOnStatus(String SSID){
        //
    }
}
