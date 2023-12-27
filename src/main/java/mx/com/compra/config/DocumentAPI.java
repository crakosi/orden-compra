package mx.com.compra.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Orden Compra",
                version = "1.0",
                description = "Documentacion para orden de compra")
)
public class DocumentAPI {

}
