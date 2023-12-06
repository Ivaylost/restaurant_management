package bg.softuni.restaurants_management.web;

import bg.softuni.restaurants_management.model.dto.UserRegistrationBindingModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
class UserLoginControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void testLogin() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/users/login")
                ).andExpect(status().is2xxSuccessful())
                .andExpect(view().name("auth-login"));

    }

    @Test
    void testLoginError() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users/login-error")
                                .param("email", "test@user.com")
                                .with(csrf())
                ).andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("email", "test@user.com"))
                .andExpect(view().name("auth-login"));

    }
}