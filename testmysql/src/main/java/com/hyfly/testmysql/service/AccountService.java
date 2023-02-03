package com.hyfly.testmysql.service;

import com.hyfly.testmysql.entity.Account;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

public interface AccountService {

    AccountService initializePersistence(Handler<AsyncResult<Void>> resultHandler);

    AccountService addAccount(Account account, Handler<AsyncResult<Void>> resultVoidHandler);
}
