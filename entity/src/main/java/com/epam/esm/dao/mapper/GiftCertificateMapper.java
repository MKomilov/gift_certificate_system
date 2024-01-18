package com.epam.esm.dao.mapper;

import com.epam.esm.exception.DaoException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(rs.getLong("id"));
        giftCertificate.setName(rs.getString("name"));
        giftCertificate.setDuration(rs.getInt("duration"));
        giftCertificate.setDescription(rs.getString("description"));
        giftCertificate.setPrice(rs.getBigDecimal("price"));
        if (hasColumn(rs, "create_date")){
            giftCertificate.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
        }
        if (hasColumn(rs, "last_update_date")){
            giftCertificate.setLastUpdateDate(rs.getTimestamp("last_update_date").toLocalDateTime());
        }

        if (hasColumn(rs, "tags")){
            String tagsJson = rs.getString("tags");
            List<Tag> tags;
            try {
                tags = parseTagsJson(tagsJson);
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }

            if (Objects.nonNull(tags)) giftCertificate.setTags(tags);
        }
        return giftCertificate;
    }

    private List<Tag> parseTagsJson(String tagsJson) throws DaoException {
        List<Tag> tags = new ArrayList<>();
        if (tagsJson != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                tags = objectMapper.readValue(tagsJson, new TypeReference<List<Tag>>() {});
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        return tags;
    }

    private static boolean hasColumn(ResultSet resultSet, String columnName) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columns = metaData.getColumnCount();
        for (int i = 1; i <= columns; i++) {
            if (columnName.equals(metaData.getColumnName(i))) {
                return true;
            }
        }
        return false;
    }
}
