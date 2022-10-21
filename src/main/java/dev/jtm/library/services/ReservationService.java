package dev.jtm.library.services;

import com.sun.org.apache.xpath.internal.operations.Bool;
import dev.jtm.library.entities.Rayons;
import dev.jtm.library.entities.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> getAll();
    Reservation create(Reservation reservation,Long idDocument);
    void delete(Long id);
    Reservation edit(Reservation reservation,Long id);
    Reservation getById(Long id);
    int getCountAll();

    Reservation validRes(Reservation reservation,Long id);
    Reservation rejectRes(Reservation reservation,Long id);

    List<Reservation> getAllAcceptReservation();
    List<Reservation> getAllRejectReservation();

    // Liste des reservations non supprimées
    List<Reservation> getAllNotDelete();

}
