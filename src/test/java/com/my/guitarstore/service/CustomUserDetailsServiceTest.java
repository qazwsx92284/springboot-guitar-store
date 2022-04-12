package com.my.guitarstore.service;

import com.my.guitarstore.model.User;
import com.my.guitarstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserRepository repo;

    @Test
    void loadUserByUsernameTest_returnCustomUserDetails() {
        when(repo.findByEmail(any())).thenReturn(getUser());
        UserDetails mockResult = customUserDetailsService.loadUserByUsername("john@gmail.com");
        assertNotNull(mockResult);
    }

    @Test
    void loadUserByUsernameTest_throw_exception() {
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("xxx@gamil.com");
        });
    }

    private User getUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Economy");
        user.setEmail("john@gmail.com");
        user.setPassword("password");

        return user;
    }
}