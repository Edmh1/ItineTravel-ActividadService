package edu.unimagdalena.actividadservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "actividades")
public class Actividad {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad")
    private Integer idActividad;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_viaje", referencedColumnName = "id_viaje")
    private Viaje viaje;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_tipo_actividad", referencedColumnName = "idTipoActividad")
    private TipoActividad tipoActividad;

    @Column(nullable = false)
    private String titulo;

    @Column
    private String descripcionActividad;

    @Column
    private LocalDateTime fecha;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_ubicacion", referencedColumnName = "idUbicacion")
    private Ubicacion ubicacion;

    @Column
    private float costo;

    @OneToOne(mappedBy = "actividad")
    private Alojamiento alojamiento;

    @OneToOne(mappedBy = "actividad")
    private Vuelo vuelo;

    @OneToOne(mappedBy = "actividad")
    private Transporte transporte;

}
