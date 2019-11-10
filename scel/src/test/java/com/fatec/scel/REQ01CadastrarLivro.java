package com.fatec.scel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import com.fatec.scel.model.Livro;
import com.fatec.scel.model.LivroRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class REQ01CadastrarLivro {
	@Autowired
	LivroRepository repository;
	private Validator validator;
	private ValidatorFactory validatorFactory;

	/**
	 * Verificar o comportamento da classe LivroRepository
	 */
	@Test
	public void CT01CadastrarLivroComSucesso_valida_comportamento() {
		// dado que o isbn nao esta cadastrado
		repository.deleteAll();
		// quando o usurio inclui as informacoes obrigatorias e confirma a operacao
		Livro livro = new Livro("3333", "Teste de Software", "Delamaro");
		repository.save(livro);
		// entao a quantidade de livros no repositorio será 1
		assertEquals(1, repository.count());

	}
	@Test
	public void CT02CadastrarLivroComSucesso() {

		// dado que o isbn nao esta cadastrado
		repository.deleteAll();
		// quando o usurio inclui as informacoes obrigatorias e confirma a operacao
		Livro livro1 = new Livro("3333", "Teste de Software", "Delamaro");
		repository.save(livro1);
		// entao o estado do objeto obtido é igual ao objeto cadastrado 
		Livro livro2 = repository.findByIsbn("3333");
		assertTrue (livro1.equals(livro2));
	}
	@Test
	public void CT03CadastrarLivroComSucesso_verificacao_de_violacao() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		// dado que as informações obrigatorias foram incluidas
		Livro livro = new Livro("3333", "Teste de Software", "Delamaro");
		// quando a verificacao de violacao for acionada
		Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
		// entao nenhuma violacao sera detectada
		assertTrue(violations.isEmpty());
	}

	@Test
	public void CT04CadastrarLivroCom_TituloInvalido_verificacao_de_violacao() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		// dado que o titulo do livro esta invalido
		Livro livro = new Livro("3333", "", "Delamaro");
		// quando a verificacao de violacao for acionada
		Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
		// entao a mensagem o titulo deve ser preenchido será enviada
		assertEquals("O titulo deve ser preenchido", violations.iterator().next().getMessage());
	}

}