package com.ana.app.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponseDTO<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private List<T> users;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}
