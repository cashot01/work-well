package br.com.fiap.workwell.controller;

import br.com.fiap.workwell.model.Departamento;
import br.com.fiap.workwell.service.DepartamentoService;
import br.com.fiap.workwell.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

    private final DepartamentoService departamentoService;
    private final EmpresaService empresaService;

    public DepartamentoController(DepartamentoService departamentoService, EmpresaService empresaService) {
        this.departamentoService = departamentoService;
        this.empresaService = empresaService;
    }

    @GetMapping
    public String listarDepartamentos(@PageableDefault(size = 5) Pageable pageable, Model model) {
        model.addAttribute("departamentos", departamentoService.listarDepartamentosPaginados(pageable));
        return "departamentos/lista";
    }

    @GetMapping("/novo")
    public String novoDepartamento(Model model) {
        model.addAttribute("departamento", new Departamento());
        model.addAttribute("empresas", empresaService.listarTodas());
        return "departamentos/form";
    }

    @PostMapping
    public String salvarDepartamento(@Valid @ModelAttribute Departamento departamento, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("empresas", empresaService.listarTodas());
            return "departamentos/form";
        }

        departamentoService.salvar(departamento);
        redirectAttributes.addFlashAttribute("sucesso", "Departamento cadastrado com sucesso!");
        return "redirect:/departamentos";
    }

    @GetMapping("/{id}/editar")
    public String editarDepartamento(@PathVariable Long id, Model model) {
        model.addAttribute("departamento", departamentoService.buscarPorId(id));
        model.addAttribute("empresas", empresaService.listarTodas());
        return "departamentos/form";
    }

    @DeleteMapping("/{id}")
    public String excluirDepartamento(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        departamentoService.excluir(id);
        redirectAttributes.addFlashAttribute("sucesso", "Departamento excluído com sucesso!");
        return "redirect:/departamentos";
    }
}
