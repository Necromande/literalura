package com.literalura.livros;

import com.literalura.livros.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LivrosApplication implements CommandLineRunner {
	private final Principal principal;

	public LivrosApplication(Principal principal) {
		this.principal = principal;
	}

	public static void main(String[] args) {
		SpringApplication.run(LivrosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		principal.exibirMenu();
	}
}
