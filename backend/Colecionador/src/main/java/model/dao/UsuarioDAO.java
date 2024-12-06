package model.dao;

import model.vo.UsuarioVO;

import java.sql.*;

public class UsuarioDAO {
    //Verifica se foi cadastrado pelo login
    public boolean verificarCadastroUsuarioBancoDAO(UsuarioVO usuarioVO){
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        ResultSet result = null;
        boolean retorno = false;
        String query = "SELECT idusuario FROM usuario WHERE login = '" + usuarioVO.getLogin() + "' ";
        try{
            result = stmt.executeQuery(query);
            if (result.next()){
                retorno = true;
            }

        }catch (SQLException erro){
            System.out.println("Erro ao executar a query do método");
            System.out.println("Erro " + erro.getMessage());
        }finally {
            Banco.closeResultSet(result);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return retorno;
    }
    //Cadastra o usuario

    public UsuarioVO CadastrarUsuarioDAO(UsuarioVO usuarioVO){
        String query = "INSERT INTO usuario (nome, email, login, senha, datacadastro) VALUES (?, ?, ?, ?, ?)";
        Connection conn = Banco.getConnection();
        PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
        ResultSet result = null;
        try {
            pstmt.setString(1, usuarioVO.getNome());
            pstmt.setString(2, usuarioVO.getEmail());
            pstmt.setString(3, usuarioVO.getLogin());
            pstmt.setString(4, usuarioVO.getSenha());
            pstmt.setDate(5, Date.valueOf(java.time.LocalDate.now()));
            pstmt.execute();
            result = pstmt.getGeneratedKeys();
            if (result.next()){
                usuarioVO.setIdUsuario(result.getInt(1));
            }

        }catch (SQLException erro){
            System.out.println("Erro ao executar a query do método CadastrarUsuarioDAO");
            System.out.println("Erro " + erro.getMessage());
        }finally {
            Banco.closeResultSet(result);
            Banco.closePreparedStatement(pstmt);
            Banco.closeConnection(conn);
        }
        return usuarioVO;
    }

    //Verifica se foi cadastrado pelo ID

    public boolean verificarCadastroUsuarioIDBancoDAO(UsuarioVO usuarioVO){
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        ResultSet result = null;
        boolean retorno = false;
        String query = "SELECT idusuario FROM usuario WHERE idUsuario = '" + usuarioVO.getIdUsuario() + "' ";
        try{
            result = stmt.executeQuery(query);
            if (result.next()){
                retorno = true;
            }

        }catch (SQLException erro){
            System.out.println("Erro ao executar a query do método");
            System.out.println("Erro " + erro.getMessage());
        }finally {
            Banco.closeResultSet(result);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return retorno;
    }
    //Atualiza o usuario
    public boolean editarUsuarioDAO(UsuarioVO usuarioVO) {
        String query = "UPDATE usuario SET nome = ?, email = ?, login = ?, senha = ? WHERE idusuario = ? "  ;
        Connection conn = Banco.getConnection();
        PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
        boolean retorno = false;
        try {
            pstmt.setString(1, usuarioVO.getNome());
            pstmt.setString(2, usuarioVO.getEmail());
            pstmt.setString(3, usuarioVO.getLogin());
            pstmt.setString(4, usuarioVO.getSenha());
            pstmt.setInt(5, usuarioVO.getIdUsuario());
            int linhasAfetadas = pstmt.executeUpdate();
            if (linhasAfetadas > 0) {
                retorno = true;
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao executar a query do método, editarUsuarioDAO");
            System.out.println("Erro " + erro.getMessage());
        } finally {
            Banco.closeStatement(pstmt);
            Banco.closeConnection(conn);

        }
        return retorno;
    }

    //Exclui o usuario
    public boolean excluirUsuarioDAO(UsuarioVO usuarioVO) {
        String query = "DELETE FROM usuario WHERE idusuario = ?";

        Connection conn = Banco.getConnection();
        PreparedStatement pstmt = null;
        boolean retorno = false;
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, usuarioVO.getIdUsuario()); // Passa o ID do usuário corretamente
            int linhasAfetadas = pstmt.executeUpdate();
            if (linhasAfetadas > 0) {
                retorno = true;
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao executar a query do método excluirUsuarioDAO");
            System.out.println("Erro: " + erro.getMessage());
        } finally {
            Banco.closePreparedStatement(pstmt);
            Banco.closeConnection(conn);
        }
        return retorno;
    }

    //Login Usuario
    public UsuarioVO logarUsuarioDAO(UsuarioVO usuarioVO) {
        String query = "SELECT idusuario, nome, email, login, senha " + "FROM usuario " + "WHERE login = '" + usuarioVO.getLogin() + "' AND senha = '" + usuarioVO.getSenha() + "'";;
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        ResultSet resultado = null;
        UsuarioVO user = new UsuarioVO();
        try{
            resultado = stmt.executeQuery(query);
            if(resultado.next()){
                user = new UsuarioVO();
                user.setIdUsuario(Integer.parseInt(resultado.getString(1)));
                user.setNome(resultado.getString(2));
                user.setEmail(resultado.getString(3));
                user.setLogin(resultado.getString(4));
                user.setSenha(resultado.getString(5));
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao executar a query do método, logarUsuarioDAO");
            System.out.println("Erro " + erro.getMessage());
        } finally {
            Banco.closeResultSet(resultado);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return user;
    }
}
