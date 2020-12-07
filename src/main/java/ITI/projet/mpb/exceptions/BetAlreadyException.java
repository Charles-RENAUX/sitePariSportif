package ITI.projet.mpb.exceptions;

public class BetAlreadyException extends RuntimeException {
    public BetAlreadyException() {
        super();
    }

    public BetAlreadyException(String message) {
        super(message);
    }

    public BetAlreadyException(String message, Throwable cause) {
        super(message, cause);
    }

    public BetAlreadyException(Throwable cause) {
        super(cause);
    }

    protected BetAlreadyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
