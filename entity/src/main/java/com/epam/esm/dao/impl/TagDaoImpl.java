package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.DaoException;
import com.epam.esm.model.Tag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Component
public class TagDaoImpl implements TagDao {
    private final TagMapper tagMapper;
    private final JdbcTemplate jdbcTemplate;

    public TagDaoImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagMapper = tagMapper;
    }

    @Override
    public Tag getById(Long id) throws DaoException {
        try {
            String sql = "SELECT id, name FROM tag WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, tagMapper, id);
        } catch (Exception e) {
            throw new DaoException("Error finding tag by ID = " + id, "40401");
        }
    }

    @Override
    public List<Tag> getAll() throws DaoException {
        try {
            String sql = "SELECT id, name FROM tag ORDER BY id";
            return jdbcTemplate.query(sql, tagMapper);
        } catch (Exception e) {
            throw new DaoException("Error finding all tags", "40402");
        }
    }

    @Override
    @Transactional
    public Tag create(TagDto tagDto) throws DaoException {
        if (Objects.isNull(tagDto.getName()) || tagDto.getName().trim().isEmpty()) throw new IllegalArgumentException("Invalid param");
        try {
            String insertSql = "INSERT INTO public.tag(name) VALUES (?);";

            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"id"});
                ps.setString(1, tagDto.getName());
                return ps;
            }, keyHolder);

            Long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();

            return getById(generatedId);
        } catch (Exception e) {
            throw new DaoException("Error during create operation", "40403");
        }
    }

    @Override
    public Tag deleteById(Long id) throws DaoException {
        try {
            Tag tag = getById(id);
            if (Objects.nonNull(tag)){
                String sql = "DELETE FROM tag WHERE id = ?";
                jdbcTemplate.update(sql, id);
            }
            return tag;
        } catch (Exception e) {
            throw new DaoException("Error finding tag by ID = " + id, "40401");
        }
    }

    @Override
    public List<Tag> getCertificateTags(long id) {
        String sql = "select id, name from tag where id in (select tag_id from gift_certificate_tag where gift_certificate_id = ?)";
        return jdbcTemplate.query(sql, tagMapper, id);
    }
}
