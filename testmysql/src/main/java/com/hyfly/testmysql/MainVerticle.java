package com.hyfly.testmysql;

import com.hyfly.testmysql.api.AccountApiVerticle;
import com.hyfly.testmysql.service.AccountService;
import com.hyfly.testmysql.service.impl.JdbcAccountServiceImpl;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

public class MainVerticle extends AbstractVerticle {

    private AccountService accountService;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        super.start();

        ConfigRetrieverOptions options = new ConfigRetrieverOptions()
            .addStore(
                new ConfigStoreOptions()
                    .setType("file")
                    .setFormat("json")
                    .setConfig(new JsonObject().put("path", "conf/local.json"))
            );
        ConfigRetriever retriever = ConfigRetriever.create(vertx, options);

        retriever.getConfig(res -> {
            if (res.succeeded()) {
                JsonObject config = res.result();
                accountService = new JdbcAccountServiceImpl(vertx, config);

                DeploymentOptions deploymentOptions = new DeploymentOptions().setConfig(config);
                vertx.deployVerticle(new AccountApiVerticle(accountService), deploymentOptions,
                    res1 -> {
                        if (res1.succeeded()) {
                            startPromise.complete();
                        } else {
                            startPromise.fail(res1.cause());
                        }
                    });
            } else {
                startPromise.fail(res.cause());
            }
        });

//        accountService = new JdbcAccountServiceImpl(vertx, config());
//
//        vertx.deployVerticle(new AccountApiVerticle(accountService), res -> {
//            if (res.succeeded()) {
//                startPromise.complete();
//            } else {
//                startPromise.fail(res.cause());
//            }
//        });
    }
}
