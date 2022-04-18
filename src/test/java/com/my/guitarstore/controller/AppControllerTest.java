package com.my.guitarstore.controller;

import com.my.guitarstore.model.User;
import com.my.guitarstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class AppControllerTest {

    @Mock
    private UserRepository repo;

    @InjectMocks
    private AppController appController;

    @Test
    void viewHomePageTest() {
        String mockResult = appController.viewHomePage();
        assertEquals("index", mockResult);
    }

    @Test
    void showSignUpFormTest() {
        String mockResult = appController.showSignUpForm(getModel());
        assertEquals("signup_form", mockResult);
    }

    @Test
    void processRegistrationTest() {
        String mockResult = appController.processRegistration(getUser());
        when(repo.save(any())).thenReturn(getUser());
        assertEquals("register_success", mockResult);
    }

    @Test
    void viewUsersTest() {
        List<User> users = new ArrayList<>();
        String mockResult = appController.viewUsers(getModel());
        when(repo.findAll()).thenReturn(users);
        assertEquals("users", mockResult);
    }

    private User getUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Economy");
        user.setEmail("john@gmail.com");
        user.setPassword("password");
        return user;
    }

    private Model getModel() {
        Model model = new Model() {
            @Override
            public Model addAttribute(String attributeName, Object attributeValue) {
                return null;
            }

            @Override
            public Model addAttribute(Object attributeValue) {
                return null;
            }

            @Override
            public Model addAllAttributes(Collection<?> attributeValues) {
                return null;
            }

            @Override
            public Model addAllAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public Model mergeAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public boolean containsAttribute(String attributeName) {
                return false;
            }

            @Override
            public Object getAttribute(String attributeName) {
                return null;
            }

            @Override
            public Map<String, Object> asMap() {
                return null;
            }
        };
        return model;

    }


}
