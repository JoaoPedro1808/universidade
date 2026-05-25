package com.example.crud;

import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class Gerenciador {
    String jdbcurl = "jdbc:mysql://localhost:3306/universidade";
    String user = "root";
    String password = "admin";

    public Connection conexao() throws SQLException {
        return DriverManager.getConnection(jdbcurl, user, password);
    }

    public Universitario novoAluno(String nomeAluno, String matriculaAluno, String sexoAluno) {
        try (Connection connection = conexao();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO universitarios (matricula, nome, sexo) VALUES (?, ?, ?)")) {

            ps.setString(1, matriculaAluno);
            ps.setString(2, nomeAluno);
            ps.setString(3, sexoAluno);
            ps.executeUpdate();

            return new Universitario(nomeAluno, matriculaAluno, sexoAluno);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar aluno no banco de dados", e);
        }
    }

    public List<Universitario> listarAlunos() {
        List<Universitario> listaUniversitarios =new ArrayList<>();

        try (Connection connection = conexao();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM universitarios");
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                listaUniversitarios.add(new Universitario(rs.getString("nome"), rs.getString("matricula"), rs.getString("sexo")));
            }

            return listaUniversitarios;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar os alunos", e);
        }
    }
}
