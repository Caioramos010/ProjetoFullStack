package controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.bo.UsuarioBO;
import model.vo.UsuarioVO;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.InputStream;

@Path("/usuario")
public class UsuarioController {

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarUsuarioController(UsuarioVO usuarioVO) {

        UsuarioBO usuarioBO = new UsuarioBO();
        UsuarioVO usuarioCadastrado = usuarioBO.cadastrarUsuarioBO(usuarioVO);
        if (usuarioCadastrado == null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Usuário já existe.") // Apenas a mensagem simples
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD")
                    .header("Access-Control-Allow-Headers", "Content-Type")
                    .build();
        }
        return Response.ok(usuarioCadastrado)
                .header("Access-Control-Allow-Origin", "*") // Permite todas as origens
                .header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD")
                .header("Access-Control-Allow-Headers", "Content-Type")
                .build();
    }

    @POST
    @Path("/logar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UsuarioVO loginUsuarioController(UsuarioVO usuarioVO) {
        UsuarioBO usuarioBO = new UsuarioBO();
        return usuarioBO.logarUsuarioBO(usuarioVO);
    }

    @PUT
    @Path("/atualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean atualizarUsuarioController(UsuarioVO usuarioVO) {
        UsuarioBO usuarioBO = new UsuarioBO();
        return usuarioBO.editarUsuarioBO(usuarioVO);
    }

    @DELETE
    @Path("/excluir")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean excluirUsuarioController(UsuarioVO usuarioVO) {
        UsuarioBO usuarioBO = new UsuarioBO();
        return usuarioBO.deletarUsuarioBO(usuarioVO);
    }
}
