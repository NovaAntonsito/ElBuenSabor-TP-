package com.example.demo.Services;

import com.example.demo.Entitys.Clients;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ClientServices extends BaseServices<Clients,Long>{
    List<Clients> searchClientes(String name) throws Exception;
}
