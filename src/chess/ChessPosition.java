// Declara este arquivo como parte do pacote chess
package chess;

// Importa a classe BoardException do pacote boardgame
import boardgame.BoardException;
// Importa a classe Position do pacote boardgame
import boardgame.Position;

// Classe que representa uma posição no xadrez usando notação de coluna (a-h) e linha (1-8)
public class ChessPosition {
	// Variável que armazena a coluna da posição em formato de caractere (a-h)
	private char column;
	// Variável que armazena a linha da posição em formato numérico (1-8)
	private int row;
	
	// Construtor que inicializa uma posição de xadrez validando seus valores
	public ChessPosition(char column, int row) {
		// Verifica se coluna está entre 'a' e 'h' e se linha está entre 1 e 8
		if(column < 'a' || column > 'h' || row < 1 || row > 8) {
			// Lança uma exceção se a posição for inválida
			throw new BoardException("Error instantiating ChessPosition. " + 
									 "Valid values are from a1 to h8.");
		}
		// Atribui a coluna
		this.column = column;
		// Atribui a linha
		this.row = row;
	}

	// Método que retorna a coluna da posição de xadrez
	public char getColumn() {
		// Retorna o caractere da coluna
		return column;
	}

	// Método que retorna a linha da posição de xadrez
	public int getRow() {
		// Retorna o número da linha
		return row;
	}
	
	// Método que converte a posição de xadrez para a posição do tabuleiro
	protected Position toPosition() {
		// Retorna uma nova posição do tabuleiro com as coordenadas convertidas
		return new Position(8 - row, column - 'a');
	}
	
	// Método estático que converte uma posição do tabuleiro para notação de xadrez
	protected static ChessPosition fromPosition(Position position) {
		// Retorna uma nova posição de xadrez com as coordenadas convertidas
		return new ChessPosition((char) ('a' + position.getColumn()), 8 - position.getRow());
	}
	
	// Método que retorna uma representação em texto da posição de xadrez
	@Override
	public String toString() {
		// Retorna a posição formatada como coluna seguida de linha (ex: "a1")
		return "" + column + row;
	}
}
