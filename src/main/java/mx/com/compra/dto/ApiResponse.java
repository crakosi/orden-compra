package mx.com.compra.dto;


import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ApiResponse {

	private HttpStatus status;
	private boolean success;
	private Object data;
	private Object error;
}
