package com.quesmarkt.quesmarktbase.data.request;

import lombok.Data;

/**
 * @author anercan
 */

@Data
public class SignInRequest {
    private String mail;
    private String password;
}
