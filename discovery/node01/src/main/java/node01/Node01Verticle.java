package node01;

import common.CommonVerticle;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.types.HttpEndpoint;

public class Node01Verticle extends CommonVerticle {

    @Override
    public void start() throws Exception {
        super.start();

        System.out.println("Node01Verticle start");

        vertx.createHttpServer()
                .requestHandler(req -> {
                    System.out.println("28080 handler");
                    System.out.println(req);
                    req.response().end("hello world 28080");
                })
                .listen(28080);

        Record record = HttpEndpoint.createRecord("some-rest-api", "localhost", 28080, "/api");

        discovery.publish(record, ar -> {
            if (ar.succeeded()) {
                // 发布成功
                Record publishedRecord = ar.result();

                System.out.println("publishedRecord: " + publishedRecord);
            } else {
                // 发布失败
            }
        });

        discovery.getRecords(r -> "http-endpoint".equals(r.getType()), ar -> {
            if (ar.succeeded()) {
                System.out.println("ar.result().size(): " + ar.result().size());
                ar.result().forEach(record1 -> {
                    System.out.println("record: " + record1);
                });
            } else {
                System.out.println("ar.cause(): " + ar.cause());
            }
        });
    }
}

