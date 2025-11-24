package br.com.fiap.workwell.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final HuggingFaceService huggingFaceService;

    @Autowired
    public ChatService(HuggingFaceService huggingFaceService) {
        this.huggingFaceService = huggingFaceService;
    }

    public String analisarSituacao(String situacao) {
        return huggingFaceService.analisarSituacao(situacao);
    }
}
