package mx.com.compra.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class SecurityConfig {

	@Value("${app.client.id}")
	private String clientId;
	@Value("${app.client.secret}")
	private String clientSecret;
	@Value("${app.client.scope-read}")
	private String scopeRead;
	@Value("${app.client.scope-write}")
	private String scopeWrite;
	@Value("${app.client-redirect-debugger}")
	private String redirectUri1;
	@Value("${app.client-redirect-spring-doc}")
	private String redirectUri2;

	private final UserDetailsService userDetailsService;
	

	public SecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	@Order(1)
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

		http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());

		http.exceptionHandling(e -> e.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(LOGIN_RESOURCE)));

		return http.build();
	}

	@Bean
	@Order(2)
	SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
        
        http.formLogin(withDefaults())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(RECRUSOS_PUBLICOS).permitAll()
                        .requestMatchers(RECURSOS_USUARIOS).authenticated()
                         .requestMatchers(RECURSOS_ADMINISTRADOR).hasAuthority(ROLE_ADMIN))
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions
                            .sameOrigin()
                        ))
                .oauth2ResourceServer(server -> server
                        .jwt());

  return http.build();
	}
	
	

	@Bean
	AuthenticationProvider authenticationProvider(BCryptPasswordEncoder encoder) {
		var authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(encoder);
		authenticationProvider.setUserDetailsService(userDetailsService);
		return authenticationProvider;
	}

	@Bean
	RegisteredClientRepository registeredClientRepository(BCryptPasswordEncoder encoder) {
		var registeredClient = RegisteredClient.withId(UUID.randomUUID().toString()).clientId(clientId)
				.clientSecret(encoder.encode(clientSecret)).scope(scopeRead).scope(scopeWrite).redirectUri(redirectUri1)
				.redirectUri(redirectUri2).clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE).build();
		return new InMemoryRegisteredClientRepository(registeredClient);
	}

	@Bean
	AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().build();
	}

	@Bean
	JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	@Bean
	JWKSource<SecurityContext> jwkSource() {
		var rsaKey = generateKeys();
		var jwkSet = new JWKSet(rsaKey);
		return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	TokenSettings tokenSettings() {
		return TokenSettings.builder().refreshTokenTimeToLive(Duration.ofHours(8)).build();
	}

	@Bean
	JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter() {
		var converter = new JwtGrantedAuthoritiesConverter();
		converter.setAuthorityPrefix("");
		return converter;
	}

	@Bean
	JwtAuthenticationConverter jwtAuthenticationConverter(
			JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter) {
		var converter = new JwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		return converter;
	}

//	@Bean
//	OAuth2TokenCustomizer<JwtEncodingContext> oAuth2TokenCustomizer() {
//		return context -> {
//			if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {
//				context.getClaims().claims(claim -> {
//					claim.putAll(Map.of("owner", APPLICACION, "date", LocalDateTime.now().toString()));
//				});
//			}
//		};
//	}

	private static KeyPair generateRSA() {
		KeyPair keyPair = null;
		try {
			var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
		return keyPair;
	}

	private static RSAKey generateKeys() {
		var keyPair = generateRSA();
		var publicKey = (RSAPublicKey) keyPair.getPublic();
		var privateKey = (RSAPrivateKey) keyPair.getPrivate();
		return new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()).build();
	}

	private static final String[] RECRUSOS_PUBLICOS = {  "/swagger-ui/**", "/.well-known/**, ", "/v3/api-docs/**", "/h2-console/**" };
	private static final String[] RECURSOS_USUARIOS = {  "/cliente/**",  "/ordenCompra/**", "/producto/**" };
	private static final String[] RECURSOS_ADMINISTRADOR = { "/usuario/**" };
	private static final String ROLE_ADMIN = "ADMIN";
	private static final String LOGIN_RESOURCE = "/login";
}
