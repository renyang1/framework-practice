package Limiting;

import com.google.common.util.concurrent.RateLimiter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ryang
 * @Description
 * @date 2023年03月16日 2:47 下午
 */
public class RateLimiterTest {

    private static final RateLimiter RATE_LIMITER = RateLimiter.create(2);

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(6000);
        for (int i = 0; i < 10; i++) {
            RATE_LIMITER.acquire();
            System.out.println("时间" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "； i=" + i);
        }
    }
}
