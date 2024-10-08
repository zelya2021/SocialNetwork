package com.ana.app;

import com.ana.app.user.dto.CreateUserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void successfulRegistrationCreatesNewUser() throws Exception {
        CreateUserDTO registrationDto = CreateUserDTO.builder()
                .name("John")
                .email("john@example.com")
                .lastName("Zelo")
                .password("12345678")
                .build();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void badRequestRegistrationCreatesNewUserWithoutName() throws Exception {
        CreateUserDTO registrationDto = CreateUserDTO.builder()
                .email("john@example.com")
                .lastName("Zelo")
                .password("12345678")
                .build();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void badRequestRegistrationCreatesNewUserWithIncorrectEmail() throws Exception {
        CreateUserDTO registrationDto = CreateUserDTO.builder()
                .name("Ana")
                .email("john")
                .lastName("Zelo")
                .password("12345678")
                .build();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void badRequestRegistrationCreatesNewUserWithIncorrectPassword() throws Exception {
        CreateUserDTO registrationDto = CreateUserDTO.builder()
                .name("Ana")
                .email("john@example.com")
                .lastName("Zelo")
                .password("12345")
                .build();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void badRequestRegistrationCreatesNewUserWithEmptyPassword() throws Exception {
        CreateUserDTO registrationDto = CreateUserDTO.builder()
                .name("Ana")
                .email("john@example.com")
                .lastName("Zelo")
                .build();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDto)))
                .andExpect(status().isBadRequest());
    }
}
