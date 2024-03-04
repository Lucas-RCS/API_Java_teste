package com.system;

import java.io.IOException;

import com.system.data.Data;
import com.system.httpserver.AppProvider;
import com.system.routes.RouteMain;


public class Main {

    public static void main(String[] args) throws IOException {
        Data context = new Data();

        context.teste.add("item1");
        context.teste.add("item2");
        context.teste.add("item3");

        AppProvider App = new AppProvider(8080, context);

        new RouteMain(App);
        
        App.start();
        // provider.stopAfterDelay(30000); // 30 segundos
    }
}