package br.com.edvaldo.cm;

import br.com.edvaldo.cm.modelo.Tabuleiro;
import br.com.edvaldo.cm.visao.TabuleiroConsole;

public class Aplicacao {
	
	public static void main(String[] args) {
		
		Tabuleiro tabuleiro = new Tabuleiro (8,8,8);
		new TabuleiroConsole(tabuleiro);
		
		System.out.println(tabuleiro);
	}

}
