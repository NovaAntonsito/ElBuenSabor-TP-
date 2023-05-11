package com.example.demo.Repository;

import com.example.demo.Entitys.Insumo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InsumoRepository extends BaseRepository<Insumo,Long>{
    Insumo findByID (Long ID);

    @Query(value ="Select * from insumo where alta = 0" ,nativeQuery = true)
    Page<Insumo> getAllInsumosInAlta (Pageable page);
}
