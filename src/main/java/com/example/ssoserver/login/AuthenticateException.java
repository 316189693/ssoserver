package com.example.ssoserver.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created By willz
 * Date : 2020/3/17
 * Time: 15:00
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Authenticate fail")
public class AuthenticateException extends RuntimeException {
}
