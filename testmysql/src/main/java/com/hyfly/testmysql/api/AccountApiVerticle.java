package com.hyfly.testmysql.api;

import com.hyfly.testmysql.base.BaseRestApiVerticle;
import com.hyfly.testmysql.entity.Account;
import com.hyfly.testmysql.service.AccountService;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RequestBody;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class AccountApiVerticle extends BaseRestApiVerticle {

    private static final String SERVICE_NAME = "user-account-rest-api";
    private static final String API_ADD = "/user";
    private static final String API_RETRIEVE = "/user/:id";
    private static final String API_RETRIEVE_ALL = "/user";
    private static final String API_UPDATE = "/user/:id";
    private static final String API_DELETE = "/user/:id";

    private final AccountService accountService;

    public AccountApiVerticle(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        super.start();

        final Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.post(API_ADD).handler(this::apiAddUser);
        router.get(API_RETRIEVE).handler(this::apiRetrieveUser);
        router.get(API_RETRIEVE_ALL).handler(this::apiRetrieveAll);
        router.patch(API_UPDATE).handler(this::apiUpdateUser);
        router.delete(API_DELETE).handler(this::apiDeleteUser);

        String host = config().getString("user.account.http.address", "0.0.0.0");
        int port = config().getInteger("user.account.http.port", 8081);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(port, host, res -> {
                if (res.succeeded()) {
                    startPromise.complete();
                } else {
                    startPromise.fail(res.cause());
                }
            });
    }

    private void apiAddUser(RoutingContext context) {
        RequestBody body = context.body();
        Account account = body.asPojo(Account.class);
        accountService.addAccount(account, resultVoidHandler(context, 201));
    }

    private void apiRetrieveUser(RoutingContext routingContext) {
        JsonObject config = config();

        System.out.println(config);
    }

    private void apiRetrieveAll(RoutingContext routingContext) {
    }

    private void apiUpdateUser(RoutingContext routingContext) {
    }

    private void apiDeleteUser(RoutingContext routingContext) {
    }
}
