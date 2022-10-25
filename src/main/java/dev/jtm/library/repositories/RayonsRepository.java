package dev.jtm.library.repositories;

import dev.jtm.library.entities.Rayons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RayonsRepository extends JpaRepository<Rayons,Long> {
    @Query("SELECT count(r.idRayons) from Rayons r ")
    int countRayonsByIdRayons();
}
