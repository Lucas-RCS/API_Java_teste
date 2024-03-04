package com.system.httpserver;

import com.fasterxml.jackson.databind.JsonNode;

public class Response {
    private String responseContent = "";
    private ResponseType responseType;
    private HttpStatusCode statusCode;

    public Response(ResponseType responseType, HttpStatusCode code, String Content) {
        this.responseType = responseType;
        this.statusCode = code;
        this.responseContent = Content;
    }

    public boolean isStatus(HttpStatusCode status) {
        return statusCode == status;
    }

    public boolean isResponseType(ResponseType type) {
        return responseType == type;
    }

    public int getStatus() {
        return statusCode.code;
    }

    public static Response json(HttpStatusCode code, JsonNode json) {
        return new Response(ResponseType.JSON, code, json.toString());
    }

    public static Response json(HttpStatusCode code) {
        return new Response(ResponseType.JSON, code, "{}");
    }

    public static Response send(HttpStatusCode code, String text) {
        return new Response(ResponseType.Text, code, text);
    }

    public static Response ERROR(HttpStatusCode code, String message) {
        return new Response(ResponseType.ERROR, code, message);
    }

    public static Response ERROR(HttpStatusCode code, JsonNode message) {
        return Response.ERROR(code, message.toString());
    }

    public String getContent() {
        if (responseType == ResponseType.JSON)
            return "{\"error\": false, \"status\":" + statusCode.code + ", \"content\": " + responseContent + "}";
        else if (responseType == ResponseType.Text)
            return responseContent;
        else
            return "{\"error\": true, \"status\":" + statusCode.code + ", \"content\": \"" + responseContent + "\"}";
    }
}