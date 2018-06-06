package Dao;

import Conexao.Conexao;
import Model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Leandro Pereira Sampaio
 */
public class UsuarioDAO {

    private Connection conexao;
    private PreparedStatement stmt;

    public UsuarioDAO() {
        conexao = Conexao.getConnection();
        stmt = null;
    }

    public void create(Usuario usuario) {
        try {
            stmt = conexao.prepareStatement("insert into usuario (cpf, nome, senha) "
                    + "values (?,?,?)");
            stmt.setString(1, usuario.getCpf());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getSenha());

            stmt.execute();

            JOptionPane.showMessageDialog(null, "Salvo Com Sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO ao Salvar!" + ex);
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.closeConnection(conexao, stmt);
        }
    }

    public Usuario verficarAcesso(String codigo, String senha) {

        try {
            ResultSet rs;
            stmt = conexao.prepareStatement("select * from usuario where codigo = ? and senha = ?");
            stmt.setString(1, codigo);
            stmt.setString(2, senha);

            rs = stmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Verificado Com Sucesso!");
                Usuario usuario = new Usuario();
                usuario.setCodigo(codigo);
                usuario.setCpf(rs.getString("cpf"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSaldo(rs.getFloat("saldo"));
                return usuario;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO ao Verificar!" + ex);
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.closeConnection(conexao, stmt);
        }
        return null;
    }
}
