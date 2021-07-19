package com.example.config.service;

import com.example.config.dao.PatientDao;
import com.example.config.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientService {

    @Autowired
    PatientDao patientDao;

    public Patient addPatient(Patient patient) {
         return patientDao.save(patient);
    }

    public List<Patient> getAllPatients() {
        Iterable<Patient> iterable = patientDao.findAll();
        final List<Patient> patients = new ArrayList<>();
        iterable.forEach(patients::add);
        return patients;
    }

    public boolean isPresent(Patient patient) {
        return patientDao.existsById(patient.getPatientId());
    }

    public Optional<Patient> getPatientById(int inId) {
        return patientDao.findById(inId);
    }

    public void deletePatients() {
        patientDao.deleteAll();
    }

    public Patient updatePatient(Patient patient) {
        return patientDao.save(patient);
    }


}
