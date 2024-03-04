package com.system.httpserver;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;

import java.util.Optional;
import java.util.ArrayList;

import com.system.data.Data;

class RoutesHendler implements HttpHandler {

    public class Route {
        public String path;
        public RequestMethod method;
        public ICallback callback;

        public Route(String path, RequestMethod method, ICallback callback) {
            this.path = path;
            this.method = method;
            this.callback = callback;
        }
    }

    private Data context = null;
    private ArrayList<Route> routeList;

    public RoutesHendler(Data context) {
        this.context = context;
        this.routeList = new ArrayList<Route>();
    }

    public Optional<Route> findRouteByPath(String path) {
        return routeList.stream()
                .filter(route -> route.path.equals(path))
                .findFirst();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Request request = new Request(exchange);

            System.out.println(request.path);
            Optional<Route> optionalRoute = this.findRouteByPath(request.path);

            if (optionalRoute.isPresent()) {
                Route route = optionalRoute.get();

                if (route.method == request.method) {

                    request.BodyDecoding();
                    request.ParameterDecoding();

                    Response response = route.callback.call(context, request);

                    if (response.isResponseType(ResponseType.JSON) || response.isResponseType(ResponseType.ERROR))
                        exchange.getResponseHeaders().set("Content-Type", "application/json");

                    String responseContent = response.getContent();
                    
                    exchange.sendResponseHeaders(response.getStatus(), responseContent.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(responseContent.getBytes());
                    os.close();
                } else
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed

            } else
                exchange.sendResponseHeaders(404, 0); // Not Found

        } catch (Exception e) {
            exchange.sendResponseHeaders(500, 0); // Internal Server Error
        } finally {
            exchange.close();
        }
    }

    public void addRoute(RequestMethod method, String path, ICallback fn) {
        this.routeList.add(new Route(path, method, fn));
    }

}