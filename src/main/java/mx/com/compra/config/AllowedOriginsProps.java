package mx.com.compra.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "config")
public class AllowedOriginsProps {

	private List<String> allowedorigins;

	public List<String> getAllowedorigins() {
		return allowedorigins;
	}

	public void setAllowedorigins(List<String> allowedorigins) {
		this.allowedorigins = allowedorigins;
	}
}
