package com.shabunya.carrent.dto;

import com.shabunya.carrent.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileInfoDTO {
    public String login;
    public String name;
    public String surname;
    public String email;
    public String userRole;

}
