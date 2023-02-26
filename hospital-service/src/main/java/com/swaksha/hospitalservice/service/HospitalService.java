package com.swaksha.hospitalservice.service;

import com.swaksha.hospitalservice.entity.Patient;
import com.swaksha.hospitalservice.repository.HospitalEHRRepo;
import com.swaksha.hospitalservice.repository.HospitalPatientRepo;
import com.swaksha.hospitalservice.repository.HospitalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepo hospitalRepo;
    @Autowired
    private HospitalPatientRepo hospitalPatientRepo;
    @Autowired
    private HospitalEHRRepo hospitalEHRRepo;

    public boolean registerPatient(Patient patient) {
        hospitalPatientRepo.save(patient);
        return true;
    }

    public boolean requestEHRFromHIP(Patient patient) {
        return true;
    }

    public boolean transferEHRFromHIP(Patient patient) {
        return true;
    }

    public boolean consentNotifyHIP(Patient patient) {
        return true;
    }

    public boolean consentRequestFromHIU(Patient patient) {
        return true;
    }

    public boolean consentNotifyHIU(Patient patient) {
        return true;
    }

    public boolean confirmConsentRequestFromHIU(Patient patient) {
        return true;
    }

    public boolean receiveTransfer(Patient patient) {
        return true;
    }
}
