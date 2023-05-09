package com.example.demo.Services;

import com.example.demo.Entitys.Rol;
import com.example.demo.Entitys.Usuario;
import com.example.demo.Repository.RolRepository;
import com.example.demo.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserServiceInterface, UserDetailsService {

    private final UsuarioRepository userRepository;
    private final RolRepository rolRepository;


    @Override
    public Usuario crearNuevoUsuario(Usuario user) throws Exception {
        //TODO Finish this
        Usuario newUser = new Usuario();
        newUser.setNombre(user.getNombre());
        newUser.setApellido(user.getApellido());
        return newUser;
    }

    @Override
    public Rol crearNuevoRol(Rol rol) throws Exception {
        if (!rolRepository.existsByNombre(rol.getNombreRol())) {
            Rol newRol = new Rol();
            newRol.setNombreRol(rol.getNombreRol());
            return newRol;
        }else{
            throw new Exception("Ya existe el rol en la base de datos");
        }
    }

    @Override
    public void addRoltoUser(String username, String rolName) throws Exception {

    }

    @Override
    public Page<Usuario> verTodosUsuarios(Pageable page) throws Exception {
        log.info("Encontrando todos los usuarios por pagina");
        return userRepository.findAll(page);
    }

    @Override
    public void borrarUsuario(String username) throws UsernameNotFoundException  {
        Usuario userFound = userRepository.findByNombre(username);
        if (userFound == null){
            throw new UsernameNotFoundException("El usuario no fue encontrado en la base de datos");
        }
        userRepository.delete(userFound);
        log.info("El usuario fue borrado de la base de datos");
    }

    @Override
    public Usuario actualizarUsuario(Long ID, Usuario newUser) throws UsernameNotFoundException  {
        Usuario userFound = userRepository.findByID(ID);
        if (userFound == null){
           throw new UsernameNotFoundException("El usuario no fue encontrado en la base de datos");
        }
        userFound = newUser;
        userRepository.save(userFound);
        return newUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

}
