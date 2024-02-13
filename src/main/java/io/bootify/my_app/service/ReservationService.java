package io.bootify.my_app.service;

import io.bootify.my_app.model.Reservation;
import io.bootify.my_app.repos.ReservationRepository;
import io.bootify.my_app.repos.UserRepository;
import io.bootify.my_app.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public ReservationService(final ReservationRepository reservationRepository,
            final UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    public List<Reservation> findAll() {
        final List<Reservation> reservations = reservationRepository.findAll(Sort.by("reservationID"));
        return reservations.stream().toList();
    }

    public Reservation get(final UUID reservationID) {
        return reservationRepository.findById(reservationID)
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final Reservation Reservation) {
        final Reservation reservation = new Reservation();
        return reservationRepository.save(reservation).getReservationID();
    }

    public void update(final UUID reservationID, final Reservation Reservation) {
        final Reservation reservation = reservationRepository.findById(reservationID)
                .orElseThrow(NotFoundException::new);
        reservationRepository.save(reservation);
    }

    public void delete(final UUID reservationID) {
        reservationRepository.deleteById(reservationID);
    }

}
