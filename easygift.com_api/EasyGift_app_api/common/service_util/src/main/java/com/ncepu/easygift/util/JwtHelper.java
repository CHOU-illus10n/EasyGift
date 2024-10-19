package com.ncepu.easygift.util;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;


public class JwtHelper {
    private static long tokenExpiration = 24*60*60*1000;
    private static String tokenSignKey = "123456"; // 盐值

    public static String createToken(Long userId, Long communityId) {
        String token = Jwts.builder()
                .setSubject("easygify-user") // 设置主题
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)) // 设置token过期时间
                .claim("userId", userId)
                .claim("communityId", communityId)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }
    public static Long getUserId(String token) {
        if(StringUtils.isEmpty(token)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Long userId = claims.get("userId", Integer.class).longValue();
        return userId;
    }
    public static Integer getUserType(String token) {
        if(StringUtils.isEmpty(token)) return null;
        Jws<Claims> claimsJws
                = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (Integer)claims.get("userType");
    }
    public static Long getCommunityId(String token) {
        if(StringUtils.isEmpty(token)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Long communityId = claims.get("communityId", Integer.class).longValue();
        return communityId;
    }

    public static void main(String[] args) {
        String token = JwtHelper.createToken(123456L, 1L);
        System.out.println(token);
        System.out.println(JwtHelper.getUserId(token));
        System.out.println(JwtHelper.getUserType(token));
    }
}
