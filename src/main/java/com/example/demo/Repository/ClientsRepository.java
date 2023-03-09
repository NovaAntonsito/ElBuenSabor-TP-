package com.example.demo.Repository;

import com.example.demo.Entitys.Clients;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientsRepository extends BaseRepository<Clients,Long> {
    @Query(value = "SELECT * FROM clientes c WHERE :name IS NULL OR c.nombre LIKE %:name%",
            nativeQuery = true)
    List<Clients> searchClientes (@Param("name")String name);
}
