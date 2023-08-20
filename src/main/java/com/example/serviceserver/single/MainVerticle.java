package com.example.serviceserver.single;

import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.http.impl.headers.HeadersMultiMap;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) {
    ConfigRetriever retriever = ConfigRetriever.create(vertx);
    vertx.eventBus().registerDefaultCodec(MultiMap.class, new MultiMapToJsonCodec());
    vertx.eventBus().registerDefaultCodec(HeadersMultiMap.class, new HeadersMultiMapToJsonCodec());
    retriever.getConfig().onComplete(json -> {
      if (json.succeeded()) {
        vertx.eventBus().publish("config", json.result());

        vertx.deployVerticle(new DispatcherVerticle(),
            new DeploymentOptions().setConfig(json.result()), res1 -> {
              if (res1.succeeded()) {
                vertx.deployVerticle(new EkiModokiCommandProcessorVerticle(),
                    new DeploymentOptions().setConfig(json.result()), res2 -> {
                      if (res2.succeeded()) {
                        vertx.deployVerticle(new CommandEvaluatorVerticle(),
                            new DeploymentOptions().setConfig(json.result()), res3 -> {
                              if (res3.succeeded()) {
                                vertx.deployVerticle(new QueryApiVerticle(),
                                    new DeploymentOptions().setConfig(json.result()), res4 -> {
                                      if (res4.succeeded()) {
                                        vertx.deployVerticle(new EventPublisherVerticle(),
                                            new DeploymentOptions().setConfig(json.result()),
                                            res5 -> {
                                              if (res5.succeeded()) {
                                                vertx.deployVerticle(
                                                    new EventToDbTransferVerticle(),
                                                    new DeploymentOptions()
                                                        .setConfig(json.result()),
                                                    res6 -> {
                                                      if (res6.succeeded()) {
                                                        vertx.deployVerticle(
                                                            new DummyClientVerticle(),
                                                            new DeploymentOptions()
                                                                .setConfig(json.result()),
                                                            res7 -> {
                                                              if (res7.succeeded()) {
                                                                startPromise.complete();
                                                              } else {
                                                                startPromise.fail(res7.cause());
                                                              }
                                                            });
                                                      } else {
                                                        startPromise.fail(res6.cause());
                                                      }
                                                    });
                                              } else {
                                                startPromise.fail(res5.cause());
                                              }
                                            });
                                      } else {
                                        startPromise.fail(res4.cause());
                                      }
                                    });
                              } else {
                                startPromise.fail(res3.cause());
                              }
                            });
                      } else {
                        startPromise.fail(res2.cause());
                      }
                    });
              } else {
                startPromise.fail(res1.cause());
              }
            });
      } else {
        startPromise.fail(json.cause());
      }
    });

  }
}
