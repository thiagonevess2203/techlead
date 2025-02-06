package org.serverest.test;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.serverest.controller.Produto;
import org.serverest.controller.Usuario;
import org.serverest.factory.ProdutoFactory;
import org.serverest.factory.UsuarioFactory;
import org.serverest.model.*;
import org.serverest.util.Mensagem;

public class TestProduto {
    private String authToken = "";
    private UsuarioDTO usuarioDTO;
    private final boolean IS_ADMIN = true;

    public void init(boolean isAdmin) {
        usuarioDTO = isAdmin ? UsuarioFactory.criarAdmin() : UsuarioFactory.criarCliente();
        usuarioDTO.setId(Usuario.cadastrar(usuarioDTO, HttpStatus.SC_CREATED, Mensagem.cadastroSucesso));
        authToken = Usuario.autenticar(usuarioDTO, HttpStatus.SC_OK, Mensagem.loginSucesso);
    }

    public void cleanUp() {
        Usuario.excluir(usuarioDTO, HttpStatus.SC_OK, Mensagem.exclusaoSucesso);
    }

    @Test
    public void criarProdutoSucesso() {
        init(IS_ADMIN);

        ProdutoDTO produtoDTO = ProdutoFactory.criarComQuantidade(5);
        produtoDTO.setId(Produto.cadastrar(produtoDTO, authToken, HttpStatus.SC_CREATED, Mensagem.cadastroSucesso));

        Produto.excluir(produtoDTO, authToken, HttpStatus.SC_OK, Mensagem.exclusaoSucesso);

        cleanUp();
    }

    @Test
    public void criarProdutoJaExistente() {
        init(IS_ADMIN);

        ProdutoDTO produtoDTO = ProdutoFactory.criarComQuantidade(5);
        produtoDTO.setId(Produto.cadastrar(produtoDTO, authToken, HttpStatus.SC_CREATED, Mensagem.cadastroSucesso));
        Produto.cadastrar(produtoDTO, authToken, HttpStatus.SC_BAD_REQUEST, Mensagem.registroDuplicado);

        Produto.excluir(produtoDTO, authToken, HttpStatus.SC_OK, Mensagem.exclusaoSucesso);

        cleanUp();
    }

    @Test
    public void excluirProdutoSucesso() {
        init(IS_ADMIN);

        ProdutoDTO produtoDTO = ProdutoFactory.criarComQuantidade(5);
        produtoDTO.setId(Produto.cadastrar(produtoDTO, authToken, HttpStatus.SC_CREATED, Mensagem.cadastroSucesso));
        Produto.cadastrar(produtoDTO, authToken, HttpStatus.SC_BAD_REQUEST, Mensagem.registroDuplicado);

        Produto.excluir(produtoDTO, authToken, HttpStatus.SC_OK, Mensagem.exclusaoSucesso);

        cleanUp();
    }

    @Test
    public void criarProdutoTokenAusente() {
        init(IS_ADMIN);

        ProdutoDTO produtoDTO = ProdutoFactory.criarComQuantidade(5);
        produtoDTO.setId(Produto.cadastrar(produtoDTO, "", HttpStatus.SC_UNAUTHORIZED, Mensagem.tokenAusente));

        cleanUp();
    }

    @Test
    public void criarProdutoUsuarioComum() {
        init(!IS_ADMIN);

        ProdutoDTO produtoDTO = ProdutoFactory.criarComQuantidade(5);
        produtoDTO.setId(Produto.cadastrar(produtoDTO, authToken, HttpStatus.SC_FORBIDDEN, Mensagem.rotaExclusivaAdmin));

        cleanUp();
    }

    @Test
    public void buscarProdutoPorIdEncontrado() {
        init(IS_ADMIN);

        var p1 = ProdutoFactory.criarComQuantidade(5);
        p1.setId(Produto.cadastrar(p1, authToken, HttpStatus.SC_CREATED, Mensagem.cadastroSucesso));

        Produto.buscarPorId(p1, HttpStatus.SC_OK);

        Produto.excluir(p1, authToken, HttpStatus.SC_OK, Mensagem.exclusaoSucesso);
    }

    @Test
    public void listarProdutos() {
        init(IS_ADMIN);

        var p1 = ProdutoFactory.criarComQuantidade(5);
        p1.setDescricao("XPTO1");
        var p2 = ProdutoFactory.criarComQuantidade(5);
        p2.setDescricao("XPTO2");

        try {
            p1.setId(Produto.cadastrar(p1, authToken, HttpStatus.SC_CREATED, Mensagem.cadastroSucesso));
            p2.setId(Produto.cadastrar(p2, authToken, HttpStatus.SC_CREATED, Mensagem.cadastroSucesso));

            var produtos = Produto.buscarPorDescricao(HttpStatus.SC_OK, "XPTO");
            System.out.println(produtos.getQuantidade());
            Assert.assertEquals(2, produtos.getQuantidade());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            Produto.excluir(p1, authToken, HttpStatus.SC_OK, Mensagem.exclusaoSucesso);
            Produto.excluir(p2, authToken, HttpStatus.SC_OK, Mensagem.exclusaoSucesso);
        }

        cleanUp();
    }
}
