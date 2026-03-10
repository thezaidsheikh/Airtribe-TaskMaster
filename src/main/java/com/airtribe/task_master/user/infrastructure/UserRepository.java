package com.airtribe.task_master.user.infrastructure;

import com.airtribe.task_master.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Default methods — return User when no projection type is specified
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(Long userId);
    List<User> findByUserIdIn(List<Long> userIds);

    // Generic projection overloads — pass a Class<T> to get a custom projection
    <T> Optional<T> findByEmail(String email, Class<T> type);
    <T> Optional<T> findByUserId(Long userId, Class<T> type);
    <T> List<T> findByUserIdIn(List<Long> userIds, Class<T> type);
}
