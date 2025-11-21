package br.com.fiap.workwell.controller;

import br.com.fiap.workwell.model.Usuario;
import br.com.fiap.workwell.service.UsuarioService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String listarUsuarios(@PageableDefault(size = 5) Pageable pageable, Model model) {
        model.addAttribute("usuarios", usuarioService.listarUsuariosPaginadas(pageable));
        return "usuarios";
    }


    @GetMapping("/acesso-negado")
    public String acessoNegado() {
        return "acesso-negado";
    }

    @PostMapping("/executar-fn-usuario-json")
    @PreAuthorize("hasRole('ADMIN')")
    public String executarFnUsuarioJson(
            @RequestParam Long usuarioId,
            RedirectAttributes redirectAttributes) {
        try {
            String json = usuarioService.executarFnUsuarioJson(usuarioId);
            redirectAttributes.addFlashAttribute("sucesso", "JSON do usuário gerado com sucesso!");
            redirectAttributes.addFlashAttribute("resultadoJson", json);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao gerar JSON: " + e.getMessage());
        }
        return "redirect:/usuarios";
    }

    @PostMapping("/executar-fn-compatibilidade")
    @PreAuthorize("hasRole('ADMIN')")
    public String executarFnCompatibilidade(
            @RequestParam Long usuarioId,
            @RequestParam String vagaTitulo,
            @RequestParam String vagaCompetencias,
            @RequestParam(defaultValue = "6") Integer nivelStressMax,
            RedirectAttributes redirectAttributes) {
        try {
            String json = usuarioService.executarFnCompatibilidadeVaga(usuarioId, vagaTitulo, vagaCompetencias, nivelStressMax);
            redirectAttributes.addFlashAttribute("sucesso", "Compatibilidade calculada com sucesso!");
            redirectAttributes.addFlashAttribute("resultadoJson", json);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao calcular compatibilidade: " + e.getMessage());
        }
        return "redirect:/usuarios";
    }
}
