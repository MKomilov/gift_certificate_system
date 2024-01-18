package com.epam.esm.dao;

import com.epam.esm.config.H2Config;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.DaoException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ContextConfiguration(classes = H2Config.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class TestGiftCertificateDao {
    private static final Tag TAG_1 = new Tag(1L, "test1");
    private static final Tag TAG_2 = new Tag(2L, "test2");
    private static final Tag TAG_3 = new Tag(3L, "test3");
    private static final GiftCertificate CERTIFICATE_1 = new GiftCertificate(1L, "gift1", "Description1", new BigDecimal("10.00"), 1, Arrays.asList(TAG_1, TAG_2));
    private static final GiftCertificate CERTIFICATE_2 = new GiftCertificate(2L, "gift2", "Description2", new BigDecimal("11.00"), 2, Arrays.asList(TAG_2, TAG_3));
    private static final GiftCertificate CERTIFICATE_3 = new GiftCertificate(3L, "gift3", "Description3", new BigDecimal("12.00"), 3, Arrays.asList(TAG_1, TAG_3));
//    private static final GiftCertificateDto DTO = new GiftCertificateDto("gift4", "Description4", new BigDecimal("15.00"), 2, Arrays.asList(TAG_1.getId(), TAG_2.getId()));

    @Autowired
    private GiftCertificateDao dao;

    @Test
    @Sql(scripts = {"classpath:scripts/getAllGiftCertificate.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getAll() throws DaoException {
        List<GiftCertificate> expected = new ArrayList<>(Arrays.asList(CERTIFICATE_1, CERTIFICATE_2, CERTIFICATE_3));
        List<GiftCertificate> actual = dao.getAllForH2();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getByValidId() throws DaoException {
        GiftCertificate certificate = dao.findById(1L);
        Assertions.assertEquals(CERTIFICATE_1.getName(), certificate.getName());
    }
}
