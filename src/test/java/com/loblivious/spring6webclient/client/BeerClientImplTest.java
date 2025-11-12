package com.loblivious.spring6webclient.client;

import static org.awaitility.Awaitility.await;

import com.loblivious.spring6webclient.model.BeerStyle;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

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

  @Test
  void listBeerPage() {
    AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    beerClient.listBeerPage().subscribe(response -> {
      System.out.println(response.getContent());
      atomicBoolean.set(true);
    });

    await().untilTrue(atomicBoolean);
  }

  @Test
  void testGetBeerById() {

    AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    beerClient.listBeerPage()
        .flatMap(page -> Flux.fromIterable(page.getContent()).next())
        .flatMap(dto -> beerClient.getBeerById(dto.getId()))
        .subscribe(byIdDto -> {
          System.out.println(byIdDto.getBeerName());
          atomicBoolean.set(true);
        });

    await().untilTrue(atomicBoolean);
  }

  @Test
  void testGetBeerByBeerStyle() {
    AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    beerClient.getBeerByBeerStyle(BeerStyle.PALE_ALE.name()).subscribe(response -> {
          System.out.println(response.getContent());
          atomicBoolean.set(true);
        }
    );

    await().untilTrue(atomicBoolean);
  }
}