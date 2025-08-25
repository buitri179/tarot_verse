package org.example.tarot_verse_backend.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal != null) {
            return "redirect:/dashboard";
        }
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal != null) {
            model.addAttribute("name", principal.getAttribute("name"));
            model.addAttribute("email", principal.getAttribute("email"));

            // Lấy URL ảnh đại diện
            Map<String, Object> picture = principal.getAttribute("picture");
            if (picture != null && picture.containsKey("data")) {
                Map<String, Object> pictureData = (Map<String, Object>) picture.get("data");
                model.addAttribute("picture", pictureData.get("url"));
            }
        }
        return "dashboard";
    }

    @GetMapping("/api/user")
    @ResponseBody
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> userInfo = new HashMap<>();

        if (principal != null) {
            userInfo.put("authenticated", true);
            userInfo.put("name", principal.getAttribute("name"));
            userInfo.put("email", principal.getAttribute("email"));
            userInfo.put("id", principal.getAttribute("id"));

            // Lấy URL ảnh đại diện
            Map<String, Object> picture = principal.getAttribute("picture");
            if (picture != null && picture.containsKey("data")) {
                Map<String, Object> pictureData = (Map<String, Object>) picture.get("data");
                userInfo.put("picture", pictureData.get("url"));
            }
        } else {
            userInfo.put("authenticated", false);
        }

        return userInfo;
    }
}

