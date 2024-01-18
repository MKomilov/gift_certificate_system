package com.epam.esm.dao;

import com.epam.esm.dto.AbstractDto;
import com.epam.esm.exception.DaoException;
import com.epam.esm.model.AbstractModel;

import java.util.List;

public interface CRDDao<T extends AbstractModel, D extends AbstractDto>{
    T getById(Long id) throws DaoException;

    List<T> getAll() throws DaoException;

    T create(D item) throws DaoException;

    T deleteById(Long id) throws DaoException;
}
