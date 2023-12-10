package com.quesmarkt.quesmarktbase.data.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author anercan
 */

@Getter
@Setter
@SuperBuilder
public class SignUpResponse extends ServiceResponse {
    private String jwt;
}
