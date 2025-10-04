package edu.unimagdalena.actividadservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "vuelos")
public class Vuelo {

    @Id @Column(name = "id_actividad")
    private Integer idActividad;

    @OneToOne(cascade = CascadeType.REMOVE) @MapsId
    @JoinColumn(name = "id_actividad")
    private Actividad actividad;

    @Column(nullable = false)
    private String aereolineaa;

    @Column(nullable = false)
    private Integer nVuelo;

    @ManyToOne
    @JoinColumn(name = "origen_vuelo", referencedColumnName = "idUbicacion")
    private Ubicacion origenVuelo;

    @ManyToOne
    @JoinColumn(name = "destino_vuelo", referencedColumnName = "idUbicacion")
    private Ubicacion destinoVuelo;
}
