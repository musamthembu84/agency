package co.za.digicert.agency.controller;

import co.za.digicert.agency.dto.ReservationResponseDTO;
import co.za.digicert.agency.entity.Reservation;
import co.za.digicert.agency.service.ReservationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static co.za.digicert.agency.util.Responses.createResponse;

@RestController
@RequestMapping("/booking")
@Slf4j
@CrossOrigin
public class ReservationController {

    private final ReservationServiceImpl bookingService;
    public static final String RESERVATION_FOUND = "Reservation[s] Found";

    public static final String RESERVATION_NOT_FOUND = "Reservation Not Found";

    public ReservationController(ReservationServiceImpl bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/reservation")
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody Reservation request) {
        HttpHeaders headers = getHttpHeaders();
        try {
            boolean saveUser = bookingService.createReservation(request);
            if (saveUser) {
                return new ResponseEntity<>(createResponse(String.valueOf(HttpStatus.CREATED.value()),
                        String.format("Successfully registered user [%s]",request.getCustomerName()),null), headers, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(createResponse(String.valueOf(HttpStatus.CONFLICT.value()),
                        String.format("User [%s] already exists", request.getCustomerName()),null), headers, HttpStatus.CONFLICT);
            }

        } catch (Exception ex) {
            return new ResponseEntity<>(createResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    String.format("Registration failed because [%s]", ex.getMessage()),null), headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        HttpHeaders headers = getHttpHeaders();
        try {
            List<Reservation> allReservationsOfCustomer = bookingService.findAllReservations();
            if (!allReservationsOfCustomer.isEmpty()) {
                return new ResponseEntity<>(allReservationsOfCustomer, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(getHttpHeaders(),HttpStatus.NO_CONTENT);
            }

        } catch (Exception ex) {
            return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("reservations/{id}")
    public ResponseEntity<ReservationResponseDTO> getReservationById(@PathVariable("id") long id){
        try {
            Optional<Reservation> singleReservation =bookingService.findReservationById(id);
            return singleReservation
                    .map(reservation -> new ResponseEntity<>(createResponse(String.valueOf(HttpStatus.OK.value()),RESERVATION_FOUND, Arrays.asList(singleReservation.get())), getHttpHeaders(), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(createResponse(String.valueOf(HttpStatus.NO_CONTENT.value()),RESERVATION_NOT_FOUND,null),getHttpHeaders(), HttpStatus.NO_CONTENT));

        } catch (Exception ex) {
            return new ResponseEntity<>(createResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),ex.getMessage(),null),getHttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("reservations/{id}")
    public ResponseEntity<ReservationResponseDTO> updateReservation(@PathVariable("id") long id, @RequestBody Reservation request){
        try {
            boolean updateReservation  = bookingService.updateReservation(id,request);
            if (updateReservation) {
                return new ResponseEntity<>(createResponse(String.valueOf(HttpStatus.OK.value()),"Update Successful", null),getHttpHeaders(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(createResponse(String.valueOf(HttpStatus.NO_CONTENT.value()),"Update Failed No Content Found", null),getHttpHeaders(), HttpStatus.NO_CONTENT);
            }

        } catch (Exception ex) {
            return new ResponseEntity<>(createResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),ex.getMessage(),null),getHttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("reservations/{id}")
    public ResponseEntity<ReservationResponseDTO> delete(@PathVariable("id") long id){
        try {
            boolean deleteObservation =  bookingService.deleteReservation(id);
            if(deleteObservation){
                return  new ResponseEntity<>(createResponse(String.valueOf(HttpStatus.OK.value()),"Deleted Successfully",null),getHttpHeaders(),HttpStatus.OK);
            }
            else{
                return  new ResponseEntity<>(createResponse(String.valueOf(HttpStatus.NO_CONTENT.value()),"No Content Found To Be Deleted",null),getHttpHeaders(),HttpStatus.NO_CONTENT);

            }

        } catch (Exception ex) {
            return new ResponseEntity<>(createResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),ex.getMessage(),null),getHttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("reservations/")
    public ResponseEntity<ReservationResponseDTO> deleteAll(){
        try {
            boolean deleteObservation =  bookingService.deleteAllReservations();
            if(deleteObservation){
                return  new ResponseEntity<>(createResponse(String.valueOf(HttpStatus.OK.value()),"Deleted All Records Successfully",null),getHttpHeaders(),HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(createResponse(String.valueOf(HttpStatus.NO_CONTENT.value()),"No Content Found To Be Deleted",null),getHttpHeaders(),HttpStatus.NO_CONTENT);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(createResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),ex.getMessage(),null),getHttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static HttpHeaders getHttpHeaders() {
        return  new HttpHeaders();
    }

}
