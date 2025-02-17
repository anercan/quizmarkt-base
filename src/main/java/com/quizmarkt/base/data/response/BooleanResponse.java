package com.quizmarkt.base.data.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * @author anercan
 */

@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
public class BooleanResponse {
    boolean value;
}
