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
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import mx.com.compra.dto.ApiResponse;
import mx.com.compra.entity.Producto;
import mx.com.compra.service.ProductoService;

@RestController
@RequestMapping("producto")
@AllArgsConstructor
@Tag(name="Métodos de los productos")
public class ProductoController {

	private final ProductoService productoService;
	
	@Operation(summary = "Guarda los datos del producto")
	@PostMapping("/save")
	public ResponseEntity<ApiResponse> save(@RequestBody Producto producto) {
		ApiResponse response = productoService.saveProducto(producto);
		return ResponseEntity.status(response.getStatus()).body(response);
	}
	
	@Operation(summary = "Actualiza los datos del producto")
	@PutMapping("/update/{id}")
	 public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody Producto producto){
		ApiResponse response = productoService.update(id, producto);
		return ResponseEntity.status(response.getStatus()).body(response);
	}
	
	@Operation(summary = "Busca todos los productos")
	@GetMapping("/findAll")
	public ResponseEntity<ApiResponse> findAll(){
		ApiResponse response = productoService.findAll();
		return ResponseEntity.status(response.getStatus()).body(response);
	}
	
	@Operation(summary = "Busca un producto por ID")
	@GetMapping("/findById/{id}")
	public ResponseEntity<ApiResponse> findById(@PathVariable Long id){
		ApiResponse response = productoService.findByIdApi(id);
		return ResponseEntity.status(response.getStatus()).body(response);
	}
	
	@Operation(summary = "Elimina un producto por ID")
	@DeleteMapping(path = "/delete/{id}")
	 public ResponseEntity<ApiResponse> delete(@PathVariable Long id){
		 ApiResponse response = productoService.delete(id);
		 return ResponseEntity.status(response.getStatus()).body(response);
	 }
}
