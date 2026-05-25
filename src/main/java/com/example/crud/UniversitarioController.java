package com.example.crud;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

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
    public Universitario inserirNovoAluno(@RequestParam String nome, @RequestParam String matricula, @RequestParam String sexo) {
        Universitario universitario = gerenciador.novoAluno(nome, matricula, sexo);
        return universitario;
    }
}
