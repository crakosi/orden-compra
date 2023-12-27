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
import mx.com.compra.entity.Cliente;
import mx.com.compra.service.ClienteService;

@RestController
@RequestMapping("cliente")
@AllArgsConstructor
@Tag(name="Métodos del cliente")
public class ClienteController {

	private final ClienteService clienteService;
	
	@Operation(summary = "Guarda los datos del cliente")
	@PostMapping("/save")
	public  ResponseEntity<ApiResponse> save(@RequestBody Cliente cliente){
		ApiResponse response = clienteService.save(cliente);
		return ResponseEntity.status(response.getStatus()).body(response);
	}
	
	@Operation(summary = "Actualiza los datos del cliente")
	@PutMapping("/update/{id}")
	 public ResponseEntity<ApiResponse> updateCliente(@PathVariable Long id, @RequestBody Cliente cliente){
		ApiResponse response = clienteService.updateCliente(id, cliente);
		return ResponseEntity.status(response.getStatus()).body(response);
	}
	
	@Operation(summary = "Busca todos los clientes")
	@GetMapping("/findAll")
	public ResponseEntity<ApiResponse> findAll(){
		ApiResponse response = clienteService.findAll();
		return ResponseEntity.status(response.getStatus()).body(response);
	}
	
	@Operation(summary = "Busca un cliente por id")
	@GetMapping("/findById/{id}")
	public ResponseEntity<ApiResponse> findById(@PathVariable Long id){
		ApiResponse response = clienteService.findByIdApi(id);
		return ResponseEntity.status(response.getStatus()).body(response);
	}
	
	@Operation(summary = "Elimina un cliente por id")
	 @DeleteMapping(path = "/delete/{id}")
	 public ResponseEntity<ApiResponse> delete(@PathVariable Long id){
		 ApiResponse response = clienteService.delete(id);
		 return ResponseEntity.status(response.getStatus()).body(response);
	 }
	
}
