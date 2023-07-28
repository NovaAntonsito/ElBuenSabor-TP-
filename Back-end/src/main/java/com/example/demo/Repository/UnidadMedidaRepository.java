package com.example.demo.Repository;

import com.example.demo.Entitys.UnidadMedida;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnidadMedidaRepository extends BaseRepository<UnidadMedida, Long>{

    UnidadMedida findByID(Long id) throws Exception;

}
