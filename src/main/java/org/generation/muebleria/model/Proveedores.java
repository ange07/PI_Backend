package org.generation.muebleria.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "proveedores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Proveedores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Long idProveedor;
    @Column(name = "nombre_empresa", nullable = false, length = 200)
    private String nombreEmpresa;
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;
    @Column(name = "telefono", nullable = false, length = 100)
    private String telefono;
    @Column(name = "correo", nullable = false, length = 200)
    private String correo;
    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;
    @Column(name = "activo", columnDefinition = "TINYINT(1)")
    private Boolean activo = true;
    @Column(name = "fecha_registro")
    @CreationTimestamp //hibernate crea la fecha una sola vez
    private LocalDateTime fechaRegistro;
    @Column(name = "fecha_actualizacion", nullable = false)
    @UpdateTimestamp //hibernate actualiza la fecha cada update/add
    private LocalDateTime fechaActualizacion;

    //Relacion uno -> mucho (Productos)
    //(Productos) -> se definio [proveedor] para el mappedBy
    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Productos> productos;

}