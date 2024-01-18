package com.epam.esm.service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.DaoException;
import com.epam.esm.model.Tag;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestTagService {
    private static final Tag TAG_1 = new Tag(1L, "tagName1");
    private static final Tag TAG_2 = new Tag(2L, "tagName3");
    private static final Tag TAG_3 = new Tag(3L, "tagName2");
    private static final Tag TAG_4 = new Tag(4L, "tagName5");
    @Mock
    private TagDao tagDao;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void getAllTest() throws DaoException {
        List<Tag> expected = Arrays.asList(TAG_1, TAG_2, TAG_3, TAG_4);
        when(tagDao.getAll()).thenReturn(expected);
        List<Tag> actual = tagService.getAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getByExistedIdTest() throws DaoException, BadRequestException {
        when(tagDao.getById(TAG_1.getId())).thenReturn(TAG_1);
        Tag actual = tagService.getById(TAG_1.getId());
        Assertions.assertEquals(TAG_1, actual);
    }

    @Test
    void getByNotExistedIdTest() throws DaoException {
        when(tagDao.getById(6L)).thenThrow(DaoException.class);
        Assertions.assertThrows(DaoException.class, () -> tagService.getById(6L));
    }

    @Test
    void removeByExistedIdTest() {
        Assertions.assertDoesNotThrow(() -> tagService.delete(TAG_2.getId()));
    }

    @Test
    void removeByNotExistedIdTest() throws DaoException {
        when(tagDao.deleteById(10L)).thenThrow(DaoException.class);
        Assertions.assertThrows(DaoException.class, () -> tagService.delete(10L));
    }

    @Test
    void insertCorrectTagParamTest() throws DaoException, BadRequestException {
        TagDto tagDto = new TagDto("Tag1");
        Tag expectedTag = new Tag(1L, "Tag1");
        when(tagDao.create(tagDto)).thenReturn(expectedTag);
        Tag actualTag = tagService.create(tagDto);
        Assertions.assertEquals(expectedTag, actualTag);
    }

    @Test
    void insertIncorrectTagParamTest() {
        Assertions.assertThrows(BadRequestException.class, () -> tagService.create(new TagDto(" ")));
    }
}
