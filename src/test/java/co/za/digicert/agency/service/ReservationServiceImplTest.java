package co.za.digicert.agency.service;

import co.za.digicert.agency.entity.Reservation;
import co.za.digicert.agency.repository.ReservationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


@SpringBootTest
class ReservationServiceImplTest {

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Test
    public void testSuccessWhenCreatingNewReservation() {
        boolean createReservation = reservationService.createReservation(mockReservationObject());
        assertTrue(createReservation);
    }

    @Test
    void testFailureWhenCreatingNewReservation() {
        when(reservationRepository.findReservationByCustomerNameAndReservationLocationAndHotelRoomAndLuggageSize(any(), any(),anyInt(),anyInt())).thenReturn(mockReservationObject());
        boolean createReservation = reservationService.createReservation(mockReservationObject());
        assertFalse(createReservation);
    }

    @Test
    void testNullPointerExceptionWhenUsingNullReservation() throws NullPointerException {
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            reservationService.createReservation(null);
        });
        Assertions.assertEquals("Cannot invoke \"co.za.digicert.agency.entity.Reservation.getCustomerName()\" because \"request\" is null", thrown.getMessage());
    }

    @Test
    void testSuccessGettingReservationById() {
        when(reservationRepository.findReservationById(anyLong())).thenReturn(Optional.of(mockReservationObject()));
        Optional<Reservation> reservationById = reservationService.findReservationById(1);
        assertEquals(1, reservationById.get().getId());
    }

    @Test
    void testFailureGettingReservationById() {
        when(reservationRepository.findReservationById(anyLong())).thenReturn(Optional.of(mockReservationObject()));
        Optional<Reservation> reservationById = reservationService.findReservationById(1);
        assertNotEquals(2, reservationById.get().getId());
    }

    @Test
    void testExceptionWhenTryingToGetReservation() throws NullPointerException {
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            reservationService.findReservationById(Long.parseLong(null));
        });
        Assertions.assertEquals("Cannot parse null string", thrown.getMessage());
    }


    @Test
    void testSuccessGettingAllRecords() {
        List<Reservation> myList = new ArrayList<>();
        myList.add(mockReservationObject());
        when(reservationRepository.findAll()).thenReturn(myList);
        List<Reservation> allReservations = reservationService.findAllReservations();
        assertEquals(1, allReservations.size());
    }

    @Test
    void testFailureGettingAllRecords() {
        List<Reservation> myList = new ArrayList<>();
        myList.add(mockReservationObject());
        when(reservationRepository.findAll()).thenReturn(myList);
        List<Reservation> allReservations = reservationService.findAllReservations();
        assertNotEquals(0, allReservations.size());
    }

    @Test
    void testSuccessOfUpdatingRecord() {
        when(reservationRepository.findReservationById(anyLong())).thenReturn(Optional.of(mockReservationObject()));
        boolean updateReservation = reservationService.updateReservation(1, mockReservationObject());
        assertTrue(updateReservation);
    }

    @Test
    void testFailureOfUpdatingRecord() {
        when(reservationRepository.findReservationById(anyLong())).thenReturn(Optional.empty());
        boolean updateReservation = reservationService.updateReservation(5, mockReservationObject());
        assertFalse(updateReservation);
    }

    @Test
    void testSuccessDeletingAllRecords() {
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(mockReservationObject()));
        boolean deleteAllReservations = reservationService.deleteAllReservations();
        assertTrue(deleteAllReservations);
    }



    @Test
    void testFailureDeletingAllRecords() throws NullPointerException {
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            when(reservationRepository.findAll()).thenReturn(null);
            reservationService.deleteAllReservations();
        });
        Assertions.assertEquals("Cannot invoke \"java.util.List.isEmpty()\" because \"deleteExistingReservation\" is null", thrown.getMessage());
    }

    private Reservation mockReservationObject() {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationLocation("Johannesburg");
        reservation.setCustomerName("Peter Brown");
        reservation.setHotelRoom(10);
        reservation.setHotelRoom(1);
        return reservation;
    }

}