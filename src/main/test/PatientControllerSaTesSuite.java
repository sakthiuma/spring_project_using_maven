import com.example.config.ApplicationConfig;
import com.example.config.controller.PatientController;
import com.example.config.entity.Patient;
import com.example.config.service.PatientService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ApplicationConfig.class})
public class PatientControllerSaTesSuite {
    @Mock
    PatientService patientService;
    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @InjectMocks
    PatientController patientController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
        clearDb();
    }

    private void clearDb() {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("truncate table patient").executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Test
    public void testIndexPage() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void testAddPatient() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        MvcResult result = mockMvc.perform(post("/addPatient?patientName=n"))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println("output" + result.getResponse().getContentAsString());
    }

    @Test
    public void testGetAllPatient() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        mockMvc.perform(post("/addPatient?patientName=Meena"))
                .andExpect(status().isOk())
                .andReturn();
        mockMvc.perform(post("/addPatient?patientName=Reena"))
                .andExpect(status().isOk())
                .andReturn();
        Patient patient1 = new Patient("Meena");
        patient1.setPatientId(1); // this is for testing purpose
        Patient patient2 = new Patient("Reena");
        patient2.setPatientId(2); // this is for testing purpose

        List<Patient> patientList = Arrays.asList(patient1, patient2);
        when(patientService.getAllPatients()).thenReturn(patientList);
        MvcResult result = mockMvc.perform(get("/getAllPatient"))
                .andExpect(status().isOk())
                .andExpect(view().name("displayrecords"))
                .andExpect(model().attribute("patientList", patientList))
                .andReturn();
        assertNotNull(result.getResponse().getContentAsString());
        System.out.println("output" + result.getResponse().getContentAsString());
    }

    @Test
    public void testEmptyPatientList() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        mockMvc.perform(get("/getAllPatient"))
                .andExpect(status().isOk())
                .andExpect(view().name("displayrecords"))
                .andExpect(model().attribute("patientList", Collections.emptyList()))
                .andReturn();
    }

    @Test
    public void testGetPatientById() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        mockMvc.perform(post("/addPatient?patientName=Ram"))
                .andExpect(status().isOk())
                .andReturn();
        MvcResult result = mockMvc.perform(get("/getPatient/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("Welcome"))
                .andExpect(model().attribute("name", "Ram"))
                .andReturn();
        assertNotNull(result.getResponse().getContentAsString());
        System.out.println("output" + result.getResponse().getContentAsString());
    }

    @Test
    public void testInvalidPatientId() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        mockMvc.perform(get("/getPatient/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errormsg", "Invalid user record"))
                .andReturn();
    }

    @Test
    public void testUpdatePatient() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        mockMvc.perform(post("/addPatient?patientName=Ram"))
                .andExpect(status().isOk())
                .andReturn();
        mockMvc.perform(post("/updatePatient/1?name=Rama"))
                .andExpect(status().isOk())
                .andReturn();
        mockMvc.perform(get("/getPatient/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("Welcome"))
                .andExpect(model().attribute("name", "Rama"))
                .andReturn();
    }

    @Test
    public void testDeleteAllPatients() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        mockMvc.perform(post("/addPatient?patientName=Ram"))
                .andExpect(status().isOk())
                .andReturn();
        mockMvc.perform(post("/addPatient?patientName=Meena"))
                .andExpect(status().isOk())
                .andReturn();
        mockMvc.perform(delete("/deleteAll"))
                .andExpect(status().isOk())
                .andReturn();
        mockMvc.perform(get("/getAllPatient"))
                .andExpect(status().isOk())
                .andExpect(view().name("displayrecords"))
                .andExpect(model().attribute("patientList", Collections.emptyList()))
                .andReturn();
    }
}
