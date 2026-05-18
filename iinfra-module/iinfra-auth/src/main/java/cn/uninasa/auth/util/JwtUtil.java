package cn.uninasa.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * JWT 工具类
 * 基于 jjwt 0.11.5，提供 Token 生成与解析能力
 */
public class JwtUtil {

    /**
     * 密钥（生产环境应从配置中心读取，至少 256 位）
     */
    private static final String SECRET = "iinfra@2024#Jwt$Secret!Key&For^Token*Gen";

    /**
     * Token 有效期：24 小时（毫秒）
     */
    private static final long EXPIRATION = 24 * 60 * 60 * 1000L;

    private static SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成 Token
     *
     * @param subject 主体（通常为用户名）
     * @param claims  自定义声明
     * @return JWT Token 字符串
     */
    public static String generateToken(String subject, Map<String, Object> claims) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 解析 Token，返回 Claims
     *
     * @param token JWT Token
     * @return Claims
     */
    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取 Token 中的用户名（subject）
     */
    public static String getUsername(String token) {
        return parseToken(token).getSubject();
    }

    /**
     * 获取 Token 中的用户ID
     */
    public static Long getUserId(String token) {
        Object userId = parseToken(token).get("userId");
        return userId instanceof Number ? ((Number) userId).longValue() : null;
    }

    /**
     * 判断 Token 是否过期
     */
    public static boolean isExpired(String token) {
        try {
            return parseToken(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 验证 Token 是否有效
     *
     * @param token    JWT Token
     * @param username 期望的用户名
     * @return 是否有效
     */
    public static boolean validateToken(String token, String username) {
        try {
            String subject = getUsername(token);
            return subject.equals(username) && !isExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}
