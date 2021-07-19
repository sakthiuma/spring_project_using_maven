import com.example.config.dao.PatientDao;
import com.example.config.entity.Patient;
import com.example.config.service.PatientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PatientServiceSaTestSuite {

    @InjectMocks
    PatientService patientService;

    @Mock
    PatientDao patientDao;

    @Test
    public void testAddPatient() {
        final Patient patient = new Patient("ram");
        patientService.addPatient(patient);
        verify(patientDao, times(1)).save(patient);
    }

    @Test
    public void testAllPatient() {
        final List<Patient> patients = new ArrayList<>();
        final Patient patient1 = new Patient("ram");
        final Patient patient2 = new Patient("leela");
        final Patient patient3 = new Patient("ramesh");

        patients.add(patient1);
        patients.add(patient2);
        patients.add(patient3);

        given(patientDao.findAll()).willReturn(patients);

        final List<Patient> patients1 = patientService.getAllPatients();

        assertEquals(3, patients1.size());
        verify(patientDao, times(1)).findAll();
    }

    @Test
    public void testGetPatientById() {
        final Patient patient = new Patient("ram");
        given(patientDao.findById(patient.getPatientId())).willReturn(java.util.Optional.of(patient));
        final Patient patient1 = patientService.getPatientById(patient.getPatientId()).get();
        assertEquals(patient, patient1);
        verify(patientDao, times(1)).findById(patient.getPatientId());
    }

    @Test
    public void testGetInvalidPatientId() {
        given(patientDao.findById(1)).willReturn(Optional.ofNullable(null));
        final Optional<Patient> patient1 = patientService.getPatientById(1);
        assertFalse(patient1.isPresent());
    }

    @Test
    public void testDeletePatient() {
        final Patient patient = new Patient("ram");
        doNothing().when(patientDao).delete(patient); // there is no patient record in the DB.

        given(patientDao.existsById(patient.getPatientId())).willReturn(false);
        assertFalse(patientService.isPresent(patient));

    }

    //todo is this way of checking right? it doesnt feel like it
    @Test
    public void testEditPatientDetails() {
        final Patient patient = new Patient("vinodh");
        patientService.addPatient(patient);

        patient.setPatientName("vignesh");
        patientService.addPatient(patient);
        given(patientDao.findAll()).willReturn(Arrays.asList(patient));

        final List<Patient> patients1 = patientService.getAllPatients();
        assertEquals(1, patients1.size());
        verify(patientDao, times(2)).save(patient);
        verify(patientDao, times(1)).findAll();
    }

    @Test
    public void testDeleteAllPatients() {
        final Patient patient1 = new Patient("vinodh");
        patientService.addPatient(patient1);
        final Patient patient2 = new Patient("vinodhini");
        patientService.addPatient(patient1);
        final Patient patient3 = new Patient("vimala");
        patientService.addPatient(patient1);

        doNothing().when(patientDao).deleteAll();

        given(patientDao.findById(1)).willReturn(Optional.ofNullable(null));
        final Optional<Patient> patient4 = patientService.getPatientById(1);
        assertFalse(patient4.isPresent());
    }

}
