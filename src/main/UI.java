// Declara este arquivo como parte do pacote main
package main;

// Importa a classe Arrays para converter arrays em strings
import java.util.Arrays;
// Importa a exceção InputMismatchException para erros de entrada
import java.util.InputMismatchException;
// Importa a interface List para trabalhar com listas genéricas
import java.util.List;
// Importa a classe Scanner para leitura de entrada do usuário
import java.util.Scanner;
// Importa Collectors para usar operações de stream em coleções
import java.util.stream.Collectors;

// Importa a classe ChessMatch que representa a partida
import chess.ChessMatch;
// Importa a classe ChessPiece que representa as peças
import chess.ChessPiece;
// Importa a classe ChessPosition para notação de xadrez
import chess.ChessPosition;
// Importa a enumeração Color para as cores das peças
import chess.Color;

// Classe utilitária que contém métodos para exibição da interface do jogo
public class UI {

	// Constante ANSI que reseta todas as formatações de cor
	// Link referenciado: https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	public static final String ANSI_RESET = "\u001B[0m";
	// Constante ANSI para código de cor preta em texto
	public static final String ANSI_BLACK = "\u001B[30m";
	// Constante ANSI para código de cor vermelha em texto
	public static final String ANSI_RED = "\u001B[31m";
	// Constante ANSI para código de cor verde em texto
	public static final String ANSI_GREEN = "\u001B[32m";
	// Constante ANSI para código de cor amarela em texto
	public static final String ANSI_YELLOW = "\u001B[33m";
	// Constante ANSI para código de cor azul em texto
	public static final String ANSI_BLUE = "\u001B[34m";
	// Constante ANSI para código de cor roxa em texto
	public static final String ANSI_PURPLE = "\u001B[35m";
	// Constante ANSI para código de cor ciano em texto
	public static final String ANSI_CYAN = "\u001B[36m";
	// Constante ANSI para código de cor branca em texto
	public static final String ANSI_WHITE = "\u001B[37m";

