package controller;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.bo.MoedaBO;
import model.vo.MoedaVO;

@Path("/moeda")
public class MoedaController {

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public MoedaVO cadastraMoedaController(@FormDataParam("fileInputStream") InputStream fileInputStream,
                                           @FormDataParam("fileMetaData") FormDataContentDisposition fileMetaData,
                                           @FormDataParam("moedaInputStream") InputStream moedaInputStream) throws Exception {
        MoedaBO moedaBO = new MoedaBO();
        return moedaBO.registrarMoedaBO(moedaInputStream, fileInputStream, fileMetaData);



    }

//    @GET
//    @Path("/dashboard")
//    @Consumes()
//    public Response exibirDashboard(@PathParam("idUsuario") int idUsuario){
//
//
//    }

    @PUT
    @Path("/atualizar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean atualizarMoedaController(@FormDataParam("fileInputStream") InputStream fileInputStream,
                                            @FormDataParam("fileMetaData") FormDataContentDisposition fileMetaData,
                                            @FormDataParam("moedaInputStream") InputStream moedaInputStream) throws Exception {
        MoedaBO moedaBO = new MoedaBO();
        return moedaBO.editarMoedaBO(moedaInputStream, fileInputStream, fileMetaData);
    }


    @GET
    @Path("/listar/{idUsuario}")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public Response listarMoedasController(@PathParam("idUsuario") int idUsuario) {
        MoedaBO moedaBO = new MoedaBO();
        return moedaBO.listarMoedaBO(idUsuario);
    }

    @GET
    @Path("/dashboard/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarDashboardInfos(@PathParam("idUsuario") int idUsuario) {
        MoedaBO moedaBO = new MoedaBO();
        return moedaBO.listarInfosDashBO(idUsuario);
    }


    @DELETE
    @Path("/excluir")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean excluirUsuarioController (MoedaVO moedaVO) {
        MoedaBO MoedaBO = new MoedaBO();
        return MoedaBO.deletarMoedaBO(moedaVO);
    }

}
