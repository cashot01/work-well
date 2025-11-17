package br.com.fiap.workwell.controller;

import br.com.fiap.workwell.model.CheckinDiario;
import br.com.fiap.workwell.service.CheckinDiarioService;
import br.com.fiap.workwell.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/checkins")
public class CheckinDiarioController {

    private final CheckinDiarioService checkinService;
    private final UsuarioService usuarioService;

    public CheckinDiarioController(CheckinDiarioService checkinService, UsuarioService usuarioService) {
        this.checkinService = checkinService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarCheckins(@PageableDefault(size = 5) Pageable pageable, Model model) {
        model.addAttribute("checkins", checkinService.listarCheckinsPaginados(pageable));
        return "checkins/lista";
    }

    @GetMapping("/novo")
    public String novoCheckin(Model model) {
        model.addAttribute("checkin", new CheckinDiario());
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "checkins/form";
    }

    @PostMapping
    public String salvarCheckin(@Valid @ModelAttribute CheckinDiario checkin, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("usuarios", usuarioService.listarTodos());
            return "checkins/form";
        }

        checkinService.salvar(checkin);
        redirectAttributes.addFlashAttribute("sucesso", "Check-in cadastrado com sucesso!");
        return "redirect:/checkins";
    }

    @GetMapping("/{id}/editar")
    public String editarCheckin(@PathVariable Long id, Model model) {
        model.addAttribute("checkin", checkinService.buscarPorId(id));
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "checkins/form";
    }

    @DeleteMapping("/{id}")
    public String excluirCheckin(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        checkinService.excluir(id);
        redirectAttributes.addFlashAttribute("sucesso", "Check-in excluído com sucesso!");
        return "redirect:/checkins";
    }
}
