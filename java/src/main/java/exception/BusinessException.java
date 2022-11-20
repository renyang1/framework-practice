package exception;

/**
 * @author ryang
 * @Description
 * @date 2022年10月08日 2:16 下午
 */
public class BusinessException extends RuntimeException {

    private int code;

    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
