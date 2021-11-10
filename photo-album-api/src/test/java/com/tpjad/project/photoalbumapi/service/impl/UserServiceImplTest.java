package com.tpjad.project.photoalbumapi.service.impl;

import com.tpjad.project.photoalbumapi.dao.entity.UserEntity;
import com.tpjad.project.photoalbumapi.dao.repository.IUserDetailsRepository;
import com.tpjad.project.photoalbumapi.dao.repository.IUserRepository;
import com.tpjad.project.photoalbumapi.service.impl.factory.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest extends AbstractTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private IUserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private IUserDetailsRepository userDetailsRepository;

    private UserFactory userFactory;
    private UserEntity mockUserEntity;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(passwordEncoder, userRepository, userDetailsRepository);
        userFactory = new UserFactory();
        mockUserEntity = userFactory.createMockUserAdmin("admin");
    }

    @Test
    void whenLoadByUsernameAndUserExists_thenReturnUserDetails() {
        when(userRepository.findUserByUsername(anyString())).thenReturn(mockUserEntity);
        userService.loadUserByUsername("alex");
        verify(userRepository, times(1)).findUserByUsername(anyString());
    }

    @Test
    void whenLoadByUsernameAndUserDoesNotExists_thenReturnNull() {
        when(userRepository.findUserByUsername(anyString())).thenReturn(null);
        userService.loadUserByUsername("alex");
        verify(userRepository, times(1)).findUserByUsername(anyString());
    }
}