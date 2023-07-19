package co.za.digicert.agency.service;

import co.za.digicert.agency.entity.Reservation;
import co.za.digicert.agency.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;


    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public boolean createReservation(Reservation request) {
            final Reservation bookingReservation = reservationRepository.findReservationByCustomerNameAndReservationLocationAndHotelRoomAndLuggageSize(
                    request.getCustomerName(), request.getReservationLocation(), request.getHotelRoom(), request.getLuggageSize());
            if (bookingReservation != null) {
                log.error(String.format("Did not create reservation for [%s] as reservation is already created", request.getCustomerName()));
                return false;
            }
            request.setReservationDate(new Date());
            reservationRepository.save(request);
            log.info(String.format("Successfully created reservation for [%s]", request.getCustomerName()));
            return true;
    }

    @Override
    public Optional<Reservation> findReservationById(long id) {
        return reservationRepository.findReservationById(id);
    }

    @Override
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public boolean updateReservation(long id, Reservation request) {
            final Optional<Reservation> existingReservation = reservationRepository.findReservationById(id);

            if (existingReservation.isPresent()) {
                Reservation updatedReservation = getReservation(request, existingReservation);
                reservationRepository.save(updatedReservation);
                log.info(String.format("Updated reservation for user [%s]", request.getCustomerName()));
                return true;
            }
            return false;
    }

    @Override
    public boolean deleteReservation(long id) {

            final Optional<Reservation> deleteExistingReservation = reservationRepository.findReservationById(id);
            if (deleteExistingReservation.isPresent()) {
                reservationRepository.deleteById(id);
                return true;
            }
            return false;
    }

    public boolean deleteAllReservations() {

            final List<Reservation> deleteExistingReservation = reservationRepository.findAll();
            if (!deleteExistingReservation.isEmpty()) {
                reservationRepository.deleteAll();
                log.info(String.format("Deleted total of [%d] all reservations",deleteExistingReservation.size()));
                return true;
            }
            return false;
    }

    private static Reservation getReservation(Reservation request, Optional<Reservation> existingReservation) {
        Reservation updatedReservation = existingReservation.get();
        updatedReservation.setReservationDate(updatedReservation.getReservationDate());
        updatedReservation.setCustomerName(request.getCustomerName());
        updatedReservation.setHotelRoom(request.getHotelRoom());
        updatedReservation.setLuggageSize(request.getLuggageSize());
        updatedReservation.setReservationLocation(request.getReservationLocation());
        return updatedReservation;
    }
}
