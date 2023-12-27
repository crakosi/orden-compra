package mx.com.compra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import mx.com.compra.dto.ApiResponse;
import mx.com.compra.entity.Producto;
import mx.com.compra.repository.ProductoRepository;
import mx.com.compra.service.impl.ProductoServiceImpl;

@SpringBootTest
class ProductoServiceImplTest {

	@Mock
	private ProductoRepository productoRepositoryMock;

	@InjectMocks
	private ProductoServiceImpl productoService;

	@Test
	void saveProducto() {
		Producto producto = Producto.builder()
				.cantidad(5)
				.codigo("001")
				.id(1L)
				.precio(500.0)
				.nombre("Lecehce")
				.build();

		when(productoRepositoryMock.save(producto)).thenReturn(producto);

		ApiResponse response = productoService.saveProducto(producto);

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
		
	}
}
