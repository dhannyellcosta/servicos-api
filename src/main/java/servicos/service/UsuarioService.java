package servicos.service;

import servicos.exception.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import servicos.model.entity.Usuario;
import servicos.model.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

	private final UsuarioRepository repository;

	private final PasswordEncoder passwordEncoder;
	
	public Usuario salvar(Usuario usuario) {
		
		boolean exists = repository.existsByUsername(usuario.getUsername());
		
		if(exists) {
			throw new RegraDeNegocioException("Usuário já cadastrado para o login " + usuario.getUsername());
		}
		String senhaCriptografada = passwordEncoder.encode(usuario.getPassword());
		usuario.setPassword(senhaCriptografada);
		return repository.save(usuario);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = repository
								.findByUsername(username)
								.orElseThrow(() -> new UsernameNotFoundException("Login não encontrado"));
		
		return User.builder()
					.username(usuario.getUsername())
					.password(usuario.getPassword())
					.roles("USER")
					.build();
		
	}
	
}
