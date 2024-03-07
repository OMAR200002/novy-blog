package com.example.blog.dao;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JPAQueryUtils {

    public static <T> Page<T> toPage(JPAQuery<T> query, Pageable pageable) {
        query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<T> content = query.fetch();

        return new PageImpl<>(content, pageable, query.fetchCount());
    }
}
