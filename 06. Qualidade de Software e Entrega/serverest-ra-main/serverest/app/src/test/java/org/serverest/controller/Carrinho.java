package org.serverest.controller;

import io.restassured.http.ContentType;
import org.serverest.util.Ambiente;
import org.serverest.util.Endpoint;
import static org.hamcrest.CoreMatchers.is;
import static io.restassured.RestAssured.given;
public class Carrinho {
    public static void cadastrar(String produtoId, Integer quantidade, String usuarioToken, Integer statusCode, String mensagem) {
        given()
                .header("authorization", usuarioToken)
                .body("{\n" +
                        "  \"produtos\": [\n" +
                        "    {\n" +
                        "      \"idProduto\": \"" + produtoId + "\",\n" +
                        "      \"quantidade\": " + quantidade + "\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .contentType(ContentType.JSON)
        .when()
                .post(Ambiente.localhost + Endpoint.carrinhos)
        .then()
                .statusCode(statusCode)
                .body("message", is(mensagem));
    }

    public static void cancelarCompra(String usuarioToken, Integer statusCode, String mensagem) {
        given()
                .header("authorization", usuarioToken)
        .when()
                .delete(Ambiente.localhost + Endpoint.cancelarCompra)
        .then()
                .statusCode(statusCode)
                .body("message", is(mensagem));
    }
}