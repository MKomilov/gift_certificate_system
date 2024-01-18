package com.epam.esm.service;

import com.epam.esm.dto.AbstractDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.DaoException;
import com.epam.esm.model.AbstractModel;

import java.util.List;

public interface CRDService<T extends AbstractModel, D extends AbstractDto> {
    T getById(Long id) throws DaoException, BadRequestException;

    List<T> getAll() throws DaoException;

    T create(D item) throws DaoException, BadRequestException;

    T delete(Long id) throws DaoException, BadRequestException;
}
