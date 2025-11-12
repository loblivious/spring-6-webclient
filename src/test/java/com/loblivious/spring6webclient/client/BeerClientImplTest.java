package com.loblivious.spring6webclient.client;

import static org.awaitility.Awaitility.await;

import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BeerClientImplTest {

  @Autowired
  BeerClient beerClient;

  @Test
  void listBeer() {
    AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    beerClient.listBeer().subscribe(response -> {
      System.out.println(response);
      atomicBoolean.set(true);
    });

    await().untilTrue(atomicBoolean);
  }

  @Test
  void listBeerMap() {
    AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    beerClient.listBeerMap().subscribe(response -> {
      System.out.println(response);
      atomicBoolean.set(true);
    });

    await().untilTrue(atomicBoolean);
  }

  @Test
  void listBeerJsonNode() {
    AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    beerClient.listBeerJsonNode().subscribe(response -> {
      System.out.println(response.toPrettyString());
      atomicBoolean.set(true);
    });

    await().untilTrue(atomicBoolean);
  }
}