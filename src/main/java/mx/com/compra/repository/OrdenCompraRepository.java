package mx.com.compra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.compra.entity.OrdenCompra;

public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long>{

}
