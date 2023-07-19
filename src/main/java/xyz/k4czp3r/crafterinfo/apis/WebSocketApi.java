package xyz.k4czp3r.crafterinfo.apis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import xyz.k4czp3r.crafterinfo.Logger;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WebSocketApi extends WebSocketServer {
    private static WebSocketApi instance;
    public static WebSocketApi getInstance(int port) throws UnknownHostException {
        if(instance == null) {
            instance = new WebSocketApi(port);
        }
        return instance;
    }

    private final Set<WebSocket> conns = Collections.synchronizedSet(new HashSet<>());

    private WebSocketApi(int port)  {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conns.add(conn);
        Logger.getInstance(null).info("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        conns.remove(conn);
        Logger.getInstance(null).info("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        Logger.getInstance(null).info("Received message from " + conn.getRemoteSocketAddress().getAddress().getHostAddress() + ": " + message);

    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        Logger.getInstance(null).error("Error from " + conn.getRemoteSocketAddress().getAddress().getHostAddress() + ": " + ex.getMessage());
    }

    @Override
    public void onStart() {
        Logger.getInstance(null).info("WebSocket server started!");
    }

    public void broadcast(Object message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(message);
        for(WebSocket conn : conns) {
            conn.send(jsonMessage);
        }
    }
}
