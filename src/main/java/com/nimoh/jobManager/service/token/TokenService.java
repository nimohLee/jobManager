package com.nimoh.jobManager.service.token;

import javax.servlet.http.Cookie;

public interface TokenService {
    Cookie accessTokenRefresh(Cookie[] cookies);
}
