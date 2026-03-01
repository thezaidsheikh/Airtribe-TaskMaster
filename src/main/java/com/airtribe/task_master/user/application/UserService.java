package com.airtribe.task_master.user.application;

import com.airtribe.task_master.common.exception.NotFoundException;
import com.airtribe.task_master.user.domain.User;
import com.airtribe.task_master.user.dto.UserDetailDto;
import com.airtribe.task_master.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserLookupService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserDetailDto mapToUserDetailDto(User user) {
        return UserDetailDto.builder().userId(user.getUserId()).firstName(user.getFirstName()).lastName(user.getLastName()).email(user.getEmail())
                            .phoneNum(user.getPhoneNum()).build();
    }

    @Override
    public UserDetailDto getUserById(Long id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return mapToUserDetailDto(user);
    }

    @Override
    public UserDetailDto getUserByEmail(String email) throws NotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
        return mapToUserDetailDto(user);
    }

    @Override
    public boolean userExists(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) return false;
        return true;
    }

    @Override
    public boolean userExistsByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if(user == null) return false;
        return true;
    }
}
