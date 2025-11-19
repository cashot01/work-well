package br.com.fiap.workwell.controller;

import br.com.fiap.workwell.model.Usuario;
import br.com.fiap.workwell.service.UsuarioService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
