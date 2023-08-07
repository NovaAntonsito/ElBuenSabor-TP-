package com.example.demo.Services;

import com.example.demo.Entitys.Pedido;
import com.example.demo.Repository.PedidoRepository;
import com.example.demo.Services.Interfaces.PedidoServiceInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PedidoService implements PedidoServiceInterface {

    private final PedidoRepository pedidoRepository;

    @Override
    public Pedido savePedido(Pedido pedido) throws Exception {
        return pedidoRepository.save(pedido);
    }

    @Override
    public Page<Pedido> getAllPedidos(Pageable page) throws Exception {
        return pedidoRepository.findAll(page);
    }

    @Override
    public Map<String, Integer> cantidadPedidosporMes() throws Exception {
        List<Integer> cantidadDePedidos = pedidoRepository.obtenerPedidosPorMes();
        if(cantidadDePedidos.size() == 0) throw new Exception("No existe ningun pedido");
        Map<String, Integer> resultado = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM");
        LocalDate actualDate = LocalDate.now();
        for(int i = 0 ; i < 3; i++) {
            String nombreMes = actualDate.minusMonths(i).format(formatter);
            resultado.put(nombreMes, cantidadDePedidos.get(i));
        }
        return resultado;
    }

    @Override
    public Integer cuentaPedidos(Date fecha1, Date fecha2) throws Exception {
        return pedidoRepository.cuentaDePedidos(fecha1, fecha2);
    }
}
