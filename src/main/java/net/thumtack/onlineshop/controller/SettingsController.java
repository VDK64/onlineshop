package net.thumtack.onlineshop.controller;

import net.thumtack.onlineshop.dto.Response.ResponseSettingsDto;
import net.thumtack.onlineshop.services.interfaces.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class SettingsController {

    private SettingsService settingsService;

    @Autowired
    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @GetMapping("settings")
    public ResponseEntity<ResponseSettingsDto> servSettings() {
        return ResponseEntity.ok(settingsService.servSettings());
    }
}
