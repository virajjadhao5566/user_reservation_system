package io.bootify.my_app.repos;

import io.bootify.my_app.model.Reservation;
import io.bootify.my_app.model.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    Reservation findFirstByUser(User user);

}
