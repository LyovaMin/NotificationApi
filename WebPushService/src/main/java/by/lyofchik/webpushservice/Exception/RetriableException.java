package by.lyofchik.webpushservice.Exception;

public class RetriableException extends RuntimeException{
    public RetriableException(){}

    public RetriableException(String message) {
        super(message);
    }
}
