package com.example.usuario_eventgrid.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.usuario_eventgrid.DTO.UsuarioCreateRequest;
import com.example.usuario_eventgrid.DTO.UsuarioUpdateRequest;
import com.example.usuario_eventgrid.service.EventGridPublisherService;


@RestController
@RequestMapping("/usuario")
public class EmailController {

    @Autowired
    private EventGridPublisherService publisherService;

    @PostMapping
    public ResponseEntity<String> createUsuario(@RequestBody UsuarioCreateRequest request) {
        publisherService.publishEvent("UsuarioCreado", "/usuario", request);
        return ResponseEntity.ok("Usuario creado.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUsuario(@PathVariable String id, @RequestBody UsuarioUpdateRequest request) {
        publisherService.publishEvent("UsuarioActualizado", "/usuario/" + id, request);
        return ResponseEntity.ok("Usuario actualizado.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable String id, @RequestBody String entity) {
        publisherService.publishEvent("UsuarioEliminado", "/usuario/" + id, entity);
        return ResponseEntity.ok("Usuario eliminado y evento publicado.");
    }

    @GetMapping
    public ResponseEntity<String> getUsuario(@RequestParam String param) {
        publisherService.publishEvent("UsuarioConsultado", "/usuario?param=" + param, param);
        return ResponseEntity.ok("Evento de consulta enviado.");
    }
}
