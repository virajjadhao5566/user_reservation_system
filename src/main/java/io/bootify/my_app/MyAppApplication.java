package io.bootify.my_app;

import io.bootify.my_app.model.AmenityType;
import io.bootify.my_app.model.Reservation;
import io.bootify.my_app.model.User;
import io.bootify.my_app.repos.ReservationRepository;
import io.bootify.my_app.repos.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;


@SpringBootApplication
public class MyAppApplication {

    public static void main(final String[] args) {
        SpringApplication.run(MyAppApplication.class, args);
    }
    @Bean
    public CommandLineRunner loadData(UserRepository userRepository,
                                      ReservationRepository reservationRepository) {
        return (args) -> {
            User user = userRepository.save(new User());
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Reservation reservation = Reservation.builder()
                    .reservationDate(localDate)
                    .reservationStartingTime(LocalTime.of(12, 00))
                    .reservationEndingTime(LocalTime.of(13, 00))
                    .user(user)
                    .amenityType(AmenityType.POOL)
                    .build();

            reservationRepository.save(reservation);
        };
    }

}
