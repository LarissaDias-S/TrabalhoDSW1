package trabalho.dsw1.vagas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboardRedirect(Authentication authentication) {
    if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
        return "admin/dashboard-admin";
    } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_EMPRESA"))) {
        return "empresas/dashboard-empresas";
    } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PROFISSIONAL"))) {
        return "profissionais/dashboard";
    }
    return "redirect:/"; // fallback
}    
    
}
