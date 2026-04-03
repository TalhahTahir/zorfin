package com.talha.zorfin.service.serviceImpl;

import java.time.Instant;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.talha.zorfin.dto.UserDto;
import com.talha.zorfin.dto.UserRegisterDto;
import com.talha.zorfin.entity.User;
import com.talha.zorfin.enums.UserRole;
import com.talha.zorfin.enums.UserStatus;
import com.talha.zorfin.repo.UserRepo;
import com.talha.zorfin.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ModelMapper mapper;

    @Override
    public UserDto createUser(UserRegisterDto userDto) {

        User user = mapper.map(userDto, User.class);

        if (userDto.getStatus().equals(UserStatus.INACTIVE)) {
            user.setStatus(UserStatus.INACTIVE);
        } else {
            user.setStatus(UserStatus.ACTIVE);
        }
        
        user.setCreatedAt(Instant.now());
        user = userRepo.save(user);
        return mapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = userRepo.findAll();
        return users.stream().map(user -> mapper.map(user, UserDto.class)).toList();
    }

    @Override
    public UserDto getUserById(Long id) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return mapper.map(user, UserDto.class);

    }

    @Override
    public UserDto updateUser(Long id, UserRegisterDto userDto) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        mapper.map(userDto, user);
        user.setUpdatedAt(Instant.now());
        user = userRepo.save(user);
        return mapper.map(user, UserDto.class);
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepo.delete(user);
    }

    @Override
    public List<UserDto> getUsersByRole(UserRole role) {

        List<User> users = userRepo.findAllByRole(role);
        return users.stream().map(u -> mapper.map(u, UserDto.class)).toList();

    }

    @Override
    public List<UserDto> getUsersByStatus(UserStatus status) {

        List<User> users = userRepo.findAllByStatus(status);
        return users.stream().map(u -> mapper.map(u, UserDto.class)).toList();
    }

    @Override
    public List<UserDto> getUsersByRoleAndStatus(UserRole role, UserStatus status) {

        List<User> users = userRepo.findAllByRoleAndStatus(role, status);
        return users.stream().map(u -> mapper.map(u, UserDto.class)).toList();
    }

}
