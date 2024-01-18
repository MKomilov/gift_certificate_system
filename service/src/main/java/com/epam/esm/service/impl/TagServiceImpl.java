package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.DaoException;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    @Override
    public Tag getById(Long id) throws DaoException, BadRequestException {
        Validator.checkId(id, "Tag");
        return tagDao.getById(id);
    }

    @Override
    public List<Tag> getAll() throws DaoException {
        return tagDao.getAll();
    }

    @Override
    public Tag create(TagDto tagDto) throws DaoException, BadRequestException {
        Validator.checkTagDto(tagDto);
        return tagDao.create(tagDto);
    }

    @Override
    public Tag delete(Long id) throws DaoException, BadRequestException {
        Validator.checkId(id, "Tag");
        return tagDao.deleteById(id);
    }
}
