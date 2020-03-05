package com.joneying.common.config;

import com.joneying.common.constant.SecurityConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "security.fast")
public class SecurityConfig {

    private String defaultGotoUrl;

    private String loginPage;

    private String loginRequest;

    private String ticketName;

    public String getTicketName() {
        if(StringUtils.isEmpty(ticketName)){
            ticketName = SecurityConstant.TICKET;
        }
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getLoginRequest() {
        return loginRequest;
    }

    public void setLoginRequest(String loginRequest) {
        this.loginRequest = loginRequest;
    }

    public String getDefaultGotoUrl() {
        return defaultGotoUrl;
    }

    public void setDefaultGotoUrl(String defaultGotoUrl) {
        this.defaultGotoUrl = defaultGotoUrl;
    }
}
