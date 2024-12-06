package model.bo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.Response;
import model.dao.MoedaDAO;
import model.vo.MoedaVO;


public class MoedaBO {

    private byte[] converterByteParaArray (InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int read = 0;
        byte[] dados = new byte[1024];
        while ((read = inputStream.read(dados, 0, dados.length)) != -1) {
            buffer.write(dados, 0, read);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    public MoedaVO registrarMoedaBO(InputStream moedaInputStream, InputStream fileInputStream, FormDataContentDisposition fileMetaData) throws Exception {
        MoedaDAO moedaDAO = new MoedaDAO();
        MoedaVO moedaVO = null;
        try {
            byte[] arquivo = this.converterByteParaArray(fileInputStream);
            String moedaJSON = new String(this.converterByteParaArray(moedaInputStream), StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();

            moedaVO = objectMapper.readValue(moedaJSON, MoedaVO.class);
            moedaVO.setImagem(arquivo);

            if (moedaDAO.verificarCadastroMoedaDAO(moedaVO)) {
                System.out.println("Moeda já existe no banco de dados.");
            } else {
                moedaVO = moedaDAO.cadastrarMoedaDAO(moedaVO);
            }
        } catch (FileNotFoundException erro) {
            System.out.println("Erro!" + erro);
        } catch (IOException erro) {
            erro.printStackTrace();
        }
        return moedaVO;
    }
    //
    public boolean editarMoedaBO(InputStream moedaInputStream, InputStream fileInputStream, FormDataContentDisposition fileMetaData) {
        boolean resultado = false;
        MoedaDAO moedaDAO = new MoedaDAO();
        MoedaVO  moedaVO = null;
        try{
            byte[] arquivo = null;
            if (fileInputStream != null) {
                arquivo = this.converterByteParaArray(fileInputStream);
            }
            String moedaJSON = new String(this.converterByteParaArray(moedaInputStream), StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            moedaVO = objectMapper.readValue(moedaJSON, MoedaVO.class);
            if (arquivo.length > 0) {
                moedaVO.setImagem(arquivo);
            }

            if (moedaDAO.verificarCadastroMoedaDAO(moedaVO)) {
                resultado = moedaDAO.editarMoedaDAO(moedaVO);
            } else {
                System.out.println("Essa moeda não existe no Banco de Dados!");
            }
        } catch (FileNotFoundException erro) {
            System.out.println(erro);
        } catch (IOException erro) {
            erro.printStackTrace();
        }
        return resultado;
    }

    public boolean deletarMoedaBO(MoedaVO moedaVO) {
        boolean resultado = false;
        MoedaDAO moedaDAO = new MoedaDAO();

        if (moedaDAO.verificarCadastroMoedaDAO(moedaVO)) {
            resultado = moedaDAO.excluirMoedaDAO(moedaVO);
            System.out.println("moeda deletada com sucesso!");
        } else {
            System.out.println("Falha na tentativa de deletar a moeda!");
        }
        return resultado;
    }
    public Response listarInfosDashBO(int idUsuario){
        MoedaDAO moedaDAO = new MoedaDAO();
        ArrayList<MoedaVO> listaMoedasVO = moedaDAO.consultarMoedaDAO(idUsuario);
        double somaMoedas = 0;
        int quanttMoedas = 0;

        if (listaMoedasVO.isEmpty()) {
            System.out.println("Não há nenhuma moeda na lista!");
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        try {
            for(MoedaVO moedaVO :listaMoedasVO){
                somaMoedas += moedaVO.getValor();
                quanttMoedas++;
            }
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("somaMoedas", somaMoedas);
            jsonMap.put("quanttMoedas", quanttMoedas);
            ObjectMapper objectMapper = new ObjectMapper();
            String dashboardJSON = objectMapper.writeValueAsString(jsonMap);
            System.out.println(dashboardJSON);
            return Response.ok(dashboardJSON).build();

        }catch (Exception e){
            e.printStackTrace(); // Log do erro para depuração
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao processar resposta multipart.")
                    .build();
        }
    }

    public Response listarMoedaBO(int idUsuario) {
        MoedaDAO moedaDAO = new MoedaDAO();
        ArrayList<MoedaVO> listaMoedasVO = moedaDAO.consultarMoedaDAO(idUsuario);

        if (listaMoedasVO.isEmpty()) {
            System.out.println("Não há nenhuma moeda na lista!");
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        MultiPart multiPart = new FormDataMultiPart();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // Configura o ObjectMapper para processar datas, etc.

        try {
            for (MoedaVO moedaVO : listaMoedasVO) {
                String moedaJSON = objectMapper.writeValueAsString(moedaVO);
                System.out.println("Moeda JSON gerada: " + moedaJSON); // Log para depuração

                // Adiciona o JSON ao MultiPart
                multiPart.bodyPart(new StreamDataBodyPart(
                        "moedaVO",
                        new ByteArrayInputStream(moedaJSON.getBytes()),
                        moedaVO.getIdMoeda() + "-moeda.json"
                ));

            }

            // Retorna o MultiPart
            return Response.ok(multiPart).build();
        } catch (Exception e) {
            e.printStackTrace(); // Log do erro para depuração
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao processar resposta multipart.")
                    .build();

        }


    }



}
