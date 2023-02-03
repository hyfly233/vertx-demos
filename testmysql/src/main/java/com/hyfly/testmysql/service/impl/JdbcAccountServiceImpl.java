package com.hyfly.testmysql.service.impl;

import com.hyfly.testmysql.entity.Account;
import com.hyfly.testmysql.service.AccountService;
import com.hyfly.testmysql.wrapper.JdbcRepositoryWrapper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class JdbcAccountServiceImpl extends JdbcRepositoryWrapper implements AccountService {

    private static final String CREATE_STATEMENT = "CREATE TABLE IF NOT EXISTS `user_account` (\n" +
        "  `id` varchar(30) NOT NULL,\n" +
        "  `username` varchar(20) NOT NULL,\n" +
        "  `phone` varchar(20) NOT NULL,\n" +
        "  `email` varchar(45) NOT NULL,\n" +
        "  `birthDate` bigint(20) NOT NULL,\n" +
        "  PRIMARY KEY (`id`),\n" +
        "  UNIQUE KEY `username_UNIQUE` (`username`) )";

    private static final String INSERT_STATEMENT = "INSERT INTO user_account (id, username, phone, email, birthDate) VALUES (?, ?, ?, ?, ?)";

    public JdbcAccountServiceImpl(Vertx vertx, JsonObject config) {
        super(vertx, config);
    }

    @Override
    public AccountService initializePersistence(Handler<AsyncResult<Void>> resultHandler) {
        client.getConnection(connHandler(resultHandler, connection -> {
            connection.execute(CREATE_STATEMENT, r -> {
                resultHandler.handle(r);
                connection.close();
            });
        }));
        return this;
    }

    @Override
    public AccountService addAccount(Account account, Handler<AsyncResult<Void>> resultHandler) {
        JsonArray params = new JsonArray().add(account.getId())
            .add(account.getUsername())
            .add(account.getPhone())
            .add(account.getEmail())
            .add(account.getBirthDate());
        this.executeNoResult(params, INSERT_STATEMENT, resultHandler);
        return this;
    }
}
