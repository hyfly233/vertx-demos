package common;

import io.vertx.core.AbstractVerticle;
import io.vertx.servicediscovery.ServiceDiscovery;

public class CommonVerticle extends AbstractVerticle {

    protected ServiceDiscovery discovery;

    @Override
    public void start() throws Exception {
        System.out.println("CommonVerticle start");

        discovery = ServiceDiscovery.create(vertx);
    }

    @Override
    public void stop() throws Exception {
        System.out.println("CommonVerticle stop");
        if (discovery != null) {
            discovery.close();
        }
    }
}
