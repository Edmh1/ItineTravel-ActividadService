package edu.unimagdalena.actividadservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ubicaciones")
public class Ubicacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUbicacion;

    @Column(nullable = false)
    private String nombreUbicacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_padre")
    private Ubicacion padre;


    @OneToMany(mappedBy = "padre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ubicacion> hijos = new ArrayList<>();

    @OneToMany(mappedBy = "origenVuelo")
    private List<Vuelo> vuelosOrigen = new ArrayList<>();

    @OneToMany(mappedBy = "destinoVuelo")
    private List<Vuelo> vuelosDestino = new ArrayList<>();
}
