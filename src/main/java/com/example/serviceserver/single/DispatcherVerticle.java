package com.example.serviceserver.single;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;

public class DispatcherVerticle extends AbstractVerticle {
  // HTTPサーバーを起動する
  // APIからのリクエストを受け付けて、APIごとのイベントバスに送信する
  // ただのディスパッチャーですが、いずれもバーティクルからの返信を待つことが必要です
  // あと、イベントパブリッシャーからの「更新情報』待ちの部分は、ロングポーリングになります（リクエスト時点から、イベントバス待ちに入る）
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Router router = Router.router(vertx);

    // コマンドを受け付ける
    router.get("/command").handler(routingContext -> {
      MultiMap params = routingContext.request().params();
      vertx.eventBus().request("command", params, reply -> {
        if (reply.succeeded()) {
          routingContext.response().putHeader("content-type", "text/plain")
              .end((String) reply.result().body());
        } else {
          routingContext.response().putHeader("content-type", "text/plain")
              .end("Failed to send command!");
        }
      });
    });

    // クエリを受け付ける
    router.get("/query").handler(routingContext -> {
      MultiMap params = routingContext.request().params();
      vertx.eventBus().request("query", params, reply -> {
        if (reply.succeeded()) {
          routingContext.response().putHeader("content-type", "text/plain")
              .end((String) reply.result().body());
        } else {
          routingContext.response().putHeader("content-type", "text/plain")
              .end("Failed to send query!");
        }
      });
    });

    // event publisherからの更新情報を待つ
    router.get("/event").handler(routingContext -> {
      routingContext.response().putHeader("content-type", "application/json").end((String) "{}");
    });

    // health check
    router.get("/health").handler(routingContext -> {
      routingContext.response().putHeader("content-type", "text/plain").end((String) "OK");
    });

    vertx.createHttpServer().requestHandler(router).listen(8080, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8080");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }

}
