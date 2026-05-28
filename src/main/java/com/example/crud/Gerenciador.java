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

    public Universitario novoAluno(String nomeAluno, int matriculaAluno, String sexoAluno, int idadeAluno, float notaAluno) {
        try (Connection connection = conexao();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO universitarios (matricula, nome, sexo, idade, nota) VALUES (?, ?, ?, ?, ?)")) {

            ps.setInt(1, matriculaAluno);
            ps.setString(2, nomeAluno);
            ps.setString(3, sexoAluno);
            ps.setInt(4, idadeAluno);
            ps.setFloat(5, notaAluno);
            ps.executeUpdate();

            return new Universitario(nomeAluno, matriculaAluno, sexoAluno, idadeAluno, notaAluno);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar aluno no banco de dados", e);
        }
    }

    public List<Universitario> listarAlunos() {
        List<Universitario> listaUniversitarios = new ArrayList<>();

        try (Connection connection = conexao();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM universitarios");
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                listaUniversitarios.add(new Universitario(rs.getString("nome"), rs.getInt("matricula"), rs.getString("sexo"), rs.getInt("idade"), rs.getFloat("nota")));
            }

            return listaUniversitarios;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar os alunos", e);
        }
    }

    public void removerAluno(int matriculaAluno) {
        try (Connection connection = conexao();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM universitarios WHERE matricula = ?")) {

            ps.setInt(1, matriculaAluno);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover o aluno", e);
        }
    }

    public Universitario buscarAlunoPorMatricula(int matriculaAluno) {
        try (Connection connection = conexao();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM universitarios WHERE matricula = ?")) {

            ps.setInt(1, matriculaAluno);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nome");
                    int matricula = rs.getInt("matricula");
                    String sexo = rs.getString("sexo");
                    int idade = rs.getInt("idade");
                    float nota = rs.getFloat("nota");

                    return new Universitario(nome, matricula, sexo, idade, nota);
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao localizar o aluno", e);
        }
    }

    public void atualizarDadosAluno(int matriculaAluno, float notaAluno) {
        try (Connection connection = conexao();
            PreparedStatement ps = connection.prepareStatement("UPDATE universitarios SET nota = ? WHERE matricula = ?")) {

            ps.setInt(1, matriculaAluno);
            ps.setFloat(2, notaAluno);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar os dados do aluno", e);
        }
    }
}
