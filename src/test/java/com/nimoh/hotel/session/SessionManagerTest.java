package com.nimoh.hotel.session;

import com.nimoh.hotel.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.*;

public class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void 세션생성성공(){

        MockHttpServletResponse response = new MockHttpServletResponse();
        User user = new User();
        sessionManager.createSession(user,response);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(user);
    }

    @Test
    void 세션만료성공(){

        MockHttpServletResponse response = new MockHttpServletResponse();
        User user = new User();
        sessionManager.createSession(user,response);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        assertThat(expired).isNull();
    }


}
