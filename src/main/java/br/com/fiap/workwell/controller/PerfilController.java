package br.com.fiap.workwell.controller;

import br.com.fiap.workwell.model.Usuario;
import br.com.fiap.workwell.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    private final UsuarioService usuarioService;

    public PerfilController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String visualizarPerfil(Authentication authentication, Model model) {
        String email = authentication.getName();

        Usuario usuario = usuarioService.buscarPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        model.addAttribute("usuario", usuario);

        return "perfil";
    }
}
