package org.generation.muebleria.repository;

import org.generation.muebleria.model.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Productos,Long> {
    //listar solo productos activos con su categoria y proveedores
    @Query("SELECT p FROM Productos p JOIN FETCH p.categoria c JOIN FETCH p.proveedor pr WHERE p.activo = true")
    List<Productos> findByActivoTrueWithCategoriasAndProveedores();

    // 1. FILTRADO PARA USUARIO NORMAL (Activos por Categoría)
    @Query("SELECT p FROM Productos p JOIN FETCH p.categoria c JOIN FETCH p.proveedor pr " +
            "WHERE p.activo = true AND c.idCategoria = :categoriaId")
    List<Productos> findActiveByCategoriaId(@Param("categoriaId") Long categoriaId);

    // 2. FILTRADO PARA USUARIO NORMAL (Activos por Proveedor)
    @Query("SELECT p FROM Productos p JOIN FETCH p.categoria c JOIN FETCH p.proveedor pr " +
            "WHERE p.activo = true AND pr.idProveedor = :proveedorId")
    List<Productos> findActiveByProveedorId(@Param("proveedorId") Long proveedorId);

    // 3. FILTRADO PARA ADMINISTRADOR (Activos por Categoria Y/O Proveedor)
    @Query("SELECT p FROM Productos p JOIN FETCH p.categoria c JOIN FETCH p.proveedor pr " +
            "WHERE p.activo = true " +
            "AND (:categoriaId IS NULL OR c.idCategoria = :categoriaId) " +
            "AND (:proveedorId IS NULL OR pr.idProveedor = :proveedorId)")
    List<Productos> filterActiveByCategoriaAndProveedor(@Param("categoriaId") Long categoriaId, @Param("proveedorId") Long proveedorId);
}
