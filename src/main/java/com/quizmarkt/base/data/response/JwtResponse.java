package com.quizmarkt.base.data.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author anercan
 */

@Getter
@Setter
@SuperBuilder
public class JwtResponse extends ServiceResponse {
    private String jwt;
}
