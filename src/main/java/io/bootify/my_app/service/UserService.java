package io.bootify.my_app.service;

import io.bootify.my_app.model.Reservation;
import io.bootify.my_app.model.User;
import io.bootify.my_app.repos.ReservationRepository;
import io.bootify.my_app.repos.UserRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedWarning;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public UserService(final UserRepository userRepository,
            final ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<User> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new User()))
                .toList();
    }

    public User get(final UUID id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new User()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final User User) {
        final User user = new User();
        mapToEntity(User, user);
        return userRepository.save(user).getId();
    }

    public void update(final UUID id, final User User) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(User, user);
        userRepository.save(user);
    }

    public void delete(final UUID id) {
        userRepository.deleteById(id);
    }

    private User mapToDTO(final User user, final User User) {
        User.setId(user.getId());
        User.setName(user.getName());
        return User;
    }

    private User mapToEntity(final User User, final User user) {
        user.setName(User.getName());
        return user;
    }

    public ReferencedWarning getReferencedWarning(final UUID id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Reservation userReservation = reservationRepository.findFirstByUser(user);
        if (userReservation != null) {
            referencedWarning.setKey("user.reservation.user.referenced");
            referencedWarning.addParam(userReservation.getReservationID());
            return referencedWarning;
        }
        return null;
    }

}
