package co.za.digicert.agency.exceptions;

public class ReservationExceptionHandler extends Exception {

    public ReservationExceptionHandler(String errorMessage) {
        super(errorMessage);
    }

    public ReservationExceptionHandler(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}