package dev.jtm.library.repositories;

import dev.jtm.library.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query("SELECT r FROM Reservation r WHERE r.vr = true ")
    List<Reservation> getAllValidReservation();

    @Query("SELECT r FROM Reservation r WHERE r.vr = false ")
    List<Reservation> getAllRejectReservation();

    @Query("SELECT r FROM Reservation r WHERE r.sup = false ")
    List<Reservation> getAllNotDelete();
}
