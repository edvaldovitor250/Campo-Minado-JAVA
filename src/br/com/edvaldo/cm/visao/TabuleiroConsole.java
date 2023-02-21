package br.com.edvaldo.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.edvaldo.cm.excecao.ExplosaoException;
import br.com.edvaldo.cm.excecao.SairExption;
import br.com.edvaldo.cm.modelo.Tabuleiro;

public class TabuleiroConsole {

	private Tabuleiro tabuleiro;
	private Scanner entrada = new Scanner(System.in);

	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;

		executarJogo();
	}

	private void executarJogo() {

		try {
			boolean continuar = true;

			while (continuar) {
				cicloDoJogo();

				System.out.println("Outra partidade? (S/n) ");
				String resposta = entrada.nextLine();

				if ("n".equalsIgnoreCase(resposta)) {
					continuar = false;
				} else {

					tabuleiro.reiniciar();

				}
			}
		} catch (SairExption e) {
			System.out.println("Tchau!!!");
		} finally {
			entrada.close();
		}

	}

	private void cicloDoJogo() {
		try {

			while (!tabuleiro.objetivoAlcancado()) {
				System.out.println(tabuleiro.toString());

				String digitado = capturarValorDigitado("Digite (x,y) :");

				// para tranforma de String para Int
				Iterator<Integer> xy = Arrays.stream(digitado.split(",")).map(e -> Integer.parseInt(e.trim()))
						.iterator();

				digitado = capturarValorDigitado("1 - Abrir ou 2 - (Des)Marcar: ");

				if ("1".equals(digitado)) {
					tabuleiro.abrir(xy.next(), xy.next());

				} else if ("2".equalsIgnoreCase(digitado)) {
					tabuleiro.altenarMarcacao(xy.next(), xy.next());
				}

			}
			System.out.println(tabuleiro);
			System.out.println("Voce ganhou!");
		} catch (ExplosaoException e) {
			System.out.println(tabuleiro);
			System.out.println("Voce perdeu!");
		}

	}

	private String capturarValorDigitado(String texto) {
		System.out.println(texto);
		String digitado = entrada.nextLine();

		if ("sair".equalsIgnoreCase(digitado)) {
			throw new SairExption();
		}
		return digitado;

	}
}
