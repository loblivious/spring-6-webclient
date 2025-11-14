package com.loblivious.spring6webclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient.Builder;

@Configuration
public class WebClientConfig implements WebClientCustomizer {

  private final String rootUrl;
  private final String clientRegistrationId;
  private final ReactiveOAuth2AuthorizedClientManager authorizedClientManager;

  public WebClientConfig(@Value("${beer.host.rootUrl}") String rootUrl,
      @Value("${oauth2.client.registrationId}") String clientRegistrationId,
      ReactiveOAuth2AuthorizedClientManager authorizedClientManager) {
    this.rootUrl = rootUrl;
    this.clientRegistrationId = clientRegistrationId;
    this.authorizedClientManager = authorizedClientManager;
  }

  @Override
  public void customize(Builder webClientBuilder) {
    ServerOAuth2AuthorizedClientExchangeFilterFunction oauth
        = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
    oauth.setDefaultClientRegistrationId(clientRegistrationId);
    webClientBuilder.baseUrl(this.rootUrl).filter(oauth);
  }
}
