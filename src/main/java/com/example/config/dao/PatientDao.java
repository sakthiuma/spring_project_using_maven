package com.example.config.dao;

import com.example.config.entity.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientDao extends CrudRepository<Patient, Integer> {
}
