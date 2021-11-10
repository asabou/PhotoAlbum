package com.tpjad.project.photoalbumapi.service.abstracts;

import com.tpjad.project.photoalbumapi.dao.entity.UserDetailsEntity;
import com.tpjad.project.photoalbumapi.dao.entity.UserEntity;
import com.tpjad.project.photoalbumapi.dao.repository.IUserDetailsRepository;
import com.tpjad.project.photoalbumapi.dao.repository.IUserRepository;
import com.tpjad.project.photoalbumapi.exceptions.BadRequestException;
import com.tpjad.project.photoalbumapi.exceptions.InternalServerErrorException;
import com.tpjad.project.photoalbumapi.exceptions.NotFoundException;
import com.tpjad.project.photoalbumapi.service.helper.UserDetailsTransformer;
import com.tpjad.project.photoalbumapi.service.helper.UserTransformer;
import com.tpjad.project.photoalbumapi.service.model.User;
import com.tpjad.project.photoalbumapi.service.model.UserDetails;
import com.tpjad.project.photoalbumapi.utils.Base64Utils;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class AbstractService {
    protected PasswordEncoder passwordEncoder;
    protected IUserRepository userRepository;
    protected IUserDetailsRepository userDetailsRepository;

    protected AbstractService(PasswordEncoder passwordEncoder,
                              IUserRepository userRepository,
                              IUserDetailsRepository userDetailsRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    public void throwBadRequestException(String message) {
        throw new BadRequestException(message);
    }

    public void throwNotFoundException(String message) {
        throw new NotFoundException(message);
    }

    public void throwInternalServerErrorException(String message) {
        throw new InternalServerErrorException(message);
    }

    public void createUser(UserDetails userDetails) {
        User user = userDetails.getUser();
        if (userRepository.findUserByUsername(user.getUsername()) != null) {
            throwBadRequestException("User " + user.getUsername() + " already exists!");
        }
        UserEntity userEntity = UserTransformer.transformUser(user);
        UserDetailsEntity userDetailsEntity = UserDetailsTransformer.transform(userDetails);
        userEntity.setPassword(passwordEncoder.encode(Base64Utils.decode(user.getPassword())));
        userEntity.setUserDetails(userDetailsEntity);
        userDetailsEntity.setUser(userEntity);

        userRepository.save(userEntity);
        userDetailsRepository.save(userDetailsEntity);

    }

}
