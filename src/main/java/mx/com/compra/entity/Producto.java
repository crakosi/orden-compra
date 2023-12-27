package mx.com.compra.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUCTO")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "El nombre es requerido")
	@Column(length = 30)
	private String nombre;

	@Column(length = 20)
	private String codigo;

	@NotNull(message = "El precio no puede ser nulo")
	private Double precio;

	private Integer cantidad;


}
