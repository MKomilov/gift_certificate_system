package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode()
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GiftCertificateDto implements AbstractDto{
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private List<Long> tagIds;

    public GiftCertificateDto(String name, String description, BigDecimal price, Integer duration) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
    }
}
