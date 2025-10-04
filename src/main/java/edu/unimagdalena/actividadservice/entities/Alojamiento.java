package edu.unimagdalena.actividadservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "alojamientos")
public class Alojamiento {

    @Id
    @Column (name = "id_actividad")
    private Integer idActividad;

    @OneToOne @MapsId
    @JoinColumn(name = "id_actividad")
    private Actividad actividad;

    @Column(nullable = false)
    private String nombreHotel;

    @Column
    private String direccion;

    @Column
    private LocalDateTime checkIn;

    @Column
    private LocalDateTime checkOut;

    @Column
    private String contacto;

}
