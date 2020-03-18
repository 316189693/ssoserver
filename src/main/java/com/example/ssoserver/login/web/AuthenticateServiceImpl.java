package com.example.ssoserver.login.web;

import com.example.ssoserver.authenticate.service.AuthenticateService;
import com.example.ssoserver.entity.SsoUser;
import com.example.ssoserver.login.AuthenticateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

/**
 * Created By willz
 * Date : 2020/3/17
 * Time: 14:49
 */
@Controller
public class AuthenticateServiceImpl {
    @Autowired
    AuthenticateService authenticateService;
    @GetMapping("/validate")
    @ResponseBody
    public String authenticate(HttpServletResponse response, @PathParam("service") String service, @PathParam("ticket") String ticket) {
        SsoUser ssoUser = authenticateService.authTicket(service, ticket);
        if (ssoUser == null) {
            throw new AuthenticateException();
        }
        return String.valueOf(ssoUser.getId());
    }
}
