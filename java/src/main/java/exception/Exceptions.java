package exception;

/**
 * @author ryang
 * @Description
 * @date 2022年10月08日 1:52 下午
 */
public class Exceptions {

    public static BusinessException ORDER_EXISTS = new BusinessException("订单已存在", 1001);

    public static BusinessException orderExists() {
        return new BusinessException("订单已存在", 1001);
    }
}
