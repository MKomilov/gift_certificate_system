package com.epam.esm.dao;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.DaoException;
import com.epam.esm.model.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDao extends CRUDDao<GiftCertificate, GiftCertificateDto, Long> {

    List<GiftCertificate> filter(Map<String, String> map) throws DaoException;

    List<GiftCertificate> getAllForH2() throws DaoException;

    GiftCertificate findById(Long id) throws DaoException;
}
