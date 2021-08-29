package com.example.config.entity;

import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "patient")
@EqualsAndHashCode
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    int patientId;

    @Column
    String patientName;

    public Patient() {

    }

    public Patient(String patientName) {
        //this.patientId = patientId;
        this.patientName = patientName;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId=" + patientId +
                ", patientName='" + patientName + '\'' +
                '}';
    }
}
