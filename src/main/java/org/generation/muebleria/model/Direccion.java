package org.generation.muebleria.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="direcciones")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion")
    private Long idDireccion;
    @Column(name = "tipo_direccion", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDireccion tipoDireccion;
    @Column(name = "alias", nullable = false, length = 150)
    private String alias;
    @Column(name = "direccion", nullable = false, length = 300)
    private String direccion;
    @Column(name = "ciudad", nullable = false, length = 150)
    private String ciudad;
    @Column(name = "estado", nullable = false, length = 200)
    private String estado;
    @Column(name = "municipio", nullable = false, length = 200)
    private String municipio;
    @Column(name = "codigo_postal", nullable = false)
    private String codigoPostal;
    @Column(name = "es_predeterminada", columnDefinition = "TINYINT(1)")
    private Boolean esPredeterminada = false;

    //Relacion: muchos -> uno (Usuario)
    @ManyToOne
    @JoinColumn(name="id_usuario", nullable = false)
    @JsonBackReference
    private Usuarios usuario;

    //Relacion: uno -> muchos (Pedidos)
    //(Pedidos) -> se definio [direccion] para el mappedBy
    @OneToMany(mappedBy = "direccion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Pedidos> pedidos;

    public enum TipoDireccion {
        ENVIO,
        FACTURACION,
        ENVIO_Y_FACTURACION
    }
}

