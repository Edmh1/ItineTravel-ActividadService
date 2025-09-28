package edu.unimagdalena.actividadservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "viajes")
public class Viaje {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_viaje")
    private Integer idViaje;

    @Column(nullable = false)
    private Integer idViajero;

    @Column(nullable = false)
    private String nombreViaje;

    @Column
    private String descripcion;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    @OneToMany(mappedBy = "viaje", cascade = CascadeType.REMOVE)
    private List<Actividad> actividades;
}
