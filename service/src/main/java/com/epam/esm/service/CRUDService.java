package com.epam.esm.service;

import com.epam.esm.dto.AbstractDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.DaoException;
import com.epam.esm.model.AbstractModel;

import java.io.Serializable;

public interface CRUDService<T extends AbstractModel, D extends AbstractDto, ID extends Serializable> extends CRDService<T, D> {
    void update(ID id, D item) throws DaoException, BadRequestException;
}
