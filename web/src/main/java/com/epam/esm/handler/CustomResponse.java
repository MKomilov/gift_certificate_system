package com.epam.esm.handler;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomResponse {
    private String message;
    private String code;
}
