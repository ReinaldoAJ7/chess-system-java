// Declara este arquivo como parte do pacote chess.pieces
package chess.pieces;

// Importa a classe Board do pacote boardgame
import boardgame.Board;
// Importa a classe Position do pacote boardgame
import boardgame.Position;
// Importa a classe ChessPiece do pacote chess
import chess.ChessPiece;
// Importa a enumeração Color do pacote chess
import chess.Color;

// Classe que representa a peça Torre no xadrez, herda de ChessPiece
public class Bishop extends ChessPiece{

	// Construtor que inicializa uma torre com o tabuleiro e cor
	public Bishop(Board board, Color color) {
		// Chama o construtor da classe pai (ChessPiece) passando tabuleiro e cor
		super(board, color);
	}
	
	// Método que retorna a representação em texto da torre
	@Override
	public String toString() {
		// Retorna a letra R para representar a torre
		return "B";
	}

	// Método que retorna uma matriz booleana com todos os movimentos possíveis da torre
	@Override
	public boolean[][] possibleMoves() {
		// Cria uma matriz booleana com as dimensões do tabuleiro
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		// Cria um objeto auxiliar para testar posições
		Position p = new Position(0, 0);
		
		// Testa movimentos para diagonal nordeste
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		// Loop enquanto a posição existe e não há peça nela
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
			// Marca esta posição como movimento possível
			mat[p.getRow()][p.getColumn()] = true;
			// Move uma linha acima
			p.setValues(p.getRow() - 1, p.getColumn() + 1);
		}
		// Se há uma peça do oponente nesta posição
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
			// Marca como movimento possível (pode capturar)
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Testa movimentos para diagonal suldeste
		p.setValues(position.getRow()+1, position.getColumn() +1);
		// Loop enquanto a posição existe e não há peça nela
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
			// Marca esta posição como movimento possível
			mat[p.getRow()][p.getColumn()] = true;
			// Move uma coluna à esquerda
			p.setValues(p.getRow() + 1, p.getColumn() + 1);
		}
		// Se há uma peça do oponente nesta posição
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
			// Marca como movimento possível (pode capturar)
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Testa movimentos para a diagonal suldoeste
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		// Loop enquanto a posição existe e não há peça nela
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
			// Marca esta posição como movimento possível
			mat[p.getRow()][p.getColumn()] = true;
			// Move uma coluna à direita
			p.setValues(p.getRow() + 1, p.getColumn() - 1);
		}
		// Se há uma peça do oponente nesta posição
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
			// Marca como movimento possível (pode capturar)
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Testa movimentos para diagonal nordeste
		// Define a nova posição uma linha abaixo
		p.setValues(position.getRow() -1, position.getColumn() - 1);
		// Loop enquanto a posição existe e não há peça nela
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
			// Marca esta posição como movimento possível
			mat[p.getRow()][p.getColumn()] = true;
			// Move uma linha abaixo
			p.setValues(p.getRow() - 1, p.getColumn() + 1);
		}
		// Se há uma peça do oponente nesta posição
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
			// Marca como movimento possível (pode capturar)
			mat[p.getRow()][p.getColumn()] = true;
		}
				
		// Retorna a matriz com todos os movimentos possíveis
		return mat;
	}
}

