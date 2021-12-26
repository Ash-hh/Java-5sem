package com.shabunya.carrent.dto;

import com.shabunya.carrent.model.Role;
import com.shabunya.carrent.model.UserRole;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ModelAttribute;

@Data
@Getter
@Setter
public class AdminUserUpdateDTO {
    private Long userId;
    private Role newRole;
    private boolean newActivity;
}
