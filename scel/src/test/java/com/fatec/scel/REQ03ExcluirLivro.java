package com.fatec.scel;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.fatec.scel.model.Livro;
import com.fatec.scel.model.LivroRepository;
@RunWith(SpringRunner.class)
@SpringBootTest
class REQ03ExcluirLivro {
	@Autowired
	LivroRepository repository;
	static Livro livro;
	@Test
	public void CT01ExcluirLivroComSucesso() {
	// dado que o livro esta cadastrado
	repository.deleteAll();
	Livro livro = new Livro("3333", "Teste de Software", "Delamaro");
	repository.save(livro);
	//quando o usuario exclui o livro
	Livro ro = repository.findByIsbn("3333");
	repository.deleteById(ro.getId());
	// entao o livro é deletado da base
	assertThat(repository.findByIsbn("3333")).isEqualTo(null);
	}
}
