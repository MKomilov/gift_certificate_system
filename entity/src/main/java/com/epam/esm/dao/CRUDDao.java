package com.epam.esm.dao;

import com.epam.esm.dto.AbstractDto;
import com.epam.esm.exception.DaoException;
import com.epam.esm.model.AbstractModel;

import java.io.Serializable;

public interface CRUDDao<T extends AbstractModel, D extends AbstractDto, ID extends Serializable> extends CRDDao<T, D>{
    void update(ID id,D item) throws DaoException;
}
