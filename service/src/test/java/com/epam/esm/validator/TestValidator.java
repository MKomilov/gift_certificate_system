package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class TestValidator {
    private static final GiftCertificateDto correctDto = new GiftCertificateDto("Certificate", "its description", new BigDecimal("3.4"), 10);
    private static final GiftCertificateDto invalidNameDto = new GiftCertificateDto(" ", "its description", new BigDecimal("3.4"), 10);
    private static final GiftCertificateDto invalidDescriptionDto = new GiftCertificateDto("Certificate", " ", new BigDecimal("3.4"), 10);
    private static final GiftCertificateDto invalidPriceDto = new GiftCertificateDto("Certificate", " ", null, 10);
    private static final GiftCertificateDto invalidDurationDto = new GiftCertificateDto("Certificate", " ", null, -10);
    @Test
    void testNullParam(){
        Assertions.assertThrows(BadRequestException.class, () -> Validator.checkParam(null, "Name"));
    }

    @Test
    void testEmptyParam(){
        Assertions.assertThrows(BadRequestException.class, () -> Validator.checkParam("  ", "Name"));
    }

    @Test
    void testCorrectParam(){
        Assertions.assertDoesNotThrow(() -> Validator.checkParam("Correct value", "Name"));
    }

    @Test
    void testNullId(){
        Assertions.assertThrows(BadRequestException.class, () -> Validator.checkId(null, "Name"));
    }

    @Test
    void testNegativeId(){
        Assertions.assertThrows(BadRequestException.class, () -> Validator.checkId(-10L, "Name"));
    }

    @Test
    void testCorrectId(){
        Assertions.assertDoesNotThrow(() -> Validator.checkId(10L, "Name"));
    }

    @Test
    void testCorrectDto(){
        Assertions.assertDoesNotThrow(() -> Validator.checkDto(correctDto));
    }

    @Test
    void testInvalidNameDto(){
        Assertions.assertThrows(BadRequestException.class, () -> Validator.checkDto(invalidNameDto));
    }

    @Test
    void testInvalidDescriptionDto(){
        Assertions.assertThrows(BadRequestException.class, () -> Validator.checkDto(invalidDescriptionDto));
    }

    @Test
    void testInvalidPriceDto(){
        Assertions.assertThrows(BadRequestException.class, () -> Validator.checkDto(invalidPriceDto));
    }

    @Test
    void testInvalidDurationDto(){
        Assertions.assertThrows(BadRequestException.class, () -> Validator.checkDto(invalidDurationDto));
    }
}
