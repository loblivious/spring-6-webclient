package com.loblivious.spring6webclient.client;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;
import reactor.core.publisher.Flux;

public interface BeerClient {

  Flux<String> listBeer();

  Flux<Map> listBeerMap();

  Flux<JsonNode> listBeerJsonNode();
}
