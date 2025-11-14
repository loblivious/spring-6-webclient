package com.loblivious.spring6webclient.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.loblivious.spring6webclient.model.BeerDTO;
import com.loblivious.spring6webclient.model.RestPageImpl;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BeerClientImpl implements BeerClient {

  public static final String BEER_PATH = "/api/v1/beer";
  public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";


  private final ParameterizedTypeReference<RestPageImpl<BeerDTO>> type = new ParameterizedTypeReference<>() {
  };

  private final WebClient webClient;

  public BeerClientImpl(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.build();
  }

  @Override
  public Flux<String> listBeer() {
    return webClient.get().uri(BEER_PATH)
        .retrieve().bodyToFlux(String.class);
  }

  @Override
  public Flux<Map> listBeerMap() {
    return webClient.get().uri(BEER_PATH)
        .retrieve().bodyToFlux(Map.class);
  }

  @Override
  public Flux<JsonNode> listBeerJsonNode() {
    return webClient.get().uri(BEER_PATH)
        .retrieve().bodyToFlux(JsonNode.class);
  }

  @Override
  public Mono<Page<BeerDTO>> listBeerPage() {
    return webClient.get().uri(BEER_PATH)
        .retrieve().bodyToMono(type).map(bar -> (Page<BeerDTO>) bar);
  }

  @Override
  public Mono<BeerDTO> getBeerById(UUID id) {
    return webClient.get().uri(uriBuilder -> uriBuilder.path(BEER_PATH_ID).build(id))
        .retrieve().bodyToMono(BeerDTO.class);
  }

  @Override
  public Mono<Page<BeerDTO>> getBeerByBeerStyle(String beerStyle) {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder.path(BEER_PATH).queryParam("beerStyle", beerStyle).build())
        .retrieve().bodyToMono(type).map(bar -> bar);
  }

  @Override
  public Mono<BeerDTO> createBeer(BeerDTO beerDto) {
    return webClient.post().uri(BEER_PATH)
        .body(Mono.just(beerDto), BeerDTO.class)
        .retrieve()
        .toBodilessEntity()
        .map(voidResponseEntity ->
            Objects.requireNonNull(voidResponseEntity.getHeaders().get("Location")).getFirst()
        )
        .map(path -> UUID.fromString(path.split("/")[path.split("/").length - 1]))
        .flatMap(this::getBeerById);
  }
}
