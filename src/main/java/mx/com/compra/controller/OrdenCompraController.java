package mx.com.compra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import mx.com.compra.dto.ApiResponse;
import mx.com.compra.entity.OrdenCompra;
import mx.com.compra.service.OrdenCompraService;
import mx.com.compra.util.exception.IdNotFoundException;

@RestController
@RequestMapping("ordenCompra")
@AllArgsConstructor
@Tag(name="Métodos de la Orden de Compra")
public class OrdenCompraController {

	private final OrdenCompraService ordenCompraService;

	@Operation(summary = "Guarda la orden de compra junto con el cliente y los productos")
	@PostMapping("/save")
	public ResponseEntity<ApiResponse> save(@RequestBody OrdenCompra ordenCompra) {
		ApiResponse response = ordenCompraService.save(ordenCompra);
		return ResponseEntity.status(response.getStatus()).body(response);
	}
	
	@io.swagger.v3.oas.annotations.responses.ApiResponse(
			responseCode = "400", description = "Cuando el request es inválido", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = IdNotFoundException.class))
			})
	
	@Operation(summary = "Actualiza la orden de compra, cliente y producto")
	@PutMapping("/update/{id}")
	 public ResponseEntity<ApiResponse> updateOrden(@PathVariable Long id, @RequestBody OrdenCompra ordenCompra){
		ApiResponse response = ordenCompraService.updateOrden(id, ordenCompra);
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@Operation(summary = "Busca todas las ordenes de compra")
	@GetMapping("/findAll")
	public ResponseEntity<ApiResponse> findAll() {
		ApiResponse response = ordenCompraService.findAll();
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@Operation(summary = "Busca una orden de compra por ID")
	@GetMapping("findById/{id}")
	public ResponseEntity<ApiResponse> findById(@PathVariable Long id) {
		ApiResponse response = ordenCompraService.findById(id);
		return ResponseEntity.status(response.getStatus()).body(response);
	}
	
	@Operation(summary = "Elimina una orden de compra por id")
	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
		ApiResponse response = ordenCompraService.delete(id);
		return ResponseEntity.status(response.getStatus()).body(response);
	}
}
