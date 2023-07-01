package com.heyprojecthub.ilproperty.filter;

import com.heyprojecthub.ilproperty.domain.LoginUser;
import com.heyprojecthub.ilproperty.utils.JwtUtil;
import com.heyprojecthub.ilproperty.utils.RedisCache;
import io.jsonwebtoken.Claims;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // GET TOKEN
        String token = request.getHeader("token");
        if (StringUtil.isNullOrEmpty(token)) {
            // 放行
            filterChain.doFilter(request, response);
            return;
        }

        String userId;
        // INTERPRET TOKEN
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid Token");
        }

        // GET USER INFORMATION FROM REDIS
        String redisKey = "login:" + userId;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("Invalid Token");
        }

        // STORE RECORD INTO SECURITYCONTEXTHOLDER
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication userNamePasswordAuthentication = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        context.setAuthentication(userNamePasswordAuthentication);

        filterChain.doFilter(request, response);
    }
}
