package mx.com.compra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.compra.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{

}
