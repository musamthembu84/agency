package co.za.digicert.agency.dto;

import co.za.digicert.agency.entity.Reservation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ReservationResponseDTO{

    @JsonProperty("result")
    private String result;

    @JsonProperty("code")
    private String code;

    @JsonProperty("data")
    private List<Reservation> data;
}
