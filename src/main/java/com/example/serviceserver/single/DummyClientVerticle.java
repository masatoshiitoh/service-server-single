package com.example.serviceserver.single;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;

public class DummyClientVerticle extends AbstractVerticle {
  // クライアントとしての振る舞いをするverticleです
  // webサーバーverticleです

  boolean isDummyClientRunning = false;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println("DummyClientVerticle started!");
    // ダミクラ実体を起動
    dummyClientRunner();

    // 管理用Webを起動
    Router router = Router.router(vertx);
    // ダミクラ起動
    router.get("/start").handler(routingContext -> {
      isDummyClientRunning = true;
      routingContext.response().putHeader("content-type", "text/plain").end("DummyClient started!");
    });

    // ダミクラ停止
    router.get("/stop").handler(routingContext -> {
      isDummyClientRunning = false;
      routingContext.response().putHeader("content-type", "text/plain").end("DummyClient stopped!");
    });

    // ダミクラのログを取得
    router.get("/log").handler(routingContext -> {
      routingContext.response().putHeader("content-type", "application/json").end((String) "{}");
    });

    // health check
    router.get("/health").handler(routingContext -> {
      routingContext.response().putHeader("content-type", "text/plain").end((String) "OK");
    });

    vertx.createHttpServer().requestHandler(router).listen(8081, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8081");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }

  @Override
  public void stop(Promise<Void> stopPromise) throws Exception {
    System.out.println("DummyClientVerticle stopped!");
    super.stop(stopPromise);
  }

  public void dummyClientRunner() throws Exception {
    WebClient webClient = WebClient.create(vertx);

    vertx.eventBus().consumer("dummyclient", message -> {
      System.out.println("Received dummyclient: " + message.body());

      vertx.setTimer((int) (Math.random() * 1000) + 1, id -> {

        webClient.get(8080, "localhost", "/command").send().onSuccess(response -> {
          message.reply("Received response with status code" + response.statusCode());
        }).onFailure(err -> {
          message.reply("Something went wrong " + err.getMessage());
        });

      });
    });

    System.out.println("DummyClientVerticle started!");
    vertx.setPeriodic(1000, id -> {
      if (!isDummyClientRunning) {
        return;
      }
      vertx.eventBus().request("dummyclient", "send", reply -> {
        if (reply.succeeded()) {
          System.out.println("mesage sent!: " + reply.result().body());
        } else {
          System.out.println("Failed to send message!: " + reply.cause());
        }
      });
    });
  }
}

