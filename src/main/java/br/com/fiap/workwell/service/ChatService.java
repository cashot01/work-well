package br.com.fiap.workwell.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço de chat que utiliza Hugging Face para análise de situações de bem-estar corporativo.
 */
@Service
public class ChatService {

    private final HuggingFaceService huggingFaceService;

    @Autowired
    public ChatService(HuggingFaceService huggingFaceService) {
        this.huggingFaceService = huggingFaceService;
    }

    /**
     * Analisa a situação do usuário usando o Hugging Face (microsoft/DialoGPT-medium).
     * @param situacao A situação descrita pelo usuário.
     * @return A resposta de análise da IA.
     */
    public String analisarSituacao(String situacao) {
        return huggingFaceService.analisarSituacao(situacao);
    }
}
