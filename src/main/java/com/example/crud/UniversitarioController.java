package com.example.crud;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/universitarios")
@Tag(name = "Gerenciador de alunos", description = "Gerenciador dos alunos da faculdade")
public class UniversitarioController {

    private final Gerenciador gerenciador;

    public UniversitarioController(Gerenciador gerenciador) {
        this.gerenciador = gerenciador;
    }

    @PostMapping("/novo-aluno")
    @Operation(summary = "Inserir novo aluno", description = "Inserir um novo aluno do banco de dados")
    public Universitario inserirNovoAluno(@RequestParam String nome, @RequestParam int matricula, @RequestParam String sexo, @RequestParam int idade) {
        Universitario universitario = gerenciador.novoAluno(nome, matricula, sexo, idade);
        return universitario;
    }

    @GetMapping("/lista-alunos")
    @Operation(summary = "Alunos da universidade", description = "Lista de todos os alunos da universidade")
    public List<Universitario> listarAlunos() {
        return gerenciador.listarAlunos();
    }

    @GetMapping("/buscar-aluno")
    @Operation(summary = "Buscar por aluno", description = "Buscar por um aluno com a matricula")
    public Universitario buscarAluno(@RequestParam int matricula) {
        return gerenciador.buscarAlunoPorMatricula(matricula);
    }

    @DeleteMapping("/remover-aluno")
    @Operation(summary = "Remover um aluno", description = "Remover um aluno do banco de dados")
    public String removerUmAluno(@RequestParam int matricula) {
        gerenciador.removerAluno(matricula);
        return "Aluno deletado";
    }

    @PutMapping("/atualizar-aluno")
    @Operation(summary = "Atualizar aluno", description = "Atualizar os dados do aluno")
    public void atualizarAluno(@RequestParam int matricula, @RequestParam String nome, @RequestParam String sexo, @RequestParam int idade) {
        gerenciador.atualizarDadosAluno(matricula, nome, sexo, idade);
    }
}
