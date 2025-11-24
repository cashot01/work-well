package br.com.fiap.workwell.service;

import br.com.fiap.workwell.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AiAnalysisConsumerService {

    @Autowired
    private HuggingFaceService huggingFaceService;
    @RabbitListener(queues = RabbitMQConfig.QUEUE_AI_ANALYSIS)
    public void receiveMessage(String message) {
        System.out.println("Mensagem recebida da fila para análise de IA: " + message);

        try {
            String aiResponse = huggingFaceService.analisarSituacao(message);
            System.out.println("Resposta da IA para a situação do usuário: " + aiResponse);

        } catch (Exception e) {
            System.err.println("Erro ao processar a mensagem com Hugging Face: " + e.getMessage());
        }
    }
}
