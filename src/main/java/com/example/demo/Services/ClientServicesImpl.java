package com.example.demo.Services;

import com.example.demo.Entitys.Clients;
import com.example.demo.Repository.BaseRepository;
import com.example.demo.Repository.ClientsRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServicesImpl extends BaseServiceImpl<Clients,Long> implements ClientServices {
    private final ClientsRepository clientsRepository;

    public ClientServicesImpl(BaseRepository<Clients, Long> baseRepository, ClientsRepository clientsRepository) {
        super(baseRepository);
        this.clientsRepository = clientsRepository;
    }

    @Override
    public List<Clients> searchClientes(String name) throws Exception{
        try {
            return clientsRepository.searchClientes(name);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
