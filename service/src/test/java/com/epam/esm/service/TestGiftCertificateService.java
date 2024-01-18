package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.DaoException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestGiftCertificateService {
    private static final Tag TAG_1 = new Tag(1L, "tagName1");
    private static final Tag TAG_2 = new Tag(2L, "tagName2");
    private static final Tag TAG_3 = new Tag(3L, "tagName3");
    private static final GiftCertificate CERTIFICATE_1 = new GiftCertificate(1L, "Certificate1", "Description1", new BigDecimal(50), 1, Arrays.asList(TAG_1, TAG_2));
    private static final GiftCertificate CERTIFICATE_2 = new GiftCertificate(2L, "Certificate2", "Description2", new BigDecimal(20), 3, Arrays.asList(TAG_3, TAG_2));
    private static final GiftCertificate CERTIFICATE_3 = new GiftCertificate(3L, "Certificate3", "Description3", new BigDecimal(30), 2, Arrays.asList(TAG_3, TAG_1));

    @Mock
    private GiftCertificateDao dao;

    @InjectMocks
    private GiftCertificateServiceImpl service;

    @Test
    void getAllTest() throws DaoException {
        List<GiftCertificate> expected = Arrays.asList(CERTIFICATE_1, CERTIFICATE_2, CERTIFICATE_3);
        when(dao.getAll()).thenReturn(expected);
        List<GiftCertificate> actual = service.getAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getByExistedIdTest() throws DaoException, BadRequestException {
        when(dao.getById(CERTIFICATE_1.getId())).thenReturn(CERTIFICATE_1);
        GiftCertificate actual = service.getById(CERTIFICATE_1.getId());
        Assertions.assertEquals(CERTIFICATE_1, actual);
    }
    @Test
    void getByNotExistedIdTest() throws DaoException {
        when(dao.getById(6L)).thenThrow(DaoException.class);
        Assertions.assertThrows(DaoException.class, () -> service.getById(6L));
    }

    @Test
    void removeByExistedIdTest() {
        Assertions.assertDoesNotThrow(() -> service.delete(CERTIFICATE_1.getId()));
    }

    @Test
    void removeByNotExistedIdTest() throws DaoException {
        when(dao.deleteById(10L)).thenThrow(DaoException.class);
        Assertions.assertThrows(DaoException.class, () -> service.delete(10L));
    }
    @Test
    void insertCorrectTagParamTest() throws DaoException, BadRequestException {
        GiftCertificateDao giftCertificateDaoMock = mock(GiftCertificateDao.class);
        GiftCertificateServiceImpl service = new GiftCertificateServiceImpl(giftCertificateDaoMock);

        GiftCertificateDto dto = new GiftCertificateDto("Certificate2", "Description2", new BigDecimal(20), 3, Arrays.asList(TAG_3.getId(), TAG_2.getId()));
        when(giftCertificateDaoMock.create(dto)).thenReturn(CERTIFICATE_2);

        GiftCertificate actual = service.create(dto);
        Assertions.assertEquals(CERTIFICATE_2, actual);
    }
}
