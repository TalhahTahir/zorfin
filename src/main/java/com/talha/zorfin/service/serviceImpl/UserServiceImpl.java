package com.talha.zorfin.service.serviceImpl;

import java.time.Instant;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.talha.zorfin.dto.UserDto;
import com.talha.zorfin.dto.UserRegisterDto;
import com.talha.zorfin.dto.UserSearchRequest;
import com.talha.zorfin.entity.User;
import com.talha.zorfin.enums.UserStatus;
import com.talha.zorfin.exception.AlreadyExistsException;
import com.talha.zorfin.exception.ResourceNotFoundException;
import com.talha.zorfin.repo.UserRepo;
import com.talha.zorfin.repo.UserSpecification;
import com.talha.zorfin.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserRegisterDto userDto) {

        if (userRepo.existsByEmail(userDto.getEmail())) {
            throw new AlreadyExistsException("User already exists with email: " + userDto.getEmail());
        }

        User user = mapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

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
    public UserDto getUserById(Long id) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return mapper.map(user, UserDto.class);

    }

    @Override
    public List<UserDto> getUsers(UserSearchRequest request) {

        Specification<User> spec = UserSpecification.getFilteredUsers(request);
        List<User> users = userRepo.findAll(spec);
        return users.stream().map(u -> mapper.map(u, UserDto.class)).toList();
    }

    @Override
    public UserDto updateUser(Long id, UserRegisterDto userDto) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (userRepo.existsByEmailAndIdNot(userDto.getEmail(), id)) {
            throw new AlreadyExistsException("User already exists with email: " + userDto.getEmail());
        }

        mapper.map(userDto, user);

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        user.setUpdatedAt(Instant.now());
        user = userRepo.save(user);
        return mapper.map(user, UserDto.class);
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepo.delete(user);
    }
}
