package com.example.blog.dao.filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainFilter {
    private  Pageable pageable;
    private String searchTerm;
}


