package com.avishek.clouddrive.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponse {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
}
