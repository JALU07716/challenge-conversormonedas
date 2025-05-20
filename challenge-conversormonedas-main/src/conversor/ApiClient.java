package conversor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class ApiClient {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/41125a1654c4cb35d343163a/latest/USD";

    public String obtenerTasasDeCambio() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public Map<String, Double> parsearTasasDeCambio(String jsonResponse) {
        Map<String, Double> tasasDeCambio = new HashMap<>();
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");

        for (String key : conversionRates.keySet()) {
            tasasDeCambio.put(key, conversionRates.get(key).getAsDouble());
        }

        return tasasDeCambio;
    }
}
