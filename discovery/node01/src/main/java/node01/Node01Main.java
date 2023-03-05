package node01;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class Node01Main {
    public static void main(String[] args) {
        VertxOptions options = new VertxOptions();
        Vertx.clusteredVertx(options, ar -> {
            if (ar.succeeded()) {
                Vertx vertx = ar.result();
                vertx.deployVerticle(new Node01Verticle());
            } else {
                System.out.println("Failed: " + ar.cause());
            }
        });
    }
}
