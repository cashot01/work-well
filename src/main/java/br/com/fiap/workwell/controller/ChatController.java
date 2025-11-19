package br.com.fiap.workwell.controller;

import br.com.fiap.workwell.dto.ChatRequest;
import br.com.fiap.workwell.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public String exibirChat(Model model) {
        if (!model.containsAttribute("chatRequest")) {
            model.addAttribute("chatRequest", new ChatRequest());
        }
        // A resposta da IA será adicionada ao modelo se existir
        return "chat";
    }

    @PostMapping
    public String processarChat(@Valid @ModelAttribute("chatRequest") ChatRequest chatRequest,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Se houver erros de validação, retorna para o formulário
            return "chat";
        }

        try {
            // Chama o serviço de IA para analisar a situação
            String aiResponse = chatService.analisarSituacao(chatRequest.getSituacao());

            // Adiciona a resposta da IA e a situação original ao modelo para exibição
            redirectAttributes.addFlashAttribute("aiResponse", aiResponse);
            redirectAttributes.addFlashAttribute("situacaoUsuario", chatRequest.getSituacao());

            // Redireciona para o GET para evitar reenvio do formulário
            return "redirect:/chat";

        } catch (Exception e) {
            // Em caso de erro (ex: chave da API inválida, problema de conexão),
            // adiciona uma mensagem de erro e retorna
            redirectAttributes.addFlashAttribute("erro", "Ocorreu um erro ao processar a solicitação de IA: " + e.getMessage());
            return "redirect:/chat";
        }
    }
}