	// Constante ANSI para fundo preto
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	// Constante ANSI para fundo vermelho
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	// Constante ANSI para fundo verde
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	// Constante ANSI para fundo amarelo
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	// Constante ANSI para fundo azul
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	// Constante ANSI para fundo roxo
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	// Constante ANSI para fundo ciano
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	// Constante ANSI para fundo branco
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	// Método estático que limpa a tela do console
	// Link referenciado: https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() {
		// Envia comando ANSI para mover cursor para início e limpar tela
		System.out.print("\033[H\033[2J");
		// Força a exibição do output
		System.out.flush();
	}

	// Método estático que lê uma posição de xadrez do usuário
	public static ChessPosition readChessPosition(Scanner sc) {
		// Bloco try para capturar possíveis exceções
		try {
			// Lê uma linha inteira do usuário
			String s = sc.nextLine();
			// Obtém o primeiro caractere como coluna
			char column = s.charAt(0);
			// Converte o resto da string para inteiro como linha
			int row = Integer.parseInt(s.substring(1));
			// Retorna uma nova posição de xadrez com coluna e linha
			return new ChessPosition(column, row);
		} catch (RuntimeException e) {
			// Se houver erro na leitura, lança uma exceção
			throw new InputMismatchException("Erro reading ChessPosition.\n" + "Valid values are from a1 to h8.");
		}
	}

	// Método estático que exibe a partida com o tabuleiro, peças capturadas e informações
	public static void PrintMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
		// Exibe o tabuleiro com as peças
		printBoard(chessMatch.getPieces());
		// Imprime uma linha em branco
		System.out.println();
		// Exibe as peças capturadas
		printCapturedPieces(captured);
		// Imprime uma linha em branco
		System.out.println();
		// Exibe o número da jogada atual
		System.out.println("Turn: " + chessMatch.getTurn());
		// Exibe qual jogador está esperando para jogar
		System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
		
		// Se há xeque no jogo
		if(chessMatch.getCheck()) {
			// Exibe mensagem de alerta de xeque
			System.out.println("CHECK! >>>");
		}
	}
	
	// Método estático que exibe o tabuleiro sem destacar movimentos possíveis
	public static void printBoard(ChessPiece[][] pieces) {
		// Bloco try para capturar exceções de array
		try {
			// Loop que itera sobre cada linha do tabuleiro
			for (int i = 0; i < pieces.length; i++) {
				// Exibe o número da linha (8 no topo, 1 na base)
				System.out.print((8 - i) + " ");
				// Loop que itera sobre cada coluna da linha
				for (int j = 0; j < pieces[i].length; j++) {
					// Exibe a peça sem destaque de movimento possível
					printPiece(pieces[i][j], false);
				}
				// Quebra de linha
				System.out.println();
			}
			// Exibe as letras das colunas (a até h)
			System.out.println("  a b c d e f g h");
		} catch (ArrayIndexOutOfBoundsException e) {
			// Se houver erro ao acessar o array, exibe mensagem de erro
			System.out.println(e.getMessage());
		}
	}
	
	// Método estático que exibe o tabuleiro com destaque de movimentos possíveis
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
		// Bloco try para capturar exceções de array
		try {
			// Loop que itera sobre cada linha do tabuleiro
			for (int i = 0; i < pieces.length; i++) {
				// Exibe o número da linha (8 no topo, 1 na base)
				System.out.print((8 - i) + " ");
				// Loop que itera sobre cada coluna da linha
				for (int j = 0; j < pieces[i].length; j++) {
					// Exibe a peça com destaque se há movimento possível
					printPiece(pieces[i][j], possibleMoves[i][j]);
				}
				// Quebra de linha
				System.out.println();
			}
			// Exibe as letras das colunas (a até h)
			System.out.println("  a b c d e f g h");
		} catch (ArrayIndexOutOfBoundsException e) {
			// Se houver erro ao acessar o array, exibe mensagem de erro
			System.out.println(e.getMessage());
		}
	}

	// Método privado estático que exibe uma peça individual
	private static void printPiece(ChessPiece piece, boolean background) {
		// Se há destaque de movimento possível
		if(background) {
			// Inicia o fundo verde
			System.out.print(ANSI_GREEN_BACKGROUND);
		}
		// Se não há peça na posição
		if (piece == null) {
			// Exibe um hífen seguido de reset de formatação
			System.out.print("-" + ANSI_RESET);
		} else {
			// Se a peça é branca
			if (piece.getColor() == Color.WHITE) {
				// Exibe a peça em branco com reset
				System.out.print(ANSI_WHITE + piece + ANSI_RESET);
			} else {
				// Se a peça é preta, exibe em vermelho com reset
				System.out.print(ANSI_RED + piece + ANSI_RESET);
			}
		}
		// Imprime um espaço após a peça
		System.out.print(" ");
	}
	
	// Método privado estático que exibe as peças capturadas
	private static void printCapturedPieces(List<ChessPiece> captured) {
		// Filtra e coleta as peças brancas capturadas
		List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
		// Filtra e coleta as peças pretas capturadas
		List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
		// Exibe título da seção
		System.out.println("Captured pieces: ");
		// Exibe rótulo para peças brancas
		System.out.print("White: ");
		// Inicia exibição em branco
		System.out.print(ANSI_WHITE);
		// Converte array de peças brancas em string e exibe
		System.out.println(Arrays.toString(white.toArray()));
		// Reseta a formatação
		System.out.print(ANSI_RESET);
		
		// Exibe rótulo para peças pretas
		System.out.print("Black: ");
		// Inicia exibição em vermelho (para preto)
		System.out.print(ANSI_RED);
		// Converte array de peças pretas em string e exibe
		System.out.println(Arrays.toString(black.toArray()));
		// Reseta a formatação
		System.out.print(ANSI_RESET);
	}
}
