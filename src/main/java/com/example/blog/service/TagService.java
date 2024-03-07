package com.example.blog.service;

import com.example.blog.dao.TagDao;
import com.example.blog.domain.Tag;
import com.example.blog.service.dto.TagDTO;
import com.example.blog.service.exception.BusinessException;
import com.example.blog.service.mapper.TagMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class TagService {
    private final TagDao tagDao;
    private final TagMapper tagMapper;

    public TagService(TagDao tagDao, TagMapper tagMapper) {
        this.tagDao = tagDao;
        this.tagMapper = tagMapper;
    }


    public TagDTO creatTag(TagDTO tagDTO){
        if (tagDao.existsByName(tagDTO.getName())){
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(),"tag.already.exists","Tag already exists");
        }
        Tag tag = tagMapper.toEntity(tagDTO);
        Tag savedTag = tagDao.save(tag);
        return tagMapper.toDTO(savedTag);
    }

    public TagDTO save(TagDTO tagDTO,Long id) {
        if(tagDao.findById(id).isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND.value(),"tag.not.found","Tag not found");
        }

        Optional<Tag> tagDB = tagDao.findByName(tagDTO.getName());

        if (tagDB.isPresent() && !Objects.equals(tagDB.get().getId(), id)){
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(),"tag.name.already.used","Tag name already used");
        }


        Tag tag = tagMapper.toEntity(tagDTO);
        tag.setId(id);

        tagDao.save(tag);

        tagDTO.setId(id);
        return tagDTO;
    }

    public Page<TagDTO> getAllTags(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return tagDao.findAll(pageable).map(tagMapper::toDTO);
    }

    public TagDTO getTag(Long id) {
        Tag tag = tagDao.findById(id).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND.value(), "tag.not.found", "Tag not found"));
        return tagMapper.toDTO(tag);
    }

    public void delete(Long id) {
        tagDao.delete(id);
    }
}
