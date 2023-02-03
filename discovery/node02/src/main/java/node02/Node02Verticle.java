package node02;

import common.CommonVerticle;

public class Node02Verticle extends CommonVerticle {

    @Override
    public void start() throws Exception {
        super.start();

        System.out.println("Node02Verticle start");

        discovery.getRecords(r -> "http-endpoint".equals(r.getType()), ar -> {
            if (ar.succeeded()) {
                System.out.println("ar.result().size(): " + ar.result().size());
                ar.result().forEach(record -> {
                    System.out.println("record: " + record);
                });
            } else {
                System.out.println("ar.cause(): " + ar.cause());
            }
        });
    }
}
