package com.example.ssoserver.login.web;

import com.example.ssoserver.authenticate.service.AuthenticateService;
import com.example.ssoserver.dao.SsoUserDao;
import com.example.ssoserver.entity.App;
import com.example.ssoserver.entity.SsoUser;
import com.example.ssoserver.login.AuthenticateException;
import com.example.ssoserver.login.vo.LoginForm;
import com.example.ssoserver.util.EncryptDecryptUtil;
import com.example.ssoserver.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created By willz
 * Date : 2020/3/17
 * Time: 10:43
 */
@Controller
public class LoginServiceImpl {
    @Autowired
    AuthenticateService authenticateService;

    @Autowired
    SsoUserDao ssoUserDao;

    @Autowired
    RedisUtil redisUtil;

    @GetMapping("/login")
    public String loginForm(String service, Model model) {
        LoginForm loginForm = new LoginForm();
        loginForm.setService(service);
        model.addAttribute("loginForm", loginForm);
        return "login";
    }

    @PostMapping("login")
    public String login(HttpServletResponse response, @CookieValue(name = "CASTGC", required = false) Cookie casTgc, @Valid LoginForm loginForm, BindingResult bindingResult) {
        if (casTgc != null && authenticateService.authTGC(loginForm.getService(), casTgc.getValue()) == null) {
            casTgc.setMaxAge(0);
            response.addCookie(casTgc);
            return "login";
        } else if (casTgc == null) {
            if (bindingResult.hasErrors()) {
                bindingResult.addError(new FieldError("loginForm", "error", "Invalid Account / password"));
                return "login";
            }
            SsoUser ssoUser =  ssoUserDao.selectByUserNameOrEmail(loginForm.getUserName(), loginForm.getUserName());
            if (ssoUser == null || !ssoUser.getPassword().equals(EncryptDecryptUtil.md5(loginForm.getPassword(),ssoUser.getSalt()))){
                bindingResult.addError(new FieldError("loginForm", "error", "User not found in SSO"));
                return "login";
            }
            String tgc =  authenticateService.generateTGC(loginForm.getService(), ssoUser.getSalt());
            if (StringUtils.isBlank(tgc)) {
                bindingResult.addError(new FieldError("loginForm", "error", " get TGC fail"));
                return "login";
            }
            casTgc = new Cookie("CASTGC", tgc);
            casTgc.setMaxAge(30 * 60);
            response.addCookie(casTgc);
            redisUtil.set(tgc, ssoUser, 7200);
        }
        String ticket = authenticateService.generateTicket(loginForm.getService());
        redisUtil.set(ticket, casTgc.getValue(), 20);
        return "redirect:" + loginForm.getService() + "?ticket=" + ticket;
    }

    @GetMapping("/logOutAll")
    public void logOutAll(@PathParam("TGC") String tgc, @PathParam("service") String service) {
        SsoUser ssoUser = authenticateService.authTGC(service, tgc);
        if (ssoUser == null) {
            throw new AuthenticateException();
        }
        if(ssoUser.getAuthorizationApp().stream().filter(app->app.getUrl().equals(service)).count() > 0) {
            ssoUser.getAuthorizationApp().stream().forEach(app->{
                doLogOut(app, ssoUser.getId());
            });
        }
    }

    @GetMapping("/logout")
    @ResponseBody
    public String logOut(@PathParam("userId") int userId) {
      return String.valueOf(userId);
    }

    private void doLogOut(App app, int ssoUserId) {
        try {
            URL url = new URL(app.getLogOutUrl()+"?userId="+ssoUserId);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod(HttpMethod.GET.name());
            httpURLConnection.connect();
            httpURLConnection.setConnectTimeout(3000);
        } catch (Exception e) {

        } finally {

        }


    }

}
