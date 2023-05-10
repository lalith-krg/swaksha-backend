package com.swaksha.gatewayservice.firebase.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swaksha.gatewayservice.firebase.service.NotificationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    record Notification(String token, String title, String body){}

    @PostMapping("/send")
    public void sendNotificationController(@RequestBody Notification notification){
        this.notificationService.sendNotification(notification.token, notification.title, notification.body);
        System.out.println("Hello");
    }
}
