package mx.com.compra.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import mx.com.compra.dto.ApiResponse;
import mx.com.compra.entity.Producto;
import mx.com.compra.repository.ProductoRepository;
import mx.com.compra.service.ProductoService;
import mx.com.compra.util.exception.IdNotFoundException;

@Service
@AllArgsConstructor
public class ProductoServiceImpl  implements ProductoService{
	
	
	private final ProductoRepository productoRepository;
	

	@Override
	@Transactional
	public Producto save(Producto producto) {
		return productoRepository.save(producto);
	}
	
	@Override
	@Transactional
	public ApiResponse saveProducto(Producto producto) {
		 productoRepository.save(producto);
		 return ApiResponse.builder().status(HttpStatus.OK).error(null).success(true).data("Producto Guardado")
					.build();
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean existProducto(Long id) {
		return productoRepository.existsById(id);
	}
	
	@Override
	@Transactional
	public ApiResponse update(Long id, Producto producto) {

		Producto productoExistente = productoRepository.findById(id)
				.orElseThrow(() -> new IdNotFoundException("Producto no encontrado con el ID: " + id));

		productoExistente.setNombre(producto.getNombre());
		productoExistente.setPrecio(producto.getPrecio());
		productoExistente.setCodigo(producto.getCodigo());
		productoExistente.setCantidad(producto.getCantidad());

		return ApiResponse.builder().status(HttpStatus.OK).error(null).success(true).data("Cliente Modificado").build();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Producto> findById(Long id) {
		return productoRepository.findById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ApiResponse findByIdApi(Long id) {
		Producto producto = productoRepository.findById(id)
				.orElseThrow(() -> new IdNotFoundException("Producto no encontrado con el ID: " + id));
		return ApiResponse.builder().status(HttpStatus.OK).error(null).success(true).data(producto).build();
	}
	
	@Override
	@Transactional(readOnly = true)
	public ApiResponse findAll() {
		List<Producto> producto = productoRepository.findAll();
		return ApiResponse.builder().status(HttpStatus.OK).error(null).success(true).data(producto).build();
	}
	
	
	@Override
	@Transactional
	public ApiResponse delete(Long id) {
		productoRepository.deleteById(id);
		return ApiResponse.builder().status(HttpStatus.OK).error(null).success(true).data("Produco eliminado").build();
	}
}
