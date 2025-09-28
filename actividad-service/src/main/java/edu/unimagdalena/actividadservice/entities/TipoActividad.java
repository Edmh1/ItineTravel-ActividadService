package edu.unimagdalena.actividadservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tipo_actividad")
public class TipoActividad {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoActividad;

    @Column(nullable = false)
    private String nombreTipoActividad;

    @OneToMany(mappedBy = "tipoActividad")
    private List<Actividad> actividades;
}
