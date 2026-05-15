import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class sentiment {

    private static final String ENDPOINT = "https://language-ex4-letfarias.cognitiveservices.azure.com";
    private static final String KEY = System.getenv("AZURE_LANGUAGE_KEY");

    public static void main(String[] args) throws Exception {

        if (KEY == null || KEY.isBlank()) {
            throw new RuntimeException("Defina a variável de ambiente AZURE_LANGUAGE_KEY antes de executar.");
        }

        String text = "gostei muito do atendimento, foi rápido e eficiente.";
        String url = ENDPOINT + "/text/analytics/v3.2/sentiment?opinionMining=true";

        String bodyJson =
            "{\n" +
            "  \"documents\": [\n" +
            "    {\n" +
            "      \"id\": \"1\",\n" +
            "      \"language\": \"pt-BR\",\n" +
            "      \"text\": \"" + escapeJson(text) + "\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Ocp-Apim-Subscription-Key", KEY)
                .POST(HttpRequest.BodyPublishers.ofString(bodyJson))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("HTTP " + resp.statusCode());
        System.out.println(resp.body());
    }

    private static String escapeJson(String s) {
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
