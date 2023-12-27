package mx.com.compra.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import mx.com.compra.entity.Rol;
import mx.com.compra.entity.Usuario;
import mx.com.compra.repository.UsuarioRepository;
import mx.com.compra.service.UsuarioService;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

	private final UsuarioRepository usuarioRepository;

	@Override
	public List<Usuario> findAll() {

		return usuarioRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var usuario = this.usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
		return mapUserToUserDetails(usuario);
	}

	private static UserDetails mapUserToUserDetails(Usuario usuario) {
		Set<GrantedAuthority> authorities = convertToAuthorities(usuario.getRol());
		return new User(usuario.getUsername(), usuario.getPassword(), true, true, true, true, authorities);
	}

	public static Set<GrantedAuthority> convertToAuthorities(Rol role) {
		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority(role.getNombre()));
		return authorities;
	}
}
