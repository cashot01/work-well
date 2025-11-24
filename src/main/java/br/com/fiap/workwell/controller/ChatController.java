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
        return "chat";
    }

    @PostMapping
    public String processarChat(@Valid @ModelAttribute("chatRequest") ChatRequest chatRequest,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "chat";
        }

        try {
            String aiResponse = chatService.analisarSituacao(chatRequest.getSituacao());

            redirectAttributes.addFlashAttribute("aiResponse", aiResponse);
            redirectAttributes.addFlashAttribute("situacaoUsuario", chatRequest.getSituacao());

            return "redirect:/chat";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Ocorreu um erro ao processar a solicitação de IA: " + e.getMessage());
            return "redirect:/chat";
        }
    }
}
