package br.com.fiap.workwell.controller;

import br.com.fiap.workwell.service.HuggingFaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    @Autowired
    private HuggingFaceService huggingFaceService;

    @GetMapping("/test-all")
    public String testAllModels() {
        huggingFaceService.testarTodosModelos();
        return "Teste completo executado. Verifique os logs!";
    }

    @GetMapping("/test-simple")
    public String testSimple() {
        String resultado = huggingFaceService.analisarSituacao("Teste simples");
        return "Resultado: " + resultado;
    }

    @PostMapping("/chat")
    public String testChat(@RequestParam String mensagem) {
        return huggingFaceService.analisarSituacao(mensagem);
    }
}
