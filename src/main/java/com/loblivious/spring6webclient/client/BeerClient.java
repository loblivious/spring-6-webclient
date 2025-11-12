package com.loblivious.spring6webclient.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.loblivious.spring6webclient.model.BeerDTO;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerClient {

  Flux<String> listBeer();

  Flux<Map> listBeerMap();

  Flux<JsonNode> listBeerJsonNode();

  Mono<Page<BeerDTO>> listBeerPage();

  Mono<BeerDTO> getBeerById(UUID id);
}
