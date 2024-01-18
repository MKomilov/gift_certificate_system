package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode()
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TagDto implements AbstractDto{
    private String name;
}
