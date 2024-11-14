package com.example.starter;

import io.vertx.core.Vertx;

public class RepCase {
 
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(RepCaseMain.class.getName());
    }
}
