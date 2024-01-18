package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.DaoException;
import com.epam.esm.model.GiftCertificate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class GiftCertificateDaoImpl implements GiftCertificateDao{
    private final TagDao tagDao;
    private final JdbcTemplate jdbcTemplate;
    private static final String GET_ALL = "SELECT g.*,\n" +
        "       (select jsonb_agg(jsonb_build_object('id', t.id, 'name', t.name))\n" +
        "        FROM gift_certificate_tag gct\n" +
        "                 JOIN tag t ON t.id = gct.tag_id\n" +
        "        WHERE g.id = gct.gift_certificate_id) AS tags\n" +
        "FROM gift_certificate g\n" +
        "         JOIN gift_certificate_tag gct ON g.id = gct.gift_certificate_id\n" +
        "         JOIN tag t ON t.id = gct.tag_id\n" +
        "GROUP BY g.id;";
    private final GiftCertificateMapper mapper;
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, TagDao tagDao, GiftCertificateMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagDao = tagDao;
        this.mapper = mapper;
    }

    @Override
    public GiftCertificate getById(Long id) throws DaoException {
        try {
            String sql = "SELECT g.*,\n" +
                    "       (select jsonb_agg(jsonb_build_object('id', t.id, 'name', t.name))\n" +
                    "        FROM gift_certificate_tag gct\n" +
                    "                 JOIN tag t ON t.id = gct.tag_id\n" +
                    "        WHERE g.id = gct.gift_certificate_id) AS tags\n" +
                    "FROM gift_certificate g\n" +
                    "         JOIN gift_certificate_tag gct ON g.id = gct.gift_certificate_id\n" +
                    "         JOIN tag t ON t.id = gct.tag_id\n" +
                    "WHERE g.id = ?\n" +
                    "GROUP BY g.id";
            return jdbcTemplate.queryForObject(sql, mapper, id);
        } catch (Exception e) {
            throw new DaoException("Error finding gift certificate by ID = " + id, "40401");
        }
    }


    @Override
    public List<GiftCertificate> getAll() throws DaoException {
        try {
            return jdbcTemplate.query(GET_ALL, mapper);
        } catch (Exception e) {
            throw new DaoException("Error finding all gift certificates", "40402");
        }
    }

    @Override
    @Transactional
    public GiftCertificate create(GiftCertificateDto giftDto) throws DaoException {
        try {
            String insertSql = "INSERT INTO gift_certificate (name, description, price, duration) VALUES (?, ?, ?, ?);";

            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"id"});
                ps.setString(1, giftDto.getName());
                ps.setString(2, giftDto.getDescription());
                ps.setBigDecimal(3, giftDto.getPrice());
                ps.setInt(4, giftDto.getDuration());
                return ps;
            }, keyHolder);

            Long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
            giftDto.getTagIds().forEach(tagId -> jdbcTemplate.update(
                    "insert into gift_certificate_tag values(?, ?)",
                    generatedId,
                    tagId
            ));

            return getById(generatedId);
        } catch (Exception e) {
            throw new DaoException("Error during create gift certificate", "40403");
        }
    }

    @Override
    public GiftCertificate deleteById(Long id) throws DaoException {
        try {
            GiftCertificate certificate = getById(id);
            if (Objects.nonNull(certificate)){
                String sql = "DELETE FROM gift_certificate WHERE id = ?";
                jdbcTemplate.update(sql, id);
            }
            return certificate;
        }catch (Exception e){
            throw new DaoException("Error finding gift certificate by ID = " + id, "40401");
        }
    }

    @Override
    public void update(Long id, GiftCertificateDto giftDto) throws DaoException {
        try {
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
            StringBuilder updateSql = new StringBuilder("UPDATE gift_certificate SET last_update_date = :date, ");
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("date", LocalDateTime.now());

            if (giftDto.getName() != null) {
                updateSql.append("name = :name, ");
                parameters.addValue("name", giftDto.getName());
            }
            if (giftDto.getDescription() != null) {
                updateSql.append("description = :description, ");
                parameters.addValue("description", giftDto.getDescription());
            }
            if (giftDto.getPrice() != null) {
                updateSql.append("price = :price, ");
                parameters.addValue("price", giftDto.getPrice());
            }
            if (giftDto.getDuration() != null) {
                updateSql.append("duration = :duration, ");
                parameters.addValue("duration", giftDto.getDuration());
            }

            updateSql.setLength(updateSql.length() - 2);

            updateSql.append(" WHERE id = :id");
            parameters.addValue("id", id);

            namedParameterJdbcTemplate.update(updateSql.toString(), parameters);
        }catch (Exception e){
            throw new DaoException("Error update gift certificate with id = " + id, "40404");
        }
    }

    @Override
    public List<GiftCertificate> filter(Map<String, String> map) throws DaoException {
        List<GiftCertificate> list;
        try {
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
            StringBuilder sql = new StringBuilder("SELECT g.*,\n" +
                    "       (select jsonb_agg(jsonb_build_object('id', t.id, 'name', t.name))\n" +
                    "        FROM gift_certificate_tag gct\n" +
                    "                 JOIN tag t ON t.id = gct.tag_id\n" +
                    "        WHERE g.id = gct.gift_certificate_id) AS tags\n" +
                    "FROM gift_certificate g\n" +
                    "         JOIN gift_certificate_tag gct ON g.id = gct.gift_certificate_id\n" +
                    "         JOIN tag t ON t.id = gct.tag_id\n" +
                    "WHERE (CAST(:name AS VARCHAR) IS NULL OR t.name = CAST(:name AS VARCHAR))\n" +
                    "  AND (CAST(:search AS VARCHAR) IS NULL OR g.name ILIKE concat('%', CAST(:search AS VARCHAR), '%') OR g.description ILIKE concat('%', CAST(:search AS VARCHAR), '%'))\n" +
                    "GROUP BY g.id");
            MapSqlParameterSource parameters = new MapSqlParameterSource();

            String name = map.get("tag_name");
            String search = map.get("search");
            String sortDate = map.get("sort_date");
            String sortName = map.get("sort_name");
            parameters.addValue("name", name);
            parameters.addValue("search", search);

            if (sortDate != null) {
                sql.append(" ORDER BY g.create_date");
                if (sortDate.equalsIgnoreCase("desc")){
                    sql.append(" desc");
                }
            }
            if (sortName != null) {
                sql.append(", g.name");
                if (sortName.equalsIgnoreCase("desc")){
                    sql.append(" desc");
                }
            }

            list = namedParameterJdbcTemplate.query(sql.toString(), parameters, mapper);
        }catch (Exception e){
            throw new DaoException("Error during extracting filtered data from database", "40405");
        }
        return list;
    }

    @Override
    public List<GiftCertificate> getAllForH2() throws DaoException {
        try {
            String sql = "select id, name, description, duration, price from gift_certificate";
            List<GiftCertificate> list = jdbcTemplate.query(sql, mapper);
            list.forEach(giftCertificate -> giftCertificate.setTags(tagDao.getCertificateTags(giftCertificate.getId())));
            return list;
        }catch (Exception e){
            throw new DaoException("Error", "40406");
        }
    }

    @Override
    public GiftCertificate findById(Long id) throws DaoException {
        try {
            String sql = "select id, name, description, duration, price from gift_certificate where id = ?";
            return jdbcTemplate.queryForObject(sql, mapper, id);
        }catch (Exception e){
            throw new DaoException("Error finding gift certificate id = " + id, "40407");
        }

    }
}
