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
    public Universitario inserirNovoAluno(@RequestParam String nome, @RequestParam String matricula, @RequestParam String sexo, @RequestParam String idade) {
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
    public Universitario buscarAluno(@RequestParam String matricula) {
        return gerenciador.buscarAlunoPorMatricula(matricula);
    }

    @DeleteMapping("/remover-aluno")
    @Operation(summary = "Remover um aluno", description = "Remover um aluno do banco de dados")
    public String removerUmAluno(@RequestParam String matricula) {
        gerenciador.removerAluno(matricula);
        return "Aluno deletado";
    }
}
