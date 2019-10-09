package net.thumtack.onlineshop.controller;

import net.thumtack.onlineshop.services.interfaces.DebugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class DebugController {
    private DebugService debugService;

    @Autowired
    public DebugController(DebugService debugService) {
        this.debugService = debugService;
    }

    @PostMapping("debug/clear")
    public ResponseEntity clearDB(){
        return ResponseEntity.ok(debugService.clearDB());
    }
}
