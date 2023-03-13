package com.example.demo.Services;

import com.example.demo.Entitys.Client;

import java.util.List;


public interface ClientServices extends BaseServices<Client,Long>{
    List<Client> searchClientes(String name) throws Exception;
}
