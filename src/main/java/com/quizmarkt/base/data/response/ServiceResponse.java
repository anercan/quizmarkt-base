package com.quizmarkt.base.data.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author anercan
 */

@Getter
@Setter
@SuperBuilder
@ToString
@NoArgsConstructor
public class ServiceResponse {
    private Integer status = 0;
    private String message;
}
