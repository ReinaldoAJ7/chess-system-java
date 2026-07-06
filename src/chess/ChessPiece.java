// Declara este arquivo como parte do pacote chess
package chess;

// Importa a classe Board do pacote boardgame
import boardgame.Board;
// Importa a classe abstrata Piece do pacote boardgame
import boardgame.Piece;
// Importa a classe Position do pacote boardgame
import boardgame.Position;

// Classe abstrata que representa uma peça de xadrez, herda de Piece
public abstract class ChessPiece extends Piece{
	
	// Variável que armazena a cor da peça de xadrez
	private Color color;
	private int moveCount;

	// Construtor que inicializa uma peça de xadrez com tabuleiro e cor
	public ChessPiece(Board board, Color color) {
		// Chama o construtor da classe pai (Piece) passando o tabuleiro
		super(board);
		// Atribui a cor da peça
		this.color = color;
	}

	// Método que retorna a cor da peça
	public Color getColor() {
		// Retorna a cor da peça
		return color;
	}
	public int getMoveCount() {
		return moveCount;
	}
	
	public void increaseMoveCount() {
		moveCount++;
	}
	public void decreaseMoveCount() {
		moveCount--;
	}
	
	// Método que retorna a posição da peça em notação de xadrez
	public ChessPosition getChessPosition() {
		// Converte a posição do tabuleiro para notação de xadrez e retorna
		return ChessPosition.fromPosition(position);
	}
	
	// Método protegido que verifica se há uma peça adversária em uma posição
	protected boolean isThereOpponentPiece(Position position) {
		// Obtém a peça na posição e converte para ChessPiece
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		// Retorna true se há uma peça e ela não tem a mesma cor que a peça atual
		return p != null && p.getColor() != color;
	}
	
	
	
}
