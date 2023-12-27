package mx.com.compra.service;

import mx.com.compra.dto.ApiResponse;
import mx.com.compra.entity.Cliente;

public interface ClienteService {

	ApiResponse save(Cliente cliente);

	Cliente findById(Long id);

	ApiResponse updateCliente(Long id, Cliente cliente);

	ApiResponse findByIdApi(Long id);

	ApiResponse delete(Long id);

	ApiResponse findAll();

	boolean existsCliente(Long id);

}
