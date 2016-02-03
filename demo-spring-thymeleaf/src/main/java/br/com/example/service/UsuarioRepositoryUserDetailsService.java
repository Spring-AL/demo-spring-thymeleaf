package br.com.example.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.com.example.domain.Usuario;
import br.com.example.repository.UsuarioRepository;

@Service
public class UsuarioRepositoryUserDetailsService implements UserDetailsService  {
	
	@Autowired private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(username);
		if (usuario == null) {
			throw new UsernameNotFoundException("Could not find user " + username);
		}
		return new UsuarioRepositoryUserDetails(usuario);
	}

	private static class UsuarioRepositoryUserDetails extends Usuario implements UserDetails {

		private static final long serialVersionUID = 7472469536266786971L;
		
		private UsuarioRepositoryUserDetails(Usuario usuario) {
			super();
        }

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return AuthorityUtils.createAuthorityList("ROLE_USER");
		}

		@Override
		public String getPassword() {
			return this.getSenha();
		}

		@Override
		public String getUsername() {
			return getEmail();
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}
		
	}
}
