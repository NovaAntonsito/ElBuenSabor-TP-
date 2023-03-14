package com.example.demo.Services;

import com.example.demo.Entitys.Cliente;

import java.util.List;


public interface ClientServices extends BaseServices<Cliente,Long>{
    List<Cliente> searchClientes(String name) throws Exception;
}
