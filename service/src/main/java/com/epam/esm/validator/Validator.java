package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.BadRequestException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class Validator {
    public static void checkParam(String param, String name) throws BadRequestException {
        if (Objects.isNull(param) || param.trim().isEmpty()) throw new BadRequestException(name + " is not valid", "40407");
    }

    public static void checkId(Long id, String name) throws BadRequestException {
        if (Objects.isNull(id) || id < 0) throw new BadRequestException(name + " id is not valid", "40407");
    }

    public static void checkDto(GiftCertificateDto dto) throws BadRequestException {
        if (Objects.isNull(dto.getName()) || dto.getName().trim().isEmpty()) throw new BadRequestException("Gift Certificate name is not valid", "40408");
        if (Objects.isNull(dto.getDescription()) || dto.getDescription().trim().isEmpty()) throw new BadRequestException("Gift Certificate description is not valid", "40409");
        if (Objects.isNull(dto.getDuration()) || dto.getDuration() < 1) throw new BadRequestException("Gift Certificate duration is not valid", "40410");
        if (Objects.isNull(dto.getPrice()) || dto.getPrice().compareTo(BigDecimal.ZERO) < 0) throw new BadRequestException("Gift Certificate name is not valid", "40411");
    }

    public static void checkTagDto(TagDto dto) throws BadRequestException {
        if (Objects.isNull(dto.getName()) || dto.getName().trim().isEmpty()) throw new BadRequestException("Tag name is not valid", "40408");
    }
}
