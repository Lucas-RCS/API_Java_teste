package com.system.routes;

import com.system.httpserver.AppProvider;
import com.system.httpserver.HttpStatusCode;
import com.system.httpserver.Response;
import com.system.httpserver.Request;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.system.data.Data;
import static com.system.httpserver.RequestMethod.*;

public class RouteMain {
    private ObjectMapper mapper = new ObjectMapper();

    public RouteMain(AppProvider App) {
        App.addRoute(GET, "/list", (Data context, Request request) -> {
            try {
                return Response.json(HttpStatusCode.SUCCESS, mapper.valueToTree(context.teste));
            } catch (Exception e) {
                e.printStackTrace();
                return Response.ERROR(HttpStatusCode.SUCCESS, "{}");
            } 
        });

        App.addRoute(POST, "/add", (Data context, Request request) -> {
            try {
                context.teste.add(request.body.get("valor").asText());

                return Response.json(HttpStatusCode.CREATED);
            } catch (Exception e) {
                e.printStackTrace();
                return Response.ERROR(HttpStatusCode.INTERNAL_SERVER_ERROR, "{}");
            }
        });

        App.addRoute(DELETE, "/remove", (Data icontext, Request Request) -> {
            ObjectMapper teste = new ObjectMapper();

            JsonNode teste2;
            try {
                teste2 = teste.readTree("{\"teste\":\"ola\"}");

                return Response.json(HttpStatusCode.SUCCESS, teste2);

            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            return Response.ERROR(HttpStatusCode.SUCCESS, "{}");
        });
    }
    
}