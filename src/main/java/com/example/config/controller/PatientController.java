package com.example.config.controller;

import com.example.config.entity.Patient;
import com.example.config.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PatientController {
    @Autowired
    PatientService patientService;

    @RequestMapping("/home")
    public String home() {
        return "login";
    }

    @RequestMapping("/addPatient")
    @ResponseBody
    public String submit(@RequestParam("patientName") String patientName) {
        return String.valueOf(patientService.addPatient(new Patient(patientName)).getPatientId());
    }

    @RequestMapping("/getAllPatient")
    @ResponseBody
    public String getAllPatient() {
        return patientService.getAllPatients().toString();
    }

    @RequestMapping("/getPatient/{id}")
    @ResponseBody
    public String getPatientById(@PathVariable("id") int patientId) {
        return patientService.getPatientById(patientId).get().getPatientName();
    }

    @RequestMapping("/deleteAll")
    @ResponseBody
    public void deleteAll() {
        patientService.deletePatients();
    }

    @RequestMapping("/updatePatient/{id}")
    @ResponseBody
    public String updatePatient(@PathVariable("id") int patientId, @RequestParam("name") String name) {
        final Patient patient = patientService.getPatientById(patientId).get();
        patient.setPatientName(name);
        return patientService.updatePatient(patient).toString();
    }
}
