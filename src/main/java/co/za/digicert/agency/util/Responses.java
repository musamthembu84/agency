package co.za.digicert.agency.util;

import co.za.digicert.agency.dto.ReservationResponseDTO;
import co.za.digicert.agency.entity.Reservation;

import java.util.List;

public class Responses<T> {

    public static ReservationResponseDTO createResponse(String code, String result, List<Reservation> reservations) {
        ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO();
        reservationResponseDTO.setResult(result);
        reservationResponseDTO.setCode(code);
        reservationResponseDTO.setData(reservations);
        return reservationResponseDTO;
    }

}
