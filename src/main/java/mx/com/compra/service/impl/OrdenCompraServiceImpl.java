package mx.com.compra.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import mx.com.compra.dto.ApiResponse;
import mx.com.compra.entity.OrdenCompra;
import mx.com.compra.entity.Producto;
import mx.com.compra.repository.OrdenCompraRepository;
import mx.com.compra.service.ClienteService;
import mx.com.compra.service.OrdenCompraService;
import mx.com.compra.service.ProductoService;
import mx.com.compra.util.exception.IdNotFoundException;

@Service
@AllArgsConstructor
public class OrdenCompraServiceImpl implements OrdenCompraService {

	private final OrdenCompraRepository ordenCompraRepository;

	private final ClienteService clienteService;
	
	private final ProductoService productoService;

	@Override
	@Transactional
	public ApiResponse save(OrdenCompra ordenCompra) {

		if (ordenCompra.getCliente() != null) {
			var exist = clienteService.existsCliente(ordenCompra.getCliente().getId());
			if (!exist) {
				clienteService.save(ordenCompra.getCliente());
			}
		}
		if( !ordenCompra.getProductos().isEmpty()) {
			 List<Producto> productosGuardados = new ArrayList<>();
			for(Producto producto: ordenCompra.getProductos() ) {
				var exist = productoService.existProducto(producto.getId());
				if(!exist) {
					productosGuardados.add(productoService.save(producto));
				} else {
					Optional<Producto> productoExists =productoService.findById(producto.getId());
					if(productoExists.isPresent()) {
						productosGuardados.add(productoExists.get());
					}
				}
			}
			ordenCompra.setProductos(productosGuardados);
		}
		ordenCompraRepository.save(ordenCompra);
		return ApiResponse.builder().status(HttpStatus.OK).error(null).success(true).data("Orden de Compra Guardada")
				.build();
	}
	
	@Override
	@Transactional
	public ApiResponse updateOrden(Long id, OrdenCompra ordenCompra) {

		OrdenCompra ordenExistente = ordenCompraRepository.findById(id)
				.orElseThrow(() -> new IdNotFoundException("Orden no encontrada con el ID: " + id));

		ordenExistente.setCliente(ordenCompra.getCliente());
		if( !ordenCompra.getProductos().isEmpty()) {
			 List<Producto> productosGuardados = new ArrayList<>();
			for(Producto producto: ordenCompra.getProductos() ) {
				var exist = productoService.existProducto(producto.getId());
				if(!exist) {
					productosGuardados.add(productoService.save(producto));
				} else {
					Optional<Producto> productoExists =productoService.findById(producto.getId());
					if(productoExists.isPresent()) {
						productosGuardados.add(productoExists.get());
					}
				}
			}
			ordenCompra.setProductos(productosGuardados);
		}
		ordenExistente.setTotal(ordenCompra.getTotal());

		return ApiResponse.builder().status(HttpStatus.OK).error(null).success(true).data("Orden Modificado").build();
	}

	@Override
	@Transactional(readOnly = true)
	public ApiResponse findAll() {
		List<OrdenCompra> ordenes = ordenCompraRepository.findAll();
		return ApiResponse.builder().status(HttpStatus.OK).error(null).success(true).data(ordenes).build();

	}

	@Override
	@Transactional(readOnly = true)
	public ApiResponse findById(Long id) {
		OrdenCompra ordenCompra = ordenCompraRepository.findById(id)
				.orElseThrow(() -> new IdNotFoundException("Orden de compra no encontrado con el ID: " + id));
		return ApiResponse.builder().status(HttpStatus.OK).error(null).success(true).data(ordenCompra).build();
	}

	@Override
	@Transactional
	public ApiResponse delete(Long id) {
		ordenCompraRepository.deleteById(id);
		return ApiResponse.builder().status(HttpStatus.OK).error(null).success(true).data("Orden de compra eliminada").build();
	}
}
