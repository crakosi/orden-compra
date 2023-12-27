package mx.com.compra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.compra.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
