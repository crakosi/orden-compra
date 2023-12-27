package mx.com.compra.service;

import java.util.Optional;

import mx.com.compra.dto.ApiResponse;
import mx.com.compra.entity.Producto;

public interface ProductoService {

	Producto save(Producto producto);

	boolean existProducto(Long id);

	Optional<Producto> findById(Long id);

	ApiResponse findAll();

	ApiResponse delete(Long id);

	ApiResponse saveProducto(Producto producto);

	ApiResponse update(Long id, Producto producto);

	ApiResponse findByIdApi(Long id);

	
}
