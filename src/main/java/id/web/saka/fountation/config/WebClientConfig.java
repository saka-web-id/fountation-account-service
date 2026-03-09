package id.web.saka.fountation.config;

import id.web.saka.fountation.util.Env;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;
import java.util.Map;

@Configuration
public class WebClientConfig {

    private final Env env;

    public WebClientConfig(id.web.saka.fountation.util.Env env) {
        this.env = env;
    }

    @Bean
    public HttpClient httpClient() {
        ConnectionProvider provider = ConnectionProvider.builder("custom-account")
                .maxIdleTime(Duration.ofSeconds(20)) // Clear connections that have been idle for 20s
                .maxLifeTime(Duration.ofMinutes(1))  // Max life of a connection
                .evictInBackground(Duration.ofSeconds(30)) // Evict idle connections in background
                .build();

        return HttpClient.create(provider)
                .responseTimeout(Duration.ofSeconds(10)); // Request timeout
    }

    @Bean
    public WebClientCustomizer webClientCustomizer(HttpClient httpClient) {
        return webClientBuilder -> webClientBuilder.clientConnector(new ReactorClientHttpConnector(httpClient));
    }

    @Bean
    public Mono<WebClient> webClientAuthorization(HttpClient httpClient) {
        return getAccessToken(httpClient)
                .map(token ->
                        WebClient.builder()
                                .clientConnector(new ReactorClientHttpConnector(httpClient))
                                .baseUrl(env.getFountationServiceAuthorizationUrl())
                                .defaultHeaders(headers -> {
                                    headers.setBearerAuth(token);
                                    headers.set("Accept", "application/json");
                                    headers.set("Content-Type", "application/json");
                                })
                                .build()
                );
    }

    private Mono<String> getAccessToken(HttpClient httpClient) {
        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        return webClient.post()
                .uri(env.getClientRegistrationInternalServiceTokenUri())
                .bodyValue(Map.of(
                        "client_id", env.getClientRegistrationInternalServiceClientId(),
                        "client_secret", env.getClientRegistrationInternalServiceClientSecret(),
                        "audience", env.getFountationServiceSecurityJwtAudience(), // YOUR_API_IDENTIFIER
                        "grant_type", env.getClientRegistrationInternalServiceGrantType(),
                        "scope", env.getClientRegistrationInternalServiceScope()
                ))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("access_token"));
    }

}
