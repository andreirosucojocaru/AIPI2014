package ro.pub.cs.aipi.lab07.dataaccess;

public class DataBaseException extends Exception {

    private static final long serialVersionUID = 2014L;

    private static final String MESSAGE = "Error executing database operation";

    public DataBaseException() {
        super(MESSAGE);
    }

    public DataBaseException(String message) {
        super(MESSAGE + ": " + message);
    }

    public DataBaseException(Throwable cause) {
        super(MESSAGE, cause);
    }

    public DataBaseException(String message, Throwable cause) {
        super(MESSAGE + ": " + message, cause);
    }

}
