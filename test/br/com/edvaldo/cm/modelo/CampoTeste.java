package br.com.edvaldo.cm.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.edvaldo.cm.excecao.ExplosaoException;

public class CampoTeste {
	
	//Área de teste

	private Campo campo = new Campo(3, 3);

	@BeforeEach
	void iniciarCampo() {
		campo = new Campo(3, 3);
	}

	@Test
	void testeVizinhoDistancial1Esquerda() {
		Campo vizinho = new Campo(3, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);

	}

	@Test
	void testeVizinhoRealDistancial1Direita() {
		Campo vizinho = new Campo(3, 4);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);

	}

	@Test
	void testeVizinhoDistancial1Emcima() {
		Campo vizinho = new Campo(2, 3);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);

	}

	@Test
	void testeVizinhoDistancial1Embaixo() {
		Campo vizinho = new Campo(4, 3);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);

	}

	@Test
	void testeVizinhoDistancial2() {
		Campo vizinho = new Campo(2, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);

	}

	@Test
	void testeNaoVizinhoD() {
		Campo vizinho = new Campo(1, 1);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado);

	}
	

	@Test
	void testeVizinhoNulo() {
		Campo vizinho = new Campo(0, 0);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado);

	}

	@Test
	void testeVizinho3() {
		Campo vizinho = new Campo(6, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado);

	}

	@Test
	void testeValorPadraoAtributoMarcado() {
		assertFalse(campo.isMarcado());
	}

	@Test
	void testealternarMarcacao() {
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());
	}

	@Test
	void testealternarMarcacaoDuasChamdas() {
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		assertFalse(campo.isMarcado());
	}

	@Test
	void testeAbrirNaoMinadoNaoMarcado() {
		assertTrue(campo.abrir());
	}

	@Test
	void testeAbrirNaoMinadoMarcado() {
		campo.alternarMarcacao();
		assertFalse(campo.abrir());
	}

	@Test
	void testeAbrirMinadoMarcado() {
		campo.alternarMarcacao();
		campo.minar();
		assertFalse(campo.abrir());
	}
	

	@Test
	void testeAbrirMinadoNaoMarcado() {
		campo.minar();
		assertThrows(ExplosaoException.class, () -> {
			campo.abrir();
			});
	}

	@Test
	void testeAbrirComVizinhos1() {

		Campo campo11 = new Campo(2, 2);
		Campo campo22 = new Campo(2, 3);

		campo11.adicionarVizinho(campo22);
		campo.adicionarVizinho(campo11);
		campo.abrir();

		assertTrue(campo22.isAberto() && campo11.isAberto());
	}

	@Test
	void testeAbrirComVizinhos2() {

		Campo campo11 = new Campo(1, 1);
		Campo campo12 = new Campo(1, 1);
		campo12.minar();

		Campo campo22 = new Campo(2, 2);
		campo22.adicionarVizinho(campo11);
		campo22.adicionarVizinho(campo12);

		campo.adicionarVizinho(campo22);
		campo.abrir();

		assertTrue(campo22.isAberto() && campo11.isFechado());
	}

}
