package org.serverest.factory;

import com.github.javafaker.Faker;
import org.serverest.model.ProdutoDTO;

public class ProdutoFactory {

    public static ProdutoDTO criarComQuantidade(int quantidadeInicial) {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        Faker faker = new Faker();
        String nome = faker.commerce().productName();
        produtoDTO.setNome(nome);
        produtoDTO.setPreco(10);
        produtoDTO.setDescricao("Produto");
        produtoDTO.setQuantidade(quantidadeInicial);
        return produtoDTO;
    }
}