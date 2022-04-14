package com.opinionmining.restservice.service;

import com.opinionmining.restservice.dto.LoginUserDto;
import com.opinionmining.restservice.dto.SignUpUserDto;
import com.opinionmining.restservice.entity.Queue;
import com.opinionmining.restservice.entity.User;
import com.opinionmining.restservice.exception.UserNotFoundException;
import com.opinionmining.restservice.exception.UsernameIsTakenException;
import com.opinionmining.restservice.exception.UsernameOrPasswordIsInvalid;
import com.opinionmining.restservice.model.JwtUser;
import com.opinionmining.restservice.repository.UserRepository;
import com.opinionmining.restservice.security.JwtGenerator;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final QueueService queueService;

    UserService(UserRepository userRepository, QueueService queueService){
        this.userRepository = userRepository;
        this.queueService = queueService;
    }

    public void signup(SignUpUserDto user) {

        String username = user.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()){
            throw new UsernameIsTakenException();
        }else {
            userRepository.save(singUpDtoToEntity(user));
        }

    }

    public String login(LoginUserDto loginUser){
        Optional<User> login_user = userRepository.findByUsername(loginUser.getUsername());
        if (login_user.isPresent()) {
            User user = login_user.get();
            if (user.getPassword().equals(loginUser.getPassword())){
                return JwtGenerator.generate(new JwtUser(user.getUsername(), user.getId().toString()));
            }
        }
        throw new UsernameOrPasswordIsInvalid();
    }

    public void saveFbToken(JwtUser jwtUser, String fbToken){
        userRepository.findByUsername(jwtUser.getUsername()).ifPresent(user -> {
            user.setFbToken(fbToken);
            userRepository.save(user);

        });

    }

    public void saveTwToken(JwtUser jwtUser, String twToken){
        userRepository.findByUsername(jwtUser.getUsername()).ifPresent(user -> {
            user.setTwToken(twToken);
            userRepository.save(user);

        });
    }

    public User findUserById(ObjectId id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            return optionalUser.get();
        }else {
            throw new UserNotFoundException();
        }
    }

    public User findUserByUsername(String username){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()){
            return optionalUser.get();
        }else {
            throw new UserNotFoundException();
        }
    }

    private User singUpDtoToEntity(SignUpUserDto user) {
        return new User(user.getUsername(), user.getEmail(), user.getPassword());
    }

}
