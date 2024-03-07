package com.example.blog.dao;

import com.example.blog.dao.filters.PostFilter;
import com.example.blog.dao.repository.PostRepository;
import com.example.blog.domain.Blog;
import com.example.blog.domain.Post;
import com.example.blog.domain.QPost;
import com.example.blog.domain.QTag;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.observation.ObservationFilter;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
public class PostDao {
    private final PostRepository postRepository;
    private final EntityManager entityManager;


    public boolean existsByTitle(String title){
        return postRepository.existsByTitle(title);
    }
    public Optional<Post> findById(Long id){
        return postRepository.findById(id);
    }
    public Post save(Post post){
        return postRepository.save(post);
    }

    public Page<Post> findAll(Pageable pageable){
        return postRepository.findAll(pageable);
    }
    public Page<Post> findByTitleContaining(String searchTerm,Pageable pageable){
        return postRepository.findByTitleContaining(searchTerm,pageable);
    }

    public Page<Post> findALlFilteredWith(PostFilter postFilter) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QPost post = QPost.post;
        QTag tag = QTag.tag;

        JPAQuery<Post> query = queryFactory
                .select(post)
                .from(post);

        List<String> tags = Arrays.asList(postFilter.getTags().split(","));

        if (StringUtils.hasText(postFilter.getTags()) && StringUtils.hasText(postFilter.getSearchTerm())) {
            query
                    .leftJoin(post.tags,tag)
                    .where(post.title.containsIgnoreCase(postFilter.getSearchTerm()),tag.name.in(tags));
            return JPAQueryUtils.toPage(query,postFilter.getPageable());
        }
        if (StringUtils.hasText(postFilter.getSearchTerm()) && !StringUtils.hasText(postFilter.getTags())) {
            query.where(post.title.containsIgnoreCase(postFilter.getSearchTerm()));
            return JPAQueryUtils.toPage(query,postFilter.getPageable());
        }
        if (StringUtils.hasText(postFilter.getTags())) {
            query
                    .leftJoin(post.tags, tag)
                    .where(tag.name.in(tags));
            return JPAQueryUtils.toPage(query,postFilter.getPageable());
        }


        return JPAQueryUtils.toPage(query,postFilter.getPageable());
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public List<Post> getLatestPosts() {
        return postRepository.getLatestPosts();
    }

}
