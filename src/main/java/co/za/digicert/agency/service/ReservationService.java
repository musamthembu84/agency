package co.za.digicert.agency.service;

import co.za.digicert.agency.entity.Reservation;
import co.za.digicert.agency.exceptions.ReservationExceptionHandler;

import java.util.List;
import java.util.Optional;

public interface ReservationService {
    boolean createReservation(Reservation request) throws ReservationExceptionHandler;
    Optional<Reservation> findReservationById(long id);
    List<Reservation> findAllReservations();

    boolean updateReservation(long id, Reservation request);

    boolean deleteReservation(long id);
}
