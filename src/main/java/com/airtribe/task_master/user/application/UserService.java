package com.airtribe.task_master.user.application;

import com.airtribe.task_master.common.exception.NotFoundException;
import com.airtribe.task_master.user.contract.UserDetail;
import com.airtribe.task_master.user.contract.UserLookupService;
import com.airtribe.task_master.user.domain.User;
import com.airtribe.task_master.user.dto.UserDetailDto;
import com.airtribe.task_master.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public UserDetail getUserById(Long id) throws NotFoundException {
        return userRepository.<UserDetail>findByUserId(id,UserDetail.class).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public UserDetail getUserByEmail(String email) throws NotFoundException {
        return userRepository.<UserDetail>findByEmail(email,UserDetail.class).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public boolean userExists(Long id) {
        UserDetail user = userRepository.<UserDetail>findByUserId(id,UserDetail.class).orElse(null);
        if(user == null) return false;
        return true;
    }

    @Override
    public boolean userExistsByEmail(String email) {
        UserDetail user = userRepository.<UserDetail>findByEmail(email,UserDetail.class).orElse(null);
        if(user == null) return false;
        return true;
    }


    @Override
    public Map<Long, UserDetail> findAllByIds(List<Long> userIds) {
        List<UserDetail> users = userRepository.<UserDetail>findByUserIdIn(userIds,UserDetail.class);
        return users.stream().collect(Collectors.toMap(UserDetail::userId, userDetail -> userDetail));
    }
}
