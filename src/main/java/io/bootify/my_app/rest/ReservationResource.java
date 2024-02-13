package io.bootify.my_app.rest;

import io.bootify.my_app.model.ReservationDTO;
import io.bootify.my_app.service.ReservationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationResource {

    private final ReservationService reservationService;

    public ReservationResource(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @GetMapping("/{reservationID}")
    public ResponseEntity<ReservationDTO> getReservation(
            @PathVariable(name = "reservationID") final UUID reservationID) {
        return ResponseEntity.ok(reservationService.get(reservationID));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createReservation(
            @RequestBody @Valid final ReservationDTO reservationDTO) {
        final UUID createdReservationID = reservationService.create(reservationDTO);
        return new ResponseEntity<>(createdReservationID, HttpStatus.CREATED);
    }

    @PutMapping("/{reservationID}")
    public ResponseEntity<UUID> updateReservation(
            @PathVariable(name = "reservationID") final UUID reservationID,
            @RequestBody @Valid final ReservationDTO reservationDTO) {
        reservationService.update(reservationID, reservationDTO);
        return ResponseEntity.ok(reservationID);
    }

    @DeleteMapping("/{reservationID}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable(name = "reservationID") final UUID reservationID) {
        reservationService.delete(reservationID);
        return ResponseEntity.noContent().build();
    }

}
