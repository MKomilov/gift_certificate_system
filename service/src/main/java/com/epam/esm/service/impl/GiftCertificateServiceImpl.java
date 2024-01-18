package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.DaoException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;

    @Override
    public GiftCertificate getById(Long id) throws DaoException, BadRequestException {
        Validator.checkId(id, "GiftCertificate");
        return giftCertificateDao.getById(id);
    }

    @Override
    public List<GiftCertificate> getAll() throws DaoException {
        return giftCertificateDao.getAll();
    }

    @Override
    public GiftCertificate create(GiftCertificateDto giftDto) throws DaoException, BadRequestException {
        Validator.checkDto(giftDto);
        return giftCertificateDao.create(giftDto);
    }

    @Override
    public GiftCertificate delete(Long id) throws DaoException, BadRequestException {
        Validator.checkId(id, "GiftCertificate");
        return giftCertificateDao.deleteById(id);
    }

    @Override
    public void update(Long id, GiftCertificateDto giftDto) throws DaoException, BadRequestException {
        Validator.checkId(id, "GiftCertificate");
        giftCertificateDao.update(id,giftDto);
    }

    @Override
    public List<GiftCertificate> filter(Map<String, String> map) throws DaoException {
        return giftCertificateDao.filter(map);
    }
}
