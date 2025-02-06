package org.serverest.controller;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.serverest.model.*;
import org.serverest.util.Ambiente;
import org.serverest.util.Endpoint;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static io.restassured.RestAssured.given;

public class Produto {
    public static String cadastrar(ProdutoDTO produtoDTO, String token, Integer statusCode, String mensagem) {
        return given()
                    .body("{\n" +
                            "  \"nome\": \"" + produtoDTO.getNome() + "\",\n" +
                            "  \"preco\": \"" + produtoDTO.getPreco() + "\",\n" +
                            "  \"descricao\": \"" + produtoDTO.getDescricao() + "\",\n" +
                            "  \"quantidade\": \"" + produtoDTO.getQuantidade() + "\"\n" +
                            "}")
                    .contentType(ContentType.JSON)
                    .header("Authorization", token)
               .when()
                    .post(Ambiente.desenvolvimento + Endpoint.produtos)
               .then()
                    .statusCode(statusCode)
                    .body("message", is(mensagem))
                    .extract().path("_id");
    }

    public static void atualizar(ProdutoDTO produtoDTO, String token, Integer statusCode, String mensagem) {
        given()
                .body("{\n" +
                        "  \"nome\": \"" + produtoDTO.getNome() + "\",\n" +
                        "  \"preco\": \"" + produtoDTO.getPreco() + "\",\n" +
                        "  \"descricao\": \"" + produtoDTO.getDescricao() + "\",\n" +
                        "  \"quantidade\": \"" + produtoDTO.getQuantidade() + "\"\n" +
                        "}")
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .pathParam("_id", produtoDTO.getId())
        .when()
                .put(Ambiente.desenvolvimento + Endpoint.produtosId)
        .then()
                .statusCode(statusCode)
                .body("message", is(mensagem));
    }

    public static void buscarPorId(ProdutoDTO produtoDTO, Integer statusCode) {
        given()
                .pathParam("_id", produtoDTO.getId())
        .when()
                .get(Ambiente.desenvolvimento + Endpoint.produtosId)
        .then()
                .statusCode(statusCode);
    }

    public static void excluir(ProdutoDTO produtoDTO, String token, Integer statusCode, String message) {
        given()
                .pathParam("_id", produtoDTO.getId())
                .header("Authorization", token)
        .when()
                .delete(Ambiente.desenvolvimento + Endpoint.produtosId)
        .then()
                .statusCode(statusCode)
                .body("message", Matchers.is(message));
    }

    public static ProdutosDTO buscarPorDescricao(Integer statusCode, String descricao) {
        Response resp = given()
                .when()
                .queryParam("descricao", descricao)
                .get(Ambiente.desenvolvimento + Endpoint.produtos)
        .then()
                .statusCode(statusCode)
                .extract().response();

        Gson gson = new Gson();
        return gson.fromJson(resp.asString(), ProdutosDTO.class);
    }
}
