package com.talha.zorfin.service.serviceImpl;

import java.time.Instant;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.talha.zorfin.dto.PagedResponse;
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
    public PagedResponse<UserDto> getUsers(UserSearchRequest request, int page, int size, String sortBy,
            String sortDir) {

        // Determining the sort direction
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<User> spec = UserSpecification.getFilteredUsers(request);
        Page<User> userPage = userRepo.findAll(spec, pageable);
        List<UserDto> content = userPage.getContent().stream()
                .map(u -> mapper.map(u, UserDto.class))
                .toList();
                
        return new PagedResponse<>(
                content,
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.isLast());
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
