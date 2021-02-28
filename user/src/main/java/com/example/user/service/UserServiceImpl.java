package com.example.user.service;

import com.example.user.domain.User;
import com.example.user.repository.UserRepository;
import com.example.user.web.exception.UserNotFoundException;
import com.example.user.web.mapper.UserMapper;
import com.example.user.web.model.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Set<UserDto> getUsers() {
        Set<UserDto> users = new HashSet<>();

        userRepository.findAll().forEach(user -> {
            users.add(userMapper.userToUserDto(user));
        });
        return users;
    }

    @Override
    public UserDto getUserByName(String userName) {
        if(userName==null || userName.isEmpty()){
            throw new UserNotFoundException("User cannot be null");
        }

        Optional<User> userOptional = userRepository.findByUserName(userName);

        if(userOptional.isPresent()){
            return userMapper.userToUserDto(userOptional.get());
        }
        log.error("Invalid User Name provided while using getUserByName: "+ userName);
        throw new UserNotFoundException("Invalid User Name: "+ userName);
    }

    @Override
    @Transactional
    public UserDto updateUserByName(String userName, UserDto userDto) {

        Optional<User> userOptional = userRepository.findByUserName(userName);

        if(userOptional.isPresent()){
            User user = userOptional.get();

            user.setUserName(userDto.getUserName());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());

            return userMapper.userToUserDto(userRepository.save(user));
        }

        log.error("Invalid User Name provided while using getUserByName: "+ userName);
        throw new UserNotFoundException("Invalid User with name: "+ userName);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if(userDto ==  null){
            throw new UserNotFoundException("New User cannot be null");
        }

        return userMapper.userToUserDto(userRepository.save(userMapper.userDtoToUser(userDto)));
    }

    @Override
    @Transactional
    public void deleteByName(String userName) {
        if(userName==null || userName.isEmpty()){
            throw new UserNotFoundException("User cannot be null");
        }

        Optional<User> userOptional = userRepository.findByUserName(userName);

        if(!userOptional.isPresent()) {
            log.error("Invalid User Name provided while using deleteByName: "+ userName);
            throw new UserNotFoundException("Invalid User Name: "+ userName);
        }

        userRepository.deleteByUserName(userName);
    }

}
