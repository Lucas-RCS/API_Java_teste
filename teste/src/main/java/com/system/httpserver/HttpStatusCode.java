package com.system.httpserver;

public enum HttpStatusCode {
    SUCCESS(200),
    CREATED(201),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    INTERNAL_SERVER_ERROR(500);

    public final int code;

    private HttpStatusCode(int code) {
        this.code = code;
    }
}