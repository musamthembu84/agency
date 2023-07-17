package co.za.digicert.agency.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false, name = "customerName")
    private String customerName;
    @Column(nullable = false, name = "reservationLocation")
    private String reservationLocation;
    @Column(nullable = false, name = "reservationDate")
    private Date reservationDate;
    @Column(nullable = false, name = "hotelRoom")
    private int hotelRoom;
    @Column(nullable = false, name = "luggageSize")
    private int luggageSize;

}
