package com.example.demo.Repository;

import com.example.demo.Entitys.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario,Long>{
    Usuario findByNombre (String username);

    Usuario findByID (Long ID);
}
