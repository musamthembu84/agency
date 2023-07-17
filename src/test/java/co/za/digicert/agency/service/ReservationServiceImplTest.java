package co.za.digicert.agency.service;

import co.za.digicert.agency.entity.Reservation;
import co.za.digicert.agency.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@SpringBootTest
class ReservationServiceImplTest {

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Test
    public void testSuccessWhenCreatingNewReservation() {
        boolean createReservation = reservationService.createReservation(createReservation());
        assertTrue(createReservation);
    }

    @Test
    void testFailureWhenCreatingNewReservation() {
        when(reservationRepository.findReservationByCustomerNameAndReservationLocation(any(), any())).thenReturn(createReservation());
        boolean createReservation = reservationService.createReservation(createReservation());
        assertFalse(createReservation);
    }

    @Test
    void testSuccessGettingReservationById() {
        when(reservationRepository.findReservationById(anyLong())).thenReturn(Optional.of(createReservation()));
        Optional<Reservation> reservationById = reservationService.findReservationById(1);
        assertEquals(1, reservationById.get().getId());
    }

    @Test
    void testFailureGettingReservationById() {
        when(reservationRepository.findReservationById(anyLong())).thenReturn(Optional.of(createReservation()));
        Optional<Reservation> reservationById = reservationService.findReservationById(1);
        assertNotEquals(2, reservationById.get().getId());
    }


    @Test
    void testSuccessGettingAllRecords() {
        List<Reservation> myList = new ArrayList<>();
        myList.add(createReservation());
        when(reservationRepository.findAll()).thenReturn(myList);
        List<Reservation> allReservations = reservationService.findAllReservations();
        assertEquals(1, allReservations.size());
    }

    @Test
    void testFailureGettingAllRecords() {
        List<Reservation> myList = new ArrayList<>();
        myList.add(createReservation());
        when(reservationRepository.findAll()).thenReturn(myList);
        List<Reservation> allReservations = reservationService.findAllReservations();
        assertNotEquals(0, allReservations.size());
    }

    @Test
    void testSuccessOfUpdatingRecord() {
        when(reservationRepository.findReservationById(anyLong())).thenReturn(Optional.of(createReservation()));
        boolean updateReservation = reservationService.updateReservation(1, createReservation());
        assertTrue(updateReservation);
    }

    @Test
    void testFailureOfUpdatingRecord() {
        when(reservationRepository.findReservationById(anyLong())).thenReturn(Optional.empty());
        boolean updateReservation = reservationService.updateReservation(5, createReservation());
        assertFalse(updateReservation);
    }

    private Reservation createReservation() {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationLocation("Johannesburg");
        reservation.setCustomerName("Customer");
        return reservation;
    }

}