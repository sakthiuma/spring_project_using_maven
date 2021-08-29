package com.example.config.controller;

import com.example.config.entity.Patient;
import com.example.config.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class PatientController {
    @Autowired
    PatientService patientService;

    @RequestMapping("/home")
    public String home(Model model) {
        model.addAttribute("patient", new Patient());
        return "login";
    }

    @RequestMapping(value = "/addPatient", method = RequestMethod.POST)
    public ModelAndView submit(@ModelAttribute("patient") Patient inPatient) {
        final ModelAndView mv= new ModelAndView("Welcome");
        System.out.println(inPatient.toString());
        mv.addObject("name", inPatient.getPatientName());
        patientService.addPatient(inPatient);
        return mv;
    }

    @RequestMapping("/getAllPatient")
    @ResponseBody
    public ModelAndView getAllPatient() {
        patientService.getAllPatients().toString();
        ModelAndView mv = new ModelAndView("displayrecords");
        final List<Patient> patientList = patientService.getAllPatients();
        patientList.stream().forEach(System.out::println);
        mv.addObject("patientList", patientList);
        return mv;
    }

    @RequestMapping("/getPatient/{id}")
    @ResponseBody
    public ModelAndView getPatientById(@PathVariable("id") int patientId) {
        final Optional<Patient> patientOptional = patientService.getPatientById(patientId);
        if (patientOptional.isPresent()) {
            Patient patient = patientService.getPatientById(patientId).get();
            final ModelAndView mv= new ModelAndView("Welcome");
            System.out.println(patient.toString());
            mv.addObject("name", patient.getPatientName());
            return mv;
        } else {
            final ModelAndView mv = new ModelAndView("error");
            mv.addObject("errormsg" , "Invalid user record");
            return mv;
        }

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
