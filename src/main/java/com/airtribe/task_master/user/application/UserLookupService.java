package com.airtribe.task_master.user.application;

import com.airtribe.task_master.common.exception.NotFoundException;
import com.airtribe.task_master.user.dto.UserDetailDto;

public interface UserLookupService {
    public UserDetailDto getUserById(Long id) throws NotFoundException;
    public UserDetailDto getUserByEmail(String email) throws NotFoundException;
    boolean userExists(Long id);
    boolean userExistsByEmail(String email);
}
