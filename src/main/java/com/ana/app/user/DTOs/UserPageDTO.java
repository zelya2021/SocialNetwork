package com.ana.app.user.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPageDTO {
    private List<UserResponseDTO> users;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}
