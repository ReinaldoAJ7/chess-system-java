// Declara este arquivo como parte do pacote chess.pieces
package chess.pieces;

// Importa a classe Board do pacote boardgame
import boardgame.Board;
// Importa a classe Position do pacote boardgame
import boardgame.Position;
import chess.ChessMatch;
// Importa a classe abstrata ChessPiece que é a base para as peças
import chess.ChessPiece;
// Importa a enumeração Color para as cores das peças
import chess.Color;

// Classe que representa o Peão (Pawn) no jogo de xadrez
public class Pawn extends ChessPiece{

	private ChessMatch chessMatch;
	
	// Construtor que inicializa um peão com tabuleiro e cor
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		// Chama o construtor da classe pai (ChessPiece) passando tabuleiro e cor
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public boolean[][] possibleMoves() {
		// Cria uma matriz booleana com as dimensões do tabuleiro
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		// Cria um objeto auxiliar para testar posições
		Position p = new Position(0, 0);
		
		// Se a peça é branca, se move para cima (linha diminui)
		if(getColor() == Color.WHITE) {
			// Movimento para frente (uma casa)
			p.setValues(position.getRow() - 1, position.getColumn());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// Movimento inicial (duas casas)
			p.setValues(position.getRow() - 2, position.getColumn());
			Position p2 = new Position(position.getRow() - 1, position.getColumn());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// Captura diagonal esquerda
			p.setValues(position.getRow() - 1, position.getColumn() - 1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// Captura diagonal direita
			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
				mat[p.getRow()][p.getColumn()] = true;
			}	
			
			//Movimento especial enPassant white
			if(position.getRow() == 3) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVunerable()) {
					mat[left.getRow() - 1][left.getColumn()] = true;
				}
				
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVunerable()) {
					mat[right.getRow() - 1][right.getColumn()] = true;
				}
			}
			
		}
		// Se a peça é preta, se move para baixo (linha aumenta)
		else {
			// Movimento para frente (uma casa)
			p.setValues(position.getRow() + 1, position.getColumn());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// Movimento inicial (duas casas)
			p.setValues(position.getRow() + 2, position.getColumn());
			Position p2 = new Position(position.getRow() + 1, position.getColumn());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// Captura diagonal esquerda
			p.setValues(position.getRow() + 1, position.getColumn() - 1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// Captura diagonal direita
			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			
			//Movimento especial enPassant black
			if(position.getRow() == 4) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVunerable()) {
					mat[left.getRow() + 1][left.getColumn()] = true;
				}
				
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVunerable()) {
					mat[right.getRow() + 1][right.getColumn()] = true;
				}
			}
		}
		
		// Retorna a matriz de movimentos possíveis
		return mat;
	}
	
	// Método que retorna a representação em texto do peão
	@Override
	public String toString() {
		// Retorna a letra "P" para representar o peão
		return "P";
	}

}
