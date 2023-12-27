package mx.com.compra.service;

import mx.com.compra.dto.ApiResponse;
import mx.com.compra.entity.OrdenCompra;

public interface OrdenCompraService {

	ApiResponse save(OrdenCompra ordenCompra);

	ApiResponse findAll();

	ApiResponse findById(Long id);

	ApiResponse delete(Long id);

	ApiResponse updateOrden(Long id, OrdenCompra ordenCompra);

}
