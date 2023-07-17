package co.za.digicert.agency.controller;

import co.za.digicert.agency.entity.Reservation;
import co.za.digicert.agency.service.ReservationServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ReservationController.class)
@Import(ReservationController.class)
public class ReservationControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ReservationServiceImpl reservationService;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testCreateSuccessWhenInputGiven() throws Exception {
        when(reservationService.createReservation(any())).thenReturn(true);
        MvcResult mvcResult = mvc.perform(post("/booking/reservation")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(createReservation()))).andReturn();
        assertEquals(201, mvcResult.getResponse().getStatus());
    }

    @Test
    void testCreateFailureWhenInputGiven() throws Exception {
        when(reservationService.createReservation(any())).thenReturn(false);
        MvcResult mvcResult = mvc.perform(post("/booking/reservation")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(createReservation()))).andReturn();
        assertEquals(409, mvcResult.getResponse().getStatus());
    }

    @Test
    void testSuccessRetrievingData() throws Exception {
        when(reservationService.findAllReservations()).thenReturn(List.of(createReservation()));
        MvcResult mvcResult = mvc
                .perform(get("/booking/reservations")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }
    @Test
    void testBadRequestWhenRetrievingSingleReservation() throws Exception {
        when(reservationService.findReservationById(anyLong())).thenReturn(Optional.of(createReservation()));
        MvcResult mvcResult = mvc
                .perform(get("/booking/reservations/id")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    void testFailureRetrievingData() throws Exception {
        when(reservationService.findAllReservations()).thenReturn(new ArrayList<>());
        MvcResult mvcResult = mvc
                .perform(get("/booking/reservations")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(204, mvcResult.getResponse().getStatus());
    }

    @Test
    void testUnknownURLWithIncorrectData() throws Exception {
        MvcResult mvcResult = mvc.perform(post("/UNKNOWN")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(createReservation()))).andReturn();
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    private Reservation createReservation() {
        Reservation  reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationLocation("Johannesburg");
        reservation.setCustomerName("Peter");
        return reservation;
    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}