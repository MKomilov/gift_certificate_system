package com.epam.esm.dao;

import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Tag;

import java.util.List;

public interface TagDao extends CRDDao<Tag, TagDto> {

    List<Tag> getCertificateTags(long id);
}
