package org.serverest.factory;

import com.github.javafaker.Faker;
import org.serverest.model.UsuarioDTO;

public class UsuarioFactory {

    public static UsuarioDTO criarAdmin() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        Faker faker = new Faker();
        String nome = faker.name().firstName();
        String email = nome + "@restassured.com";
        usuarioDTO.setNome(nome);
        usuarioDTO.setEmail(email);
        usuarioDTO.setPassword("teste");
        usuarioDTO.setAdministrador("true");
        return usuarioDTO;
    }

    public static UsuarioDTO criarCliente() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        Faker faker = new Faker();
        String nome = faker.name().firstName();
        String email = nome + "@restassured.com";
        usuarioDTO.setNome(nome);
        usuarioDTO.setEmail(email);
        usuarioDTO.setPassword("teste");
        usuarioDTO.setAdministrador("false");
        return usuarioDTO;
    }
}