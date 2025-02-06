package org.serverest.test;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.serverest.controller.Usuario;

public class TestExample {

    @Test
    public void listarUsuarios() {
        Usuario.listar(HttpStatus.SC_OK);
    }
}
