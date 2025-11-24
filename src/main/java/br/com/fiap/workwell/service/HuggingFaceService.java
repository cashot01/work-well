package br.com.fiap.workwell.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.HashMap;
import java.util.Map;

@Service
public class HuggingFaceService {

    private final WebClient webClient;
    private final String apiKey;
    private final String modelId;

    private final String[] modelosCompatíveis = {
            "microsoft/DialoGPT-small",
            "microsoft/DialoGPT-medium",
            "microsoft/DialoGPT-large",
            "facebook/blenderbot-400M-distill",
            "facebook/blenderbot-1B-distill",
            "google/flan-t5-small",
            "google/flan-t5-base",
            "google/flan-t5-large",
            "bert-base-uncased",
            "gpt2",
            "distilgpt2"
    };

    public HuggingFaceService(
            @Value("${huggingface.api.key}") String apiKey,
            @Value("${huggingface.api.model:microsoft/DialoGPT-small}") String modelId,
            WebClient.Builder webClientBuilder) {
        this.apiKey = apiKey;
        this.modelId = modelId;

        this.webClient = webClientBuilder
                .baseUrl("https://api-inference.huggingface.co")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public String generateResponse(String prompt) {
        return tryGenerateWithMultipleEndpoints(prompt, modelId);
    }

    private String tryGenerateWithMultipleEndpoints(String prompt, String model) {
        String[] endpoints = {
                "https://api-inference.huggingface.co/models/" + model,
                "https://router.huggingface.co/hf-inference/models/" + model,
                "https://" + model + ".hf.space/api/predict"
        };

        for (String endpoint : endpoints) {
            System.out.println("🔄 Tentando endpoint: " + endpoint);
            String result = trySingleEndpoint(prompt, endpoint);
            if (!result.contains("Erro") && !result.contains("não encontrado") && !result.contains("não suportado")) {
                return result;
            }
        }

        return "❌ Todos os endpoints falharam para o modelo: " + model;
    }

    private String trySingleEndpoint(String prompt, String endpoint) {
        try {
            WebClient client = WebClient.builder()
                    .baseUrl(endpoint)
                    .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("inputs", prompt);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("max_new_tokens", 100);
            parameters.put("temperature", 0.7);

            requestBody.put("parameters", parameters);

            String response = client.post()
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println("📥 Resposta do endpoint: " + response);

            if (response != null && response.contains("generated_text")) {
                return extractGeneratedText(response);
            } else if (response != null && response.contains("error")) {
                return "Erro na API: " + response;
            }

            return "Resposta vazia ou inválida";

        } catch (WebClientResponseException e) {
            return "Erro " + e.getStatusCode() + ": " + e.getResponseBodyAsString();
        } catch (Exception e) {
            return "Erro inesperado: " + e.getMessage();
        }
    }

    private String extractGeneratedText(String jsonResponse) {
        try {
            if (jsonResponse.contains("generated_text")) {
                int start = jsonResponse.indexOf("generated_text") + 16;
                int end = jsonResponse.indexOf("\"", start);
                if (start > 15 && end > start) {
                    return jsonResponse.substring(start, end).trim();
                }
            }
            return jsonResponse;
        } catch (Exception e) {
            return "Erro ao extrair texto: " + e.getMessage();
        }
    }

    public String analisarSituacao(String situacao) {
        System.out.println("🎯 Analisando situação: " + situacao);

        String resposta = generateResponse(
                "User: " + situacao + "\nAssistant:"
        );

        if (resposta.contains("Erro") || resposta.contains("falharam")) {
            for (String modelo : modelosCompatíveis) {
                if (!modelo.equals(modelId)) {
                    System.out.println("🔄 Tentando modelo alternativo: " + modelo);
                    resposta = tryGenerateWithMultipleEndpoints(
                            "User: " + situacao + "\nAssistant:",
                            modelo
                    );
                    if (!resposta.contains("Erro") && !resposta.contains("falharam")) {
                        break;
                    }
                }
            }
        }

        if (resposta.contains("Erro") || resposta.contains("falharam")) {
            return criarRespostaFallback(situacao);
        }

        return resposta;
    }

    private String criarRespostaFallback(String situacao) {
        return "Compreendo que você está passando por: '" + situacao + "'.\n\n" +
                "Como assistente WorkWell, recomendo:\n" +
                "• Conversar com seu gestor sobre suas preocupações\n" +
                "• Buscar apoio da equipe de RH\n" +
                "• Praticar técnicas de autocuidado\n" +
                "• Estabelecer limites saudáveis entre trabalho e vida pessoal";
    }

    public void testarTodosModelos() {
        System.out.println("=== 🧪 TESTE COMPLETO DE MODELOS ===");

        for (String modelo : modelosCompatíveis) {
            System.out.println("\n🔍 Testando: " + modelo);
            String resultado = tryGenerateWithMultipleEndpoints("Hello, test", modelo);
            System.out.println("📝 Resultado: " + resultado);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}