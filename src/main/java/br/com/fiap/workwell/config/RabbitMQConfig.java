package br.com.fiap.workwell.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_AI_ANALYSIS = "aiAnalysisQueue";

    @Bean
    public Queue aiAnalysisQueue() {
        // A fila será durável (persiste após o restart do RabbitMQ)
        return new Queue(QUEUE_AI_ANALYSIS, true);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        // Configura o conversor de mensagens para JSON, facilitando o envio de objetos Java
        return new Jackson2JsonMessageConverter();
    }
}
