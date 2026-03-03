package com.airtribe.task_master.user.contract;

import com.airtribe.task_master.common.exception.NotFoundException;

import java.util.List;
import java.util.Map;

public interface UserLookupService {
    public UserDetail getUserById(Long id) throws NotFoundException;
    public UserDetail getUserByEmail(String email) throws NotFoundException;
    boolean userExists(Long id);
    boolean userExistsByEmail(String email);
    Map<Long, UserDetail> findAllByIds(List<Long> userIds);
}
