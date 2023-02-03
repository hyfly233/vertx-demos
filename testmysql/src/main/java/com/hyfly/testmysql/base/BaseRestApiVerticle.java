package com.hyfly.testmysql.base;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class BaseRestApiVerticle extends AbstractVerticle {

    protected Handler<AsyncResult<Void>> resultVoidHandler(RoutingContext context, int status) {
        return ar -> {
            if (ar.succeeded()) {
                context.response()
                    .setStatusCode(status == 0 ? 200 : status)
                    .putHeader("content-type", "application/json")
                    .end();
            } else {
                internalError(context, ar.cause());
                ar.cause().printStackTrace();
            }
        };
    }

    protected void internalError(RoutingContext context, Throwable ex) {
        context.response().setStatusCode(500)
            .putHeader("content-type", "application/json")
            .end(new JsonObject().put("error", ex.getMessage()).encodePrettily());
    }
}
