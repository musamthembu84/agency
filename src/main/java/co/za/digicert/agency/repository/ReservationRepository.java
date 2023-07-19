package co.za.digicert.agency.repository;

import co.za.digicert.agency.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
     Reservation findReservationByCustomerNameAndReservationLocationAndHotelRoomAndLuggageSize(String name, String location, int hotelRoom,int luggageSize);
     Optional<Reservation> findReservationById(long id);

}
