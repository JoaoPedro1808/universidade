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

    public Universitario novoAluno(String nomeAluno, String matriculaAluno, String sexoAluno, String idadeAluno) {
        try (Connection connection = conexao();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO universitarios (matricula, nome, sexo, idade) VALUES (?, ?, ?, ?)")) {

            ps.setString(1, matriculaAluno);
            ps.setString(2, nomeAluno);
            ps.setString(3, sexoAluno);
            ps.setString(4, idadeAluno);
            ps.executeUpdate();

            return new Universitario(nomeAluno, matriculaAluno, sexoAluno, idadeAluno);

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
                listaUniversitarios.add(new Universitario(rs.getString("nome"), rs.getString("matricula"), rs.getString("sexo"), rs.getString("idade")));
            }

            return listaUniversitarios;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar os alunos", e);
        }
    }

    public void removerAluno(String matriculaAluno) {
        try (Connection connection = conexao();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM universitarios WHERE matricula = ?")) {

            ps.setString(1, matriculaAluno);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover o aluno", e);
        }
    }

    public Universitario buscarAlunoPorMatricula(String matriculaAluno) {
        try (Connection connection = conexao();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM universitarios WHERE matricula = ?")) {

            ps.setString(1, matriculaAluno);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nome");
                    String matricula = rs.getString("matricula");
                    String sexo = rs.getString("sexo");
                    String idade = rs.getString("idade");

                    return new Universitario(nome, matricula, sexo, idade);
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao localizar o aluno", e);
        }
    }
}
