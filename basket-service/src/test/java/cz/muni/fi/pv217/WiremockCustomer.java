package cz.muni.fi.pv217;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Collections;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WiremockCustomer implements QuarkusTestResourceLifecycleManager {

    private WireMockServer wireMockServer;

    @Override
    public Map<String, String> start() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();

        stubFor(get(urlEqualTo("/customer/1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody(
                                "{\n" +
                                        "  \"id\": 1,\n" +
                                        "  \"email\": \"chuck@mail.com\",\n" +
                                        "  \"name\": \"Chuck\",\n" +
                                        "  \"passwordHash\": \"$2a$04$UAe91i4Fc0n9LILx/Uejme9XhJXGu1oFha2TZ55DQHjqA0o4oRqPS\"\n" +
                                        "}"
                        )));

        stubFor(get(urlEqualTo("/customer/2"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody(
                                "{\n" +
                                        "  \"id\": 2,\n" +
                                        "  \"email\": \"alice@mail.com\",\n" +
                                        "  \"name\": \"Alice\",\n" +
                                        "  \"passwordHash\": \"$2a$04$UAe91i4Fc0n9LILx/Uejme9XhJXGu1oFha2TZ55DQHjqA0o4oRqPS\"\n" +
                                        "}"
                        )));

        return Collections.singletonMap("customer-service-client/mp-rest/url", wireMockServer.baseUrl());
    }

    @Override
    public void stop() {
        if (null != wireMockServer) {
            wireMockServer.stop();
        }
    }
}
