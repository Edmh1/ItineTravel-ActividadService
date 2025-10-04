package edu.unimagdalena.actividadservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "transportes")
public class Transporte {

    @Id @Column(name = "id_actividad")
    private Integer idActividad;

    @OneToOne @MapsId
    @JoinColumn(name = "id_actividad")
    private Actividad actividad;

    @Column(nullable = false)
    private String tipoTransporte;

    @Column(nullable = false)
    private String empresa;

    @Column
    private String origenTransporte;

    @Column(nullable = false)
    private String destinoTransporte;

}
