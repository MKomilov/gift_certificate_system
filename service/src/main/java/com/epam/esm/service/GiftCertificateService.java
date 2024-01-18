package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.DaoException;
import com.epam.esm.model.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService extends CRUDService<GiftCertificate, GiftCertificateDto, Long> {
    List<GiftCertificate> filter(Map<String, String> map) throws DaoException, BadRequestException;

}
