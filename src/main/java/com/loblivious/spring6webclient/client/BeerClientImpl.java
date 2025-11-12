package com.loblivious.spring6webclient.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.loblivious.spring6webclient.model.BeerDTO;
import com.loblivious.spring6webclient.model.RestPageImpl;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BeerClientImpl implements BeerClient {

  public static final String BEER_PATH = "/api/v1/beer";

  private final WebClient webClient;

  public BeerClientImpl(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
  }

  @Override
  public Flux<String> listBeer() {
    return webClient.get().uri(BEER_PATH, String.class)
        .retrieve().bodyToFlux(String.class);
  }

  @Override
  public Flux<Map> listBeerMap() {
    return webClient.get().uri(BEER_PATH, Map.class)
        .retrieve().bodyToFlux(Map.class);
  }

  @Override
  public Flux<JsonNode> listBeerJsonNode() {
    return webClient.get().uri(BEER_PATH, JsonNode.class)
        .retrieve().bodyToFlux(JsonNode.class);
  }

  @Override
  public Mono<Page<BeerDTO>> listBeerPage() {
    ParameterizedTypeReference<RestPageImpl<BeerDTO>> type = new ParameterizedTypeReference<>() {
    };

    return webClient.get().uri(BEER_PATH)
        .retrieve().bodyToMono(type).map(bar -> (Page<BeerDTO>) bar);
  }


}
