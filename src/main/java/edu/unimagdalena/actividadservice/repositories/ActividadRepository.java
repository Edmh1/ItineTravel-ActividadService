package edu.unimagdalena.actividadservice.repositories;

import edu.unimagdalena.actividadservice.entities.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActividadRepository extends JpaRepository<Actividad, Integer> {

    List<Actividad> findByViajeIdViaje(Integer id);

}
