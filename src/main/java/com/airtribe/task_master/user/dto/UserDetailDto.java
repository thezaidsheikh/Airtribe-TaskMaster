package com.airtribe.task_master.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Builder
public class UserDetailDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private Long phoneNum;
}
