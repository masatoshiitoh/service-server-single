package com.example.serviceserver.single;

import io.vertx.config.ConfigRetriever;
import io.vertx.core.*;
import io.vertx.core.http.impl.headers.HeadersMultiMap;
import java.util.ArrayList;
import java.util.List;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) {
    ConfigRetriever retriever = ConfigRetriever.create(vertx);
    vertx.eventBus().registerDefaultCodec(MultiMap.class, new MultiMapToJsonCodec());
    vertx.eventBus().registerDefaultCodec(HeadersMultiMap.class, new HeadersMultiMapToJsonCodec());
    retriever.getConfig().onComplete(json -> {
      if (json.succeeded()) {
        vertx.eventBus().publish("config", json.result());

        Future<String> v1 = vertx.deployVerticle(new CommandEvaluatorVerticle(),
            new DeploymentOptions().setConfig(json.result()));
        Future<String> v2 = vertx.deployVerticle(new DispatcherVerticle(),
            new DeploymentOptions().setConfig(json.result()));
        Future<String> v3 = vertx.deployVerticle(new DummyClientVerticle(),
            new DeploymentOptions().setConfig(json.result()));
        Future<String> v4 = vertx.deployVerticle(new EkiModokiCommandProcessorVerticle(),
            new DeploymentOptions().setConfig(json.result()));
        Future<String> v5 = vertx.deployVerticle(new EventPublisherVerticle(),
            new DeploymentOptions().setConfig(json.result()));
        Future<String> v6 = vertx.deployVerticle(new EventToDbTransferVerticle(),
            new DeploymentOptions().setConfig(json.result()));
        Future<String> v7 = vertx.deployVerticle(new QueryApiVerticle(),
            new DeploymentOptions().setConfig(json.result()));
        List<Future> futures = List.of(v1, v2, v3, v4, v5, v6, v7);

        CompositeFuture.all(futures).onComplete(ar -> {
          if (ar.succeeded()) {
            startPromise.complete();
          } else {
            startPromise.fail(ar.cause());
          }
        });
      } else {
        startPromise.fail(json.cause());
      }
    });

  }
}
