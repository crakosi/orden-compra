package mx.com.compra.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductoDto implements Serializable {

	private static final long serialVersionUID = 1462403903508088246L;

	private Long id;
	
	private String nombre;
	
	private String codigo;
	
	private Double precio;

	private Integer cantidad;
	
}

