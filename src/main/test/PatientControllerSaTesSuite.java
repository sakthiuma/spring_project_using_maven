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

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ApplicationConfig.class})
public class PatientControllerSaTesSuite {
    @Mock
    PatientService patientService;
    @Autowired
    WebApplicationContext webApplicationContext;

    @InjectMocks
    PatientController patientController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();

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
        Patient patient1 = new Patient("Ram");
        Patient patient2 = new Patient("Ramesh");
        patientService.addPatient(patient1);
        patientService.addPatient(patient2);

        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        MvcResult result = mockMvc.perform(get("/getAllPatient"))
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(result.getResponse().getContentAsString());
        System.out.println("output" + result.getResponse().getContentAsString());
    }

    @Test
    public void testGetPatientById() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        MvcResult result = mockMvc.perform(get("/getPatient/2"))
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(result.getResponse().getContentAsString());
        System.out.println("output" + result.getResponse().getContentAsString());
    }
}
