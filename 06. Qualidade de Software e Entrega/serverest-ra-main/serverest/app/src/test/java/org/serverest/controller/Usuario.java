package org.serverest.controller;

import org.hamcrest.Matchers;
import org.serverest.model.UsuarioDTO;
import io.restassured.http.ContentType;
import org.serverest.util.Ambiente;
import org.serverest.util.Endpoint;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static io.restassured.RestAssured.given;
public class Usuario {
    public static String cadastrar(UsuarioDTO usuarioDTO, Integer statusCode, String mensagem) {

        return given()
                .body("{\n" +
                        "  \"nome\": \"" + usuarioDTO.getNome() + "\",\n" +
                        "  \"email\": \"" + usuarioDTO.getEmail() + "\",\n" +
                        "  \"password\": \"" + usuarioDTO.getPassword() + "\",\n" +
                        "  \"administrador\": \"" + usuarioDTO.getAdministrador() + "\"\n" +
                        "}")
                .contentType(ContentType.JSON)
            .when()
                .post(Ambiente.desenvolvimento + Endpoint.usuarios)
            .then()
                .statusCode(statusCode)
                .body("message", is(mensagem))
                .extract().path("_id");
    }

    public static String autenticar(UsuarioDTO usuarioDTO, Integer statusCode, String message) {

        return given()
                .body("{\n" +
                        "  \"email\": \"" + usuarioDTO.getEmail() + "\",\n" +
                        "  \"password\": \"" + usuarioDTO.getPassword() + "\"\n" +
                        "}")
                .contentType(ContentType.JSON)
            .when()
                .post(Ambiente.desenvolvimento + Endpoint.login)
            .then()
                .statusCode(statusCode)
                .body("message", is(message))
                .extract().path("authorization");
    }

    public static void listar(Integer statusCode) {

        when()
                .get(Ambiente.desenvolvimento + Endpoint.usuarios)
        .then()
                .statusCode(statusCode);
    }

    public static void buscarPorId(UsuarioDTO usuarioDTO, Integer statusCode) {
        given()
                .pathParam("_id", usuarioDTO.getId())
        .when()
                .get(Ambiente.desenvolvimento + Endpoint.usuariosId)
        .then()
                .statusCode(statusCode)
                .body("nome", Matchers.is(usuarioDTO.getNome()))
                .body("email", Matchers.is(usuarioDTO.getEmail()));
    }

    public static void excluir(UsuarioDTO usuarioDTO, Integer statusCode, String mensagem) {
        given()
                .pathParam("_id", usuarioDTO.getId())
        .when()
                .delete(Ambiente.desenvolvimento + Endpoint.usuariosId)
        .then()
                .statusCode(statusCode)
                .body("message", is(mensagem));
    }
}