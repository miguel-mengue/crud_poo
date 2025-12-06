package com.usermanagerapi.usermanage.service;

import com.usermanagerapi.usermanage.controller.CreateUserDto;
import com.usermanagerapi.usermanage.controller.UpdateUserDto;
import com.usermanagerapi.usermanage.entity.User;
import com.usermanagerapi.usermanage.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDto createUserDto) {

        var entity = new User(
                createUserDto.name(),
                createUserDto.email(),
                createUserDto.age()
        );

        var userSaved = userRepository.save(entity);

        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void updateUserById(String userId, UpdateUserDto updateUserDto) {
        var id = UUID.fromString(userId);


        var userEntity = userRepository.findById(id);

        if(userEntity.isPresent()) {
            var user = userEntity.get();

            if (updateUserDto.name() != null) {
                user.setName(updateUserDto.name());
            }

            if (updateUserDto.email() != null) {
                user.setEmail(updateUserDto.email());
            }

            userRepository.save(user);
        }
    }

    public void deleteById(String userId) {
        var id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        }
    }


}
