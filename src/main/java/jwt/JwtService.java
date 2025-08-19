package jwt;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;


public class JwtService {

    private RedisTemplate<String, String> redisTemplate;
    // Key helpers (네임스페이스 분리)
    private String Refresh(String jti) { return "refresh:" + jti; }
    private String BlacklistAccess(String jti) { return "black" + jti; }
    private String UserRefreshSet(String userId) { return "user:" + userId + ":refresh"; }

    // Refresh 저장: jti 기준 (TTL ms)
    public void saveRefresh(String jti, String userId, long ttlMs) {
        redisTemplate.opsForValue().set(Refresh(jti), userId, ttlMs, TimeUnit.MILLISECONDS);
        redisTemplate.opsForSet().add(UserRefreshSet(userId), jti);
        redisTemplate.expire(UserRefreshSet(userId), ttlMs, TimeUnit.MILLISECONDS); // 세트 만료 동기화(옵션)
    }

    // Refresh 삭제 (로그아웃)
    public void deleteRefresh(String jti) {
        String userId = redisTemplate.opsForValue().get(Refresh(jti));
        redisTemplate.delete(Refresh(jti));
        if (userId != null) {
            redisTemplate.opsForSet().remove(UserRefreshSet(userId), jti);
        }
    }

    // 유저의 모든 Refresh 강제 삭제
    public void deleteAllRefreshByUser(String userId) {
        String key = UserRefreshSet(userId);
        Set<String> allJti = redisTemplate.opsForSet().members(key);
        if (allJti != null) {
            allJti.forEach(jti -> redisTemplate.delete(Refresh(jti)));
        }
        redisTemplate.delete(key);
    }

    // Access 블랙리스트: 남은 만료 시간만큼 TTL (jti 권장)
    public void blacklistAccess(String jti, long remainingMs) {
        redisTemplate.opsForValue().set(BlacklistAccess(jti), "1", remainingMs, TimeUnit.MILLISECONDS);
    }
    //access블랙리스트
    public boolean isAccessBlacklisted(String jti) {
        return redisTemplate.hasKey(BlacklistAccess(jti));
    }
    //refresh 존재여부
    public boolean existsRefresh(String jti) {
        return redisTemplate.hasKey(Refresh(jti));
    }
}

