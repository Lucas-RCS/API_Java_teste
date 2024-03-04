package com.system.httpserver;

import com.system.data.Data;

@FunctionalInterface
public interface ICallback {
    Response call(Data context, Request resquest);
}