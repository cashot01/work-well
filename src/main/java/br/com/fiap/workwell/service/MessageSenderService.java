package br.com.fiap.workwell.service;

import br.com.fiap.workwell.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageSenderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Envia uma mensagem para a fila de análise de IA.
     * @param message O objeto de mensagem a ser enviado (será convertido para JSON).
     */
    public void sendAiAnalysisMessage(Object message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_AI_ANALYSIS, message);
    }
}
