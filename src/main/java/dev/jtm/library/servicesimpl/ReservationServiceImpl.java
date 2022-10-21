package dev.jtm.library.servicesimpl;

import dev.jtm.library.entities.Document;
import dev.jtm.library.entities.Reservation;
import dev.jtm.library.entities.security.AppUsers;
import dev.jtm.library.repositories.DocumentRepository;
import dev.jtm.library.repositories.ReservationRepository;
import dev.jtm.library.repositories.security.AppUsersRepository;
import dev.jtm.library.services.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final DocumentRepository documentRepository;
    private final AppUsersRepository appUsersRepository;

    @Override
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation create(Reservation reservation, Long idDocument) {
        Document document = documentRepository.findById(idDocument).orElse(null);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUsers user = appUsersRepository.getCurrentUser(username);
        assert document != null;
        assert user != null;
        reservation.setUsers(user);
        reservation.setDocument(document);
        reservation.setProcessing(true);
        if(document.getQte_res() <= 0){
            document.setQte_res(document.getQuantite() - 1);
        } else {
            document.setQte_res(document.getQte_res() - 1);
        }

        return reservationRepository.save(reservation);
    }

    @Override
    public void delete(Long id) {
        // La suppression normale
        // Reservation reservation = reservationRepository.findById(id).orElse(null);
        // assert reservation != null;
        //  reservationRepository.delete(reservation);
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        assert reservation != null;
         reservation.setSup(true);


    }

    @Override
    public Reservation edit(Reservation reservation, Long id) {
        return null;
    }

    @Override
    public Reservation getById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public int getCountAll() {
        return 0;
    }

    @Override
    public Reservation validRes(Reservation reservation,Long id) {
        Reservation res = reservationRepository.findById(id).orElse(null);
        assert res != null;
        res.setVr(true);
        res.setProcessing(false);
        return null;
    }

    @Override
    public Reservation rejectRes(Reservation reservation,Long id) {
        return null;
    }

    @Override
    public List<Reservation> getAllAcceptReservation() {
        return reservationRepository.getAllValidReservation();
    }

    @Override
    public List<Reservation> getAllRejectReservation() {
        return reservationRepository.getAllRejectReservation();
    }

    @Override
    public List<Reservation> getAllNotDelete() {
        return reservationRepository.getAllNotDelete();
    }
}
