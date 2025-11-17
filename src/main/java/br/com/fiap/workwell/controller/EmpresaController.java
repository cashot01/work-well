package br.com.fiap.workwell.controller;

import br.com.fiap.workwell.model.Empresa;
import br.com.fiap.workwell.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping
    public String listarEmpresas(@PageableDefault(size = 5) Pageable pageable, Model model) {
        model.addAttribute("empresas", empresaService.listarEmpresasPaginadas(pageable));
        return "empresas/lista";
    }

    @GetMapping("/nova")
    public String novaEmpresa(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresas/form";
    }

    @PostMapping
    public String salvarEmpresa(@Valid @ModelAttribute Empresa empresa, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "empresas/form";
        }

        empresaService.salvar(empresa);
        redirectAttributes.addFlashAttribute("sucesso", "Empresa cadastrada com sucesso!");
        return "redirect:/empresas";
    }

    @GetMapping("/{id}/editar")
    public String editarEmpresa(@PathVariable Long id, Model model) {
        model.addAttribute("empresa", empresaService.buscarPorId(id));
        return "empresas/form";
    }

    @DeleteMapping("/{id}")
    public String excluirEmpresa(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        empresaService.excluir(id);
        redirectAttributes.addFlashAttribute("sucesso", "Empresa excluída com sucesso!");
        return "redirect:/empresas";
    }
}
