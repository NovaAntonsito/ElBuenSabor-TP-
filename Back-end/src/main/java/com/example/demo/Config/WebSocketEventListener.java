package com.example.demo.Config;

import com.example.demo.Entitys.Enum.EstadoPedido;
import com.example.demo.Entitys.Pedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener extends TextWebSocketHandler {


    private static Set<WebSocketSession> sessions = new HashSet<>();

    public static void notifyEstadoPedidoChange(Pedido pedido) throws IOException {
        EstadoPedido estado = pedido.getEstado();
        for (WebSocketSession session : sessions) {
            session.sendMessage(new TextMessage(estado.name()));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }


}
