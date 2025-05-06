package com.example.usuario_eventgrid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.example.usuario_eventgrid.DTO.UsuarioCreateRequest;
import com.example.usuario_eventgrid.DTO.UsuarioUpdateRequest;
import com.example.usuario_eventgrid.service.EventGridPublisherService;

@Controller
public class UsuarioGraphQLController {

    @Autowired
    private EventGridPublisherService publisherService;

    @MutationMapping
    public String createUsuario(@Argument UsuarioCreateRequest input) {
        publisherService.publishEvent("UsuarioCreado", "/usuario", input);
        return "Usuario creado.";
    }

    @MutationMapping
    public String updateUsuario(@Argument String id, @Argument("input") UsuarioUpdateRequest input) {
        publisherService.publishEvent("UsuarioActualizado", "/usuario/" + id, input);
        return "Usuario actualizado.";
    }

    @MutationMapping
    public String deleteUsuario(@Argument String id, @Argument("input") String entity) {
        publisherService.publishEvent("UsuarioEliminado", "/usuario/" + id, entity);
        return "Usuario eliminado.";
    }

}
