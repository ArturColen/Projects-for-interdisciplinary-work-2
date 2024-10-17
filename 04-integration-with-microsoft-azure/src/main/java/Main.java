import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

/**
 * Exercício 4: Computação em Nuvem e IA como Serviço
 * 
 * @author Artur Bomtempo Colen
 * @version 1.0, 17/10/2024
 */

public class Main {
    private static final String subscriptionKey = "YOUR_SUBSCRIPTION_KEY";
    private static final String endpoint = "API_ENDPOINT";
    
    public static void main(String[] args) {
        String textToAnalyze = "Estudar Ciência da Computação na PUC Minas tem sido uma experiência incrível, mas também desafiadora. A disciplina de Algoritmos e Estruturas de Dados, em especial, despertou uma mistura de emoções ao longo do semestre. Por um lado, sinto uma imensa satisfação quando consigo resolver um problema complexo, aplicando as técnicas ensinadas em sala de aula. Aquela sensação de vitória ao finalmente entender como funciona uma árvore binária ou ao otimizar um algoritmo de ordenação é simplesmente fantástica!\n"
        		+ "\n"
        		+ "No entanto, não posso negar que, em alguns momentos, me sinto frustrado. Algumas estruturas de dados, como grafos, parecem indecifráveis à primeira vista. Às vezes, é como se o tempo investido em estudar não fosse suficiente para dominar tudo o que preciso. A pressão das entregas e provas chega a ser desgastante, mas faz parte do processo de aprendizado.\n"
        		+ "\n"
        		+ "Ao mesmo tempo, há uma empolgação que cresce dentro de mim quando penso no potencial que essas habilidades me trarão no futuro. Saber que estou adquirindo conhecimento que será crucial para minha carreira como desenvolvedor é extremamente motivador. Sei que, no final, todo o esforço valerá a pena, e isso me enche de esperança e expectativa.\n"
        		+ "\n"
        		+ "A convivência com colegas também ajuda. Estar rodeado de pessoas que compartilham as mesmas dificuldades e conquistas torna o ambiente mais leve. Conversas sobre programação, algoritmos e o mundo da tecnologia são frequentes e sempre estimulantes.\n"
        		+ "\n"
        		+ "Em resumo, Algoritmos e Estruturas de Dados é uma disciplina que provoca um verdadeiro turbilhão de sentimentos: da frustração à empolgação, do cansaço à motivação. Mas, sem dúvida, é uma das matérias mais essenciais e transformadoras do curso.";
        HttpClient client = HttpClient.newHttpClient();
        
        Gson gson = new Gson();

        Map<String, Object> document = new HashMap<>();
        document.put("language", "pt");
        document.put("id", "1");
        document.put("text", textToAnalyze);

        Map<String, Object> documents = new HashMap<>();
        documents.put("documents", new Object[]{document});

        String json = gson.toJson(documents);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(endpoint + "/text/analytics/v3.0/sentiment"))
            .header("Content-Type", "application/json")
            .header("Ocp-Apim-Subscription-Key", subscriptionKey)
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(System.out::println)
            .join();
    }
}