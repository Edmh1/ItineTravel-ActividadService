package edu.unimagdalena.actividadservice.repositories;

import edu.unimagdalena.actividadservice.entities.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActividadRepository extends JpaRepository<Actividad, Integer> {

}
