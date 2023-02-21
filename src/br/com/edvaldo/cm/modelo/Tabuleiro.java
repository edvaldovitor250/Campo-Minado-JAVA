package br.com.edvaldo.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.edvaldo.cm.excecao.ExplosaoException;

public class Tabuleiro {

	// vai usar a logica do jogo a seguir

	private int linhas;
	private int colunas;
	private int minas;

	private final List<Campo> campos = new ArrayList<>();

	
	// aqui serar gerado as linhas , colunas e minas. no decorrer do usuario pedir.
	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;

		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}

	// usado para abrir a area escolinhadas pelo usuario
	public void abrir(int linha, int coluna) {
		try {
			campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst()
			.ifPresent(c -> c.abrir());			
		} catch (ExplosaoException e) {
			
			campos.forEach(c -> c.setAberto(true));
			throw e;
		}
	}
    // altenar a Marcação do usuario
	public void altenarMarcacao(int linha, int coluna) {
		campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst()
				.ifPresent(c -> c.alternarMarcacao());
	}

	// aqui é utilizado para calcular a coluna é a linha, e cria um campo novo
	private void gerarCampos() {
		for (int l = 0; l < linhas; l++) {
			for (int c = 0; c < colunas; c++) {
				campos.add(new Campo(l, c));
			}
		}
	}

	// aqui associar para quantos numeros para cada bomba
	private void associarVizinhos() {
		for (Campo c1 : campos) {
			for (Campo c2 : campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}

	//essa ideiologia é para sortear as Minas.
	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();

		//Essa Estrutura é usada para gerar as minas e para colocar a quantidade ideias de minas
		do {
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
		} while (minasArmadas < minas);

	}

	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}

	// usei o lambda para facilitar o reiniciar o jogo
	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}

	//usando o StringBuilder para facilitar a impressão do toString
	public String toString() {
		// essa forma de criar o StringBuilder
		StringBuilder sb = new StringBuilder();
		
		sb.append("  ");
		for (int c = 0; c < colunas; c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
		}
		
		sb.append("\n");

		//será usado para um particula para contar do 0
		int i = 0;
		//usado para gerar a linha e a coluna
		for (int l = 0; l < linhas; l++) {
			sb.append(l);
			sb.append(" ");
			for (int c = 0; c < colunas; c++) {
				sb.append(" ");
				// sb.append(campos.get(i)); -> é usado para contar o campo
				sb.append(campos.get(i));
				sb.append(" ");
				i++;
			}
			//quebrar linha 
			sb.append("\n");

		}

		//vai return tudo passado pelo StringBuilder
		return sb.toString();
	}

}
