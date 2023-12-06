package bg.softuni.restaurants_management.web;

import bg.softuni.restaurants_management.model.dto.UserRegistrationBindingModel;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.repository.UserRepository;
import bg.softuni.restaurants_management.service.EventPublisherInterface;
import bg.softuni.restaurants_management.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
class UserRegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    private EventPublisherInterface eventPublisher;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void testRegistration() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users/register")
                                .param("email", "test@user.com")
                                .param("firstName", "Test")
                                .param("lastName", "User")
                                .param("password", "password")
                                .param("confirmPassword", "password")
                                .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/login"));
    }

    @Test
    void testRegistrationPostReturnBackByWrongPasswordAndConfirmPassword() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users/register")
                                .param("email", "test@user.com")
                                .param("firstName", "Test")
                                .param("lastName", "User")
                                .param("password", "password")
                                .param("confirmPassword", "password1")
                                .with(csrf())
                ).andExpect(status().is2xxSuccessful())
                .andExpect(view().name("auth-register"));
    }

    @Test
    void testRegistrationGetMethodViewNameAndViewModelBindingModel() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/users/register")
                ).andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("userRegistrationBindingModel"))
                .andExpect(model().attribute("userRegistrationBindingModel", instanceOf(UserRegistrationBindingModel.class)))
                .andExpect(view().name("auth-register"));

    }

    @Test
    void tesVerificationGetMethod() throws Exception {

        UserRegistrationBindingModel userRegistrationBindingModel = new UserRegistrationBindingModel()
                .setFirstName("first")
                .setLastName("last")
                .setEmail("test@test.com")
                .setPassword("password");

        UserEntity userEntity = userService.registerUser(userRegistrationBindingModel);
        String registrationToken = userEntity.getRegistrationToken();

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/users/verify?token={registrationToken}", registrationToken)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/login"));

    }
}