package model.bo;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.dao.UsuarioDAO;
import model.vo.UsuarioVO;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class UsuarioBO {
    //Cadastrar Usuario BO
    public UsuarioVO cadastrarUsuarioBO(UsuarioVO usuarioVO){
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if (usuarioDAO.verificarCadastroUsuarioBancoDAO(usuarioVO)){
            System.out.println("Usuario Já cadastrado");
            usuarioVO = null;

        } else {
            usuarioVO = usuarioDAO.CadastrarUsuarioDAO(usuarioVO);
        }
        return usuarioVO;
    }
    public UsuarioVO logarUsuarioBO(UsuarioVO usuarioVO) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        return usuarioDAO.logarUsuarioDAO(usuarioVO);
    }
    public boolean editarUsuarioBO(UsuarioVO usuarioVO) {
        boolean resultado = false;
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        if (usuarioDAO.verificarCadastroUsuarioBancoDAO(usuarioVO)) {
            resultado = usuarioDAO.editarUsuarioDAO(usuarioVO);
        } else {
            System.out.println("Usuário não foi encontrado no banco de dados");
        }
        return resultado;
    }

    public boolean deletarUsuarioBO(UsuarioVO usuarioVO) {
        boolean resultado = false;
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        // Verifica se o usuário existe antes de deletar
        if (usuarioDAO.verificarCadastroUsuarioBancoDAO(usuarioVO)) {
            resultado = usuarioDAO.excluirUsuarioDAO(usuarioVO);
            System.out.println("Usuário deletado com sucesso!");
        } else {
            System.out.println("Falha na tentativa de deletar o usuário!");
        }
        return resultado;
    }

}
