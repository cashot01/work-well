package br.com.fiap.workwell.controller;

import br.com.fiap.workwell.model.AlertaBurnout;
import br.com.fiap.workwell.service.AlertaBurnoutService;
import br.com.fiap.workwell.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/alertas")
public class AlertaBurnoutController {

    private final AlertaBurnoutService alertaService;
    private final UsuarioService usuarioService;

    public AlertaBurnoutController(AlertaBurnoutService alertaService, UsuarioService usuarioService) {
        this.alertaService = alertaService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarAlertas(Model model) {
        model.addAttribute("alertas", alertaService.listarTodos());
        return "alertas/lista";
    }

    @GetMapping("/novo")
    public String novoAlerta(Model model) {
        model.addAttribute("alerta", new AlertaBurnout());
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("niveisRisco", br.com.fiap.workwell.model.NivelRisco.values());
        return "alertas/form";
    }

    @PostMapping
    public String salvarAlerta(@Valid @ModelAttribute AlertaBurnout alerta, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("usuarios", usuarioService.listarTodos());
            model.addAttribute("niveisRisco", br.com.fiap.workwell.model.NivelRisco.values());
            return "alertas/form";
        }

        alertaService.salvar(alerta);
        redirectAttributes.addFlashAttribute("sucesso", "Alerta de burnout cadastrado com sucesso!");
        return "redirect:/alertas";
    }

    @GetMapping("/{id}/editar")
    public String editarAlerta(@PathVariable Long id, Model model) {
        model.addAttribute("alerta", alertaService.buscarPorId(id));
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("niveisRisco", br.com.fiap.workwell.model.NivelRisco.values());
        return "alertas/form";
    }

    @DeleteMapping("/{id}")
    public String excluirAlerta(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        alertaService.excluir(id);
        redirectAttributes.addFlashAttribute("sucesso", "Alerta de burnout excluído com sucesso!");
        return "redirect:/alertas";
    }
}