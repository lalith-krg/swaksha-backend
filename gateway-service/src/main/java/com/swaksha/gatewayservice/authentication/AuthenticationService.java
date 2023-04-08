package com.swaksha.gatewayservice.authentication;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final String ACCOUNT_SID = "AC055a42070a8342f484b3ac015953bedd"; //System.getenv("ACbc91ba0b1ce5b5020130385133c2ae46");
    private static final String AUTH_TOKEN = "d0c63bce9f18a08423c7dee94fb6df9d"; //System.getenv("aa2cab81ac3f795ce2280dbe04659ef1");
   public void create_service() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        com.twilio.rest.verify.v2.Service service = com.twilio.rest.verify.v2.Service.creator("My First Verify Service").create();
        System.out.println("Verification Service Created!");
        System.out.println(service.getSid());
        String service_sid = service.getSid();
    }
    public String send_otp(String mobile_number) {

        System.out.println(mobile_number);
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Verification verification = Verification.creator(
                        "VAb0ebd02b7c2e66266cc94e44208f208e",
                        "+91"+mobile_number,
                        "sms")
                .create();
        System.out.println(verification.getStatus());
        return verification.getStatus();
    }
    public String verify_otp(String otp, String mobile_number) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        VerificationCheck verificationCheck;
//            verificationCheck = VerificationCheck.creator("VA0ad0491033a154559fb200f2d3e155a6", otp)
            verificationCheck = VerificationCheck.creator("VAb0ebd02b7c2e66266cc94e44208f208e")
                    .setTo("+91" + mobile_number)
                    .setCode(otp)
                    .create();


        return verificationCheck.getStatus();
    }
}