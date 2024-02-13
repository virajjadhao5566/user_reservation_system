package io.bootify.my_app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReservationDTO {

    private UUID reservationID;

    @Schema(type = "string", example = "18:30")
    private LocalTime reservationStartingTime;

    @Schema(type = "string", example = "18:30")
    private LocalTime reservationEndingTime;

    private LocalDate reservationDate;

    @Size(max = 255)
    private String reservationOwner;

    @NotNull
    private UUID user;

}
