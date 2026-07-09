// Declara este arquivo como parte do pacote main
package main;

// Importa a classe ArrayList para criar listas dinâmicas
import java.util.ArrayList;
// Importa a exceção InputMismatchException para erros de entrada
import java.util.InputMismatchException;
// Importa a interface List para trabalhar com listas genéricas
import java.util.List;
// Importa a classe Scanner para leitura de entrada do usuário
import java.util.Scanner;

// Importa a classe ChessException para exceções de xadrez
import chess.ChessException;
// Importa a classe ChessMatch que representa a partida
import chess.ChessMatch;
// Importa a classe ChessPiece que representa as peças
import chess.ChessPiece;
// Importa a classe ChessPosition para notação de xadrez
import chess.ChessPosition;

// Classe principal que contém o método main para executar o programa
public class Main {
	// Método principal que inicia a execução do programa
	public static void main(String[] args) {
		// Cria um Scanner para ler entradas do usuário
		Scanner sc = new Scanner(System.in);
		// Cria uma nova partida de xadrez
		ChessMatch chessMatch = new ChessMatch();
		// Cria uma lista para armazenar as peças capturadas
		List<ChessPiece> captured = new ArrayList<>();
		
		// Loop infinito para manter o jogo em execução
		while(!chessMatch.getCheckMate()) {
			// Comentário: bloco try foi comentado para desativar o tratamento de exceções
			try {
				// Limpa a tela do console
				UI.clearScreen();
				// Exibe o estado atual da partida
				UI.PrintMatch(chessMatch, captured);
				// Imprime uma linha em branco
				System.out.println();
							// Exibe mensagem pedindo a posição de origem
							System.out.print("Origem: ");
				// Lê a posição de origem inserida pelo usuário
				ChessPosition source = UI.readChessPosition(sc);
				
				// Obtém a matriz de movimentos possíveis para a peça de origem
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				// Limpa a tela do console
				UI.clearScreen();
				// Exibe o tabuleiro com os movimentos possíveis destacados
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				
				// Imprime uma linha em branco
				System.out.println();
							// Exibe mensagem pedindo a posição de destino
							System.out.print("Destino: ");
				// Lê a posição de destino inserida pelo usuário
				ChessPosition target = UI.readChessPosition(sc);
				
				// Realiza o movimento de xadrez e obtém a peça capturada
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				
				// Se uma peça foi capturada
				if(capturedPiece != null) {
					// Adiciona a peça capturada à lista de peças capturadas
					captured.add(capturedPiece);
				}
				
				if(chessMatch.getPromoted() != null) {
					System.out.println("Enter piece for promotion (B/N/R/Q): ");
					String type = sc.nextLine();
					chessMatch.replacePromotedPiece(type);
				}
			
			}
			// Bloco catch desativado para exceções de xadrez
			catch(ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			// Bloco catch desativado para exceções de entrada
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		
		UI.clearScreen();
		UI.PrintMatch(chessMatch, captured);
	}
}
