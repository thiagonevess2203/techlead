package org.serverest.test;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.serverest.controller.Usuario;
import org.serverest.factory.UsuarioFactory;
import org.serverest.model.UsuarioDTO;
import org.serverest.util.Mensagem;

public class Pratica01 {
    @Test
    public void pratica001() {
        UsuarioDTO usuarioDTO = UsuarioFactory.criarAdmin();
        usuarioDTO.setId(Usuario.cadastrar(usuarioDTO, HttpStatus.SC_CREATED, Mensagem.cadastroSucesso));

        Usuario.buscarPorId(usuarioDTO, HttpStatus.SC_OK);
        Usuario.excluir(usuarioDTO, HttpStatus.SC_OK, Mensagem.exclusaoSucesso);
    }
}
