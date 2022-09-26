package web.proyecto.oracle.models.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.proyecto.oracle.models.dao.IUsuarioDao;
import web.proyecto.oracle.models.entity.Rol;
import web.proyecto.oracle.models.entity.Usuario;

@Service
public class JpaUsuariosService implements UserDetailsService {
    
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		 Usuario usuario = usuarioDao.findByCorreo(username);
		 
		 List<GrantedAuthority> authorities = new ArrayList<>();
		 
		  for(Rol rol : usuario.getRoles()) {
			  
			  authorities.add(new SimpleGrantedAuthority(rol.getAuthoritie()));
			  
		  }
		
		return new User(usuario.getUsername(),usuario.getPassword(), usuario.getEnabled() , true , true ,true, authorities);
	}

}
