package org.serverest.test;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.serverest.controller.Produto;
import org.serverest.controller.Usuario;
import org.serverest.factory.ProdutoFactory;
import org.serverest.factory.UsuarioFactory;
import org.serverest.model.ProdutoDTO;
import org.serverest.model.UsuarioDTO;
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

        //Produto.buscarPorId(produtoDTO, HttpStatus.SC_OK);
        //String novaDescricao = "XPTO";
        //produtoDTO.setDescricao(novaDescricao);
        //Produto.atualizar(produtoDTO, authToken, HttpStatus.SC_OK, Mensagem.atualizadoSucesso);

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
}
