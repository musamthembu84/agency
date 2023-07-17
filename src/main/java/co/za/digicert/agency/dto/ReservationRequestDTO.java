package co.za.digicert.agency.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReservationRequestDTO {

    private long id;
    private String customerName;
    private String reservationLocation;
    private Date reservationDate;
    private int hotelRoom;
    private int luggageSize;
}
