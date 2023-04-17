package com.swaksha.adminservice.controller;


import com.swaksha.adminservice.entity.Hospital;
import com.swaksha.adminservice.repository.HospitalRepository;
import com.swaksha.adminservice.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/hospitals")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HospitalController {

//    @Autowired
//    private HospitalService hospitalService;

    private final HospitalRepository hospitalRepository;

    public HospitalController(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @GetMapping("/")
    public List<Hospital> getAllHospitals () {

        System.out.println("reached here");
        for (Hospital h : hospitalRepository.findAll()) {
            System.out.println(h.getHospital_name());
        }
        System.out.println("reached here lmao");
        return hospitalRepository.findAll();
    }

//    public

}
