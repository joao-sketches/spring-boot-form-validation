package io.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class SpringBootFormValidationApplicationTests {

    private final String taskUri = "/tasks";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;


    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(context).alwaysDo(print()).build();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void shouldReturnFormattedErrors() throws Exception {
        mockMvc.perform(post(taskUri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newTask()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.causes").isArray())
                .andExpect(jsonPath("$.errors.causes[0]").value(is("Title must be not empty nor blank.")));

    }


    @Test
    public void shouldReturnTask() throws Exception {
        mockMvc.perform(post(taskUri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newTask("Learn Spring")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.title").value("Learn Spring"));


    }

    private String newTask(String title) throws JsonProcessingException {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription("");
        return new ObjectMapper().writeValueAsString(task);
    }

    private String newTask() throws JsonProcessingException {
        return newTask("");
    }
}
