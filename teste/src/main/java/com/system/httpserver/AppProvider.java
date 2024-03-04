package com.system.httpserver;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

import com.system.data.Data;



public class AppProvider {

    private HttpServer server;
    private RoutesHendler RoutesHendlerInstance;

    public AppProvider(int port, Data context) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);

        this.RoutesHendlerInstance = new RoutesHendler(context);

        server.setExecutor(null);
    }

    public void start() {
        server.start();
        System.out.println("Servidor iniciado");
    }

    public void stopAfterDelay(long delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                server.stop(0);
                System.out.println("Servidor parado apos " + delay + " milissegundos");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void addRoute(RequestMethod method, String path, ICallback fn) {

        System.out.println("Set path " + path);
        this.server.createContext(path, this.RoutesHendlerInstance);

        this.RoutesHendlerInstance.addRoute(method, path, fn);
    }
}
