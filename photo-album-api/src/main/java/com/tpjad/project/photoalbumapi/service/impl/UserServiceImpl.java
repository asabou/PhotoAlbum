package com.tpjad.project.photoalbumapi.service.impl;

import com.tpjad.project.photoalbumapi.dao.repository.IUserDetailsRepository;
import com.tpjad.project.photoalbumapi.dao.repository.IUserRepository;
import com.tpjad.project.photoalbumapi.service.abstracts.AbstractService;
import com.tpjad.project.photoalbumapi.service.abstracts.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractService implements IUserService {

    protected UserServiceImpl(PasswordEncoder passwordEncoder,
                              IUserRepository userRepository,
                              IUserDetailsRepository userDetailsRepository) {
        super(passwordEncoder, userRepository, userDetailsRepository);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username);
    }


}
