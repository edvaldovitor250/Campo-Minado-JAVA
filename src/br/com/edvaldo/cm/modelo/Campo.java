package br.com.edvaldo.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.edvaldo.cm.excecao.ExplosaoException;

public class Campo {

	//primeiro passo fazer as variaveis.
	
	private final int linha;
	private final int coluna;

	private boolean aberto = false;
	private boolean minado = false;
	private boolean marcado = false;

	// segundo passo fazer a List.
	private List<Campo> vizinhos = new ArrayList<>();

	// terceiro passo cria um construtor, para o usuario jogar.
	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
 //quarto passo cria um metodo adicionar, dai para frente é so metodo relacionado a o jogo
	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;

		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;

		// dentro do metodo cria as vizinhas
		
		if (deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else if (deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
	}

	void alternarMarcacao() {
		if (!aberto) {
			marcado = !marcado;
		}
	}

	boolean abrir() {

		if (!aberto && !marcado) {
			aberto = true;

			if (minado) {
				throw new ExplosaoException();
			}

			if (vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			return true;
		} else {
			return false;

		}

	}

	boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	public boolean isMinado() {
     return minado;
	}

	
	public boolean isMarcado() {
		return marcado;
	}

	boolean minar() {

		return minado = true;
	}
	
	 void setAberto(boolean aberto) {
		this.aberto = aberto;
	}
	public boolean isAberto() {
		return aberto;
	}

	public boolean isFechado() {
		return !isAberto();
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}

	//usando para final de jogo.
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}

	long minasNaVizinhaca() {
		return vizinhos.stream().filter(v -> v.minado).count();

	}
      //reniciar o jogo.
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
	}
	
	// sexto passo é criar meio que logica de como o jogo vai funcionar
	public String toString() {
		if (marcado) {
			//para marcar
			return "x";
			
		}else if (aberto && minado) {
			//mina
			 return "*";
		} else if (aberto && minasNaVizinhaca() > 0) {
			return Long.toString(minasNaVizinhaca());
		} else if (aberto) {
			// Está aberto
			return " ";
		} else {
			//os blocos
			return "?";
		}
			
		
	}

}
