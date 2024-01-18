package com.epam.esm.dao;

import com.epam.esm.config.H2Config;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.DaoException;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ContextConfiguration(classes = H2Config.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class TestTagDao {

    @Autowired
    private TagDao tagDao;

    @Test
    void createByCorrectParam() throws DaoException {
        TagDto dto = new TagDto("Cooking");
        Tag actual = tagDao.create(dto);
        Assertions.assertEquals(actual.getName(), dto.getName());
    }

    @Test
    void getByInvalidParam() {
        TagDto dto = new TagDto(" ");
        Assertions.assertThrows(DaoException.class, () -> tagDao.create(dto));
    }

    @Test
    void getByValidId() throws DaoException {
        Tag tag = tagDao.create(new TagDto("Tag1"));
        Tag byId = tagDao.getById(tag.getId());
        Assertions.assertEquals(tag, byId);
    }

    @Test
    void getByInvalidId() {
        Assertions.assertThrows(DaoException.class, () -> tagDao.getById(null));
    }

    @Test
    void getAll() throws DaoException {
        List<Tag> expected = new ArrayList<>(Arrays.asList(new Tag(1L, "test1"), new Tag(2L, "test2"), new Tag(3L, "test3"), new Tag(4L, "Cooking")));
        List<Tag> actual = tagDao.getAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteByCorrectId() throws DaoException {
        TagDto dto = new TagDto("Art");
        Tag created = tagDao.create(dto);
        Tag deleted = tagDao.deleteById(created.getId());
        Assertions.assertEquals(created, deleted);
    }
}
