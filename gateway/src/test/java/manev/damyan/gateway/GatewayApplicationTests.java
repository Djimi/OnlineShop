package manev.damyan.gateway;

import manev.damyan.gateway.config.ServicesEndpoints;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "services.inventoryService.host=localhost",
                "services.inventoryService.port=${wiremock.server.port}" })
@AutoConfigureWireMock(port = 0)
class GatewayApplicationTests {

    public static final String ITEMS_RESPONSE = "[\n" +
            "    {\n" +
            "        \"id\": 1,\n" +
            "        \"name\": \"Laptop Dell XP5060\",\n" +
            "        \"description\": \"This the second laptop in our store\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 2,\n" +
            "        \"name\": \"Laptop Mac 11\",\n" +
            "        \"description\": \"This the second laptop in our store\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 3,\n" +
            "        \"name\": \"HPLapto\",\n" +
            "        \"description\": \"Some old model of laptop\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 4,\n" +
            "        \"name\": \"HPLapto\",\n" +
            "        \"description\": \"Some old model of laptop\"\n" +
            "    }\n" +
            "]";

    @Autowired
    private ServicesEndpoints servicesEndpoints;

    @Autowired
    private WebTestClient webClient;

    @Test
    void contextLoads() {
    }

    @Test
    public void testAllItems() {

        stubFor(get(urlEqualTo("/items"))
                .willReturn(aResponse()
                        .withBody(ITEMS_RESPONSE)
                        .withHeader("machine-hostname", "Damyaneca")));

        webClient.get().uri("/items")
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .valueEquals("machine-hostname", "Damyaneca")
                .expectBody()
                .json(ITEMS_RESPONSE);
    }
}
