package com.epam.esm.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Tag implements AbstractModel {
    private Long id;
    private String name;
}
