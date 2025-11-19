package br.com.fiap.workwell.service;

import br.com.fiap.workwell.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço consumidor de mensagens RabbitMQ que utiliza Hugging Face para análise.
 */
@Service
public class AiAnalysisConsumerService {

    @Autowired
    private HuggingFaceService huggingFaceService;

    /**
     * Consome mensagens da fila e utiliza o Hugging Face para análise.
     * O objeto 'message' deve ser um DTO ou String que represente a situação do usuário.
     * @param message A mensagem recebida da fila.
     */
    @RabbitListener(queues = RabbitMQConfig.QUEUE_AI_ANALYSIS)
    public void receiveMessage(String message) {
        System.out.println("Mensagem recebida da fila para análise de IA: " + message);

        try {
            String aiResponse = huggingFaceService.analisarSituacao(message);
            System.out.println("Resposta da IA para a situação do usuário: " + aiResponse);

            // TODO: Aqui você pode adicionar a lógica para salvar a resposta da IA no banco de dados,
            // enviar um e-mail para o usuário, ou notificar o gestor.
            // Por enquanto, apenas imprimimos no console.

        } catch (Exception e) {
            System.err.println("Erro ao processar a mensagem com Hugging Face: " + e.getMessage());
            // TODO: Adicionar lógica de re-tentativa ou envio para uma fila de erro (DLQ)
        }
    }
}
