package dev.jtm.library.repositories;

import dev.jtm.library.entities.Rayons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RayonsRepository extends JpaRepository<Rayons,Long> {
    @Query("SELECT count(r.id) from Rayons r ")
    int countRayonsByIdRayons();

    @Query("SELECT r FROM Rayons r WHERE r.id<= 20 or r.id NOT in ( SELECT r1 FROM Rayons r1 WHERE r1.id= 1 or r1.id=2)")
    List<Rayons> limitRayons();
}
