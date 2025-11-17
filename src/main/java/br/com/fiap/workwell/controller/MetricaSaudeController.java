package br.com.fiap.workwell.controller;

import br.com.fiap.workwell.model.MetricaSaude;
import br.com.fiap.workwell.service.MetricaSaudeService;
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
@RequestMapping("/metricas")
public class MetricaSaudeController {

    private final MetricaSaudeService metricaService;
    private final UsuarioService usuarioService;

    public MetricaSaudeController(MetricaSaudeService metricaService, UsuarioService usuarioService) {
        this.metricaService = metricaService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarMetricas(@PageableDefault(size = 5) Pageable pageable, Model model) {
        model.addAttribute("metricas", metricaService.listarMetricasPaginadas(pageable));
        return "metricas/lista";
    }

    @GetMapping("/nova")
    public String novaMetrica(Model model) {
        model.addAttribute("metrica", new MetricaSaude());
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "metricas/form";
    }

    @PostMapping
    public String salvarMetrica(@Valid @ModelAttribute MetricaSaude metrica, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("usuarios", usuarioService.listarTodos());
            return "metricas/form";
        }

        metricaService.salvar(metrica);
        redirectAttributes.addFlashAttribute("sucesso", "Métrica de saúde cadastrada com sucesso!");
        return "redirect:/metricas";
    }

    @GetMapping("/{id}/editar")
    public String editarMetrica(@PathVariable Long id, Model model) {
        model.addAttribute("metrica", metricaService.buscarPorId(id));
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "metricas/form";
    }

    @DeleteMapping("/{id}")
    public String excluirMetrica(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        metricaService.excluir(id);
        redirectAttributes.addFlashAttribute("sucesso", "Métrica de saúde excluída com sucesso!");
        return "redirect:/metricas";
    }
}
