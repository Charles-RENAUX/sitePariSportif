package ITI.projet.mpb.exceptions;

public class ClientAlreadyException extends RuntimeException {
    public ClientAlreadyException() {
        super();
    }

    public ClientAlreadyException(String message) {
        super(message);
    }

    public ClientAlreadyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientAlreadyException(Throwable cause) {
        super(cause);
    }

    protected ClientAlreadyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
