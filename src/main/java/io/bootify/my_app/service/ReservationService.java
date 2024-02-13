package io.bootify.my_app.service;

import io.bootify.my_app.domain.Reservation;
import io.bootify.my_app.domain.User;
import io.bootify.my_app.model.ReservationDTO;
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

    public List<ReservationDTO> findAll() {
        final List<Reservation> reservations = reservationRepository.findAll(Sort.by("reservationID"));
        return reservations.stream()
                .map(reservation -> mapToDTO(reservation, new ReservationDTO()))
                .toList();
    }

    public ReservationDTO get(final UUID reservationID) {
        return reservationRepository.findById(reservationID)
                .map(reservation -> mapToDTO(reservation, new ReservationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final ReservationDTO reservationDTO) {
        final Reservation reservation = new Reservation();
        mapToEntity(reservationDTO, reservation);
        return reservationRepository.save(reservation).getReservationID();
    }

    public void update(final UUID reservationID, final ReservationDTO reservationDTO) {
        final Reservation reservation = reservationRepository.findById(reservationID)
                .orElseThrow(NotFoundException::new);
        mapToEntity(reservationDTO, reservation);
        reservationRepository.save(reservation);
    }

    public void delete(final UUID reservationID) {
        reservationRepository.deleteById(reservationID);
    }

    private ReservationDTO mapToDTO(final Reservation reservation,
            final ReservationDTO reservationDTO) {
        reservationDTO.setReservationID(reservation.getReservationID());
        reservationDTO.setReservationStartingTime(reservation.getReservationStartingTime());
        reservationDTO.setReservationEndingTime(reservation.getReservationEndingTime());
        reservationDTO.setReservationDate(reservation.getReservationDate());
        reservationDTO.setReservationOwner(reservation.getReservationOwner());
        reservationDTO.setUser(reservation.getUser() == null ? null : reservation.getUser().getId());
        return reservationDTO;
    }

    private Reservation mapToEntity(final ReservationDTO reservationDTO,
            final Reservation reservation) {
        reservation.setReservationStartingTime(reservationDTO.getReservationStartingTime());
        reservation.setReservationEndingTime(reservationDTO.getReservationEndingTime());
        reservation.setReservationDate(reservationDTO.getReservationDate());
        reservation.setReservationOwner(reservationDTO.getReservationOwner());
        final User user = reservationDTO.getUser() == null ? null : userRepository.findById(reservationDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        reservation.setUser(user);
        return reservation;
    }

}
