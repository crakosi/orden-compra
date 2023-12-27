package mx.com.compra.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import mx.com.compra.dto.ApiResponse;
import mx.com.compra.entity.Cliente;
import mx.com.compra.repository.ClienteRepository;
import mx.com.compra.service.ClienteService;
import mx.com.compra.util.exception.IdNotFoundException;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {

	private final ClienteRepository clienteRepository;

	@Override
	@Transactional
	public ApiResponse save(Cliente cliente) {
		clienteRepository.save(cliente);
		return new ApiResponse(HttpStatus.OK, true, "Cliente Guardado", null);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		return clienteRepository.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean existsCliente(Long id) {
		return clienteRepository.existsById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public ApiResponse findByIdApi(Long id) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new IdNotFoundException("Cliente no encontrado con el ID: " + id));
		return ApiResponse.builder().status(HttpStatus.OK).error(null).success(true).data(cliente).build();
	}

	@Override
	@Transactional(readOnly = true)
	public ApiResponse findAll() {
		List<Cliente> clientes = clienteRepository.findAll();

		return ApiResponse.builder().status(HttpStatus.OK).error(null).success(true).data(clientes).build();

	}

	@Override
	@Transactional
	public ApiResponse updateCliente(Long id, Cliente cliente) {

		Cliente clienteExistente = clienteRepository.findById(id)
				.orElseThrow(() -> new IdNotFoundException("Cliente no encontrado con el ID: " + id));

		clienteExistente.setNombre(cliente.getNombre());
		clienteExistente.setPrimerApellido(cliente.getPrimerApellido());
		clienteExistente.setCodigo(cliente.getCodigo());

		return ApiResponse.builder().status(HttpStatus.OK).error(null).success(true).data("Cliente Modificado").build();
	}

	@Override
	@Transactional
	public ApiResponse delete(Long id) {
		clienteRepository.deleteById(id);
		return ApiResponse.builder().status(HttpStatus.OK).error(null).success(true).data("Cliente Eliminado").build();
	}

}
