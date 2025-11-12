package br.com.fiap.workwell.controller;

import br.com.fiap.workwell.service.DashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    @PreAuthorize("isAuthenticated()")
    public String dashboard(Model model) {
        model.addAttribute("totalUsuarios", dashboardService.getTotalUsuarios());
        model.addAttribute("totalAlertasAtivos", dashboardService.getTotalAlertasAtivos());
        model.addAttribute("saudeGeral", String.format("%.0f%%", dashboardService.getSaudeGeral()));
        model.addAttribute("totalCasosCriticos", dashboardService.getTotalCasosCriticos());
        model.addAttribute("totalCheckinsHoje", dashboardService.getTotalCheckinsHoje());
        model.addAttribute("totalMetricasHoje", dashboardService.getTotalMetricasHoje());

        return "dashboard";
    }
}
