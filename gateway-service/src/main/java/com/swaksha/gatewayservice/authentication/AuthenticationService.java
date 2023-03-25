package com.swaksha.gatewayservice.authentication;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private static final String ACCOUNT_SID = "AC9e23964f7e6ddcd3760e5396574cd711"; //System.getenv("ACbc91ba0b1ce5b5020130385133c2ae46");
    private static final String AUTH_TOKEN = "9a7c5c025223412f0d6b1dddfa9cddc6"; //System.getenv("aa2cab81ac3f795ce2280dbe04659ef1");
    public void create_service() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        com.twilio.rest.verify.v2.Service service = com.twilio.rest.verify.v2.Service.creator("My First Verify Service").create();
        System.out.println("Verification Service Created!");
        System.out.println(service.getSid());
        String service_sid = service.getSid();
    }
    public String send_otp(String mobile_number) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Verification verification = Verification.creator(
                        "VA3325a54611ee84c9d47bf02f91e77901",
                        "+91"+mobile_number,
                        "sms")
                .create();
        System.out.println(verification.getStatus());
        return verification.getStatus();
    }
    public String verify_otp(String otp, String mobile_number) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        VerificationCheck verificationCheck = VerificationCheck.creator(
                        "VA3325a54611ee84c9d47bf02f91e77901", otp)
                .setTo("+91"+mobile_number)
                .create();
        System.out.println(verificationCheck.getStatus());
        return verificationCheck.getStatus();
    }
}