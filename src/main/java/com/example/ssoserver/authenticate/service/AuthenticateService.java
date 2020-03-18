package com.example.ssoserver.authenticate.service;

import com.example.ssoserver.entity.App;
import com.example.ssoserver.entity.SsoUser;
import com.example.ssoserver.util.EncryptDecryptUtil;
import com.example.ssoserver.util.RedisUtil;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created By willz
 * Date : 2020/3/17
 * Time: 15:16
 */
@Service
public class AuthenticateService {
    private String splitor = "-htjy-";
    @Autowired
    RedisUtil redisUtil;
    public SsoUser authTGC(String service, String tgc) {
        if (StringUtils.isBlank(service) || StringUtils.isBlank(tgc)) {
            return null;
        }
        Object obj = redisUtil.get(tgc);
        if (obj == null) {
            return null;
        }
        SsoUser ssoUser = (SsoUser) obj;
        if (ssoUser.getAppList() == null || ssoUser.getAppList().size() <= 0) {
            return null;
        }
        List<App> appList = ssoUser.getAppList().stream().filter((o)->o.getUrl().equals(service)).collect(Collectors.toList());
        if (appList == null || appList.size() <= 0) {
            return null;
        }
        return ssoUser;
    }

    public SsoUser authTicket(String service,  String ticket) {
        if (StringUtils.isBlank(service) || StringUtils.isBlank(ticket)) {
            return null;
        }
        Object obj = redisUtil.get(ticket);
        if (obj == null) {
            return null;
        }
        String tgc = String.valueOf(obj);
        Object ssoUserObj = redisUtil.get(String.valueOf(tgc));
        if (ssoUserObj == null) {
            return null;
        }
        SsoUser ssoUser = (SsoUser) ssoUserObj;
        if (ssoUser.getAppList() == null || ssoUser.getAppList().size() <= 0) {
            return null;
        }
        List<App> appList = ssoUser.getAppList().stream().filter((o)->o.getUrl().equals(service)).collect(Collectors.toList());
        if (appList == null || appList.size() <= 0) {
            return null;
        }
        if (ssoUser.getAuthorizationApp() == null) {
            ssoUser.setAuthorizationApp(new ArrayList<>());
        }
        ssoUser.getAuthorizationApp().addAll(appList);
        redisUtil.lSet(tgc, ssoUser, 7200);
        return ssoUser;
    }

    public String generateTGC(String service, String salt) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(service).append(salt).append(RandomUtils.nextLong());
        return EncryptDecryptUtil.encrypt(stringBuffer.toString());
    }

    public String generateTicket(String service){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(service).append(RandomUtils.nextLong());
        return EncryptDecryptUtil.encrypt(stringBuffer.toString());
    }
}
