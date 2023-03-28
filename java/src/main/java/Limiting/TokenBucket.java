package Limiting;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ryang
 * @Description
 * @date 2023年03月16日 4:14 下午
 */
public class TokenBucket {

    // 容量
    private final int capacity;
    // 速率
    private final int rate;
    // 当前可用令牌数
    private int tokens;
    // 上次令牌生成时间戳
    private long lastRefillTime;

    public TokenBucket(int capacity, int rate) {
        this.capacity = capacity;
        this.rate = rate;
        tokens = capacity;
        lastRefillTime = System.currentTimeMillis();
    }

    public synchronized boolean allowRequest() {
        refill();
        if (tokens > 0) {
            tokens--;
        } else {
            return false;
        }
        return true;
    }

    /**
     * 生成令牌
     * @author ryang
     * @date 2023/3/16 4:28 下午
     */
    public void refill() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis > lastRefillTime) {
            // 距离上次令牌生成时间应该生成的令牌数
            int generatedTokens = (int) ((currentTimeMillis - lastRefillTime) / 1000 * rate);
            tokens = Math.min(tokens + generatedTokens, capacity);
            lastRefillTime = currentTimeMillis;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(2000);
        TokenBucket tokenBucket = new TokenBucket(5, 2);
        for (int i = 0; i < 10; i++) {
            boolean rs = tokenBucket.allowRequest();
            System.out.println("时间" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "； rs=" + rs);
        }
        Thread.sleep(1000);
        for (int i = 0; i < 10; i++) {
            boolean rs = tokenBucket.allowRequest();
            System.out.println("时间" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "； rs=" + rs);
        }
    }
}
