package edu.unimagdalena.actividadservice.repositories;

import edu.unimagdalena.actividadservice.entities.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViajeRepository extends JpaRepository<Viaje, Integer> {

    List<Viaje> findAllByIdViajero(Integer idViajero);
}
