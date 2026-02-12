package com.challenge.Literatura;

import com.challenge.Literatura.Principal.Principal;
import com.challenge.Literatura.Repocitorio.IAutorRepocitorio;
import com.challenge.Literatura.Repocitorio.ILibrosRepocitorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {
	@Autowired
	private ILibrosRepocitorio libroRepocitorio;
	@Autowired
	private IAutorRepocitorio autorRepocitorio;
	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepocitorio, autorRepocitorio);
		principal.displayMenu();
	}
}
