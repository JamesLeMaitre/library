package dev.jtm.library.repositories;

import dev.jtm.library.entities.Rayons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RayonsRepository extends JpaRepository<Rayons,Long> {
    @Query("SELECT count(r.idRayons) from Rayons r ")
    int countRayonsByIdRayons();

    @Query("SELECT r FROM Rayons r WHERE r.idRayons<= 20 or NOT r.idRayons in  ( SELECT rs FROM Rayons rs WHERE rs.idRayons= 1 or rs.idRayons=2)")
    List<Rayons> limitRayons();
}
