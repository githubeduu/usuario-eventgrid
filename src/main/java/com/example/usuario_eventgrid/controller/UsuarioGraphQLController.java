package com.example.usuario_eventgrid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.example.usuario_eventgrid.DTO.UsuarioCreateRequest;
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
}
