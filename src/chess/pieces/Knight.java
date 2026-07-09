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

// Classe que representa a peça Rei no xadrez, herda de ChessPiece
public class Knight extends ChessPiece{

	// Construtor que inicializa um rei com o tabuleiro e cor
	public Knight(Board board, Color color) {
		// Chama o construtor da classe pai (ChessPiece) passando tabuleiro e cor
		super(board, color);
	}
	
	// Método que retorna a representação em texto do rei
	@Override
	public String toString() {
		// Retorna a letra K para representar o rei
		return "N";
	}

	// Método privado que verifica se o rei pode se mover para uma posição específica
	private boolean canMove(Position position) {
		// Obtém a peça na posição destino e converte para ChessPiece
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		// Retorna true se não há peça ou se a peça é do oponente
		return p == null || p.getColor() != getColor();
	}
	
	// Método que retorna uma matriz booleana com todos os movimentos possíveis do rei
	@Override
	public boolean[][] possibleMoves() {
		// Cria uma matriz booleana com as dimensões do tabuleiro
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		// Cria um objeto auxiliar para testar posições
		Position p = new Position(0, 0);
		
		// Testa movimento para cima/direita
		p.setValues(position.getRow() - 2, position.getColumn() + 1);
		// Verifica se a posição existe e se o rei pode se mover para lá
		if(getBoard().positionExists(p) && canMove(p)) {
			// Marca esta posição como movimento possível
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Testa movimento para direita/cima
		p.setValues(position.getRow() - 1, position.getColumn() + 2);
		// Verifica se a posição existe e se o rei pode se mover para lá
		if(getBoard().positionExists(p) && canMove(p)) {
			// Marca esta posição como movimento possível
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Testa movimento para a direita/baixo
		p.setValues(position.getRow() + 1, position.getColumn() + 2);
		// Verifica se a posição existe e se o rei pode se mover para lá
		if(getBoard().positionExists(p) && canMove(p)) {
			// Marca esta posição como movimento possível
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Testa movimento para baixo/direita
		p.setValues(position.getRow() + 2, position.getColumn() + 1);
		// Verifica se a posição existe e se o rei pode se mover para lá
		if(getBoard().positionExists(p) && canMove(p)) {
			// Marca esta posição como movimento possível
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Testa movimento para baixo/esquerda
		p.setValues(position.getRow() + 2, position.getColumn() - 1);
		// Verifica se a posição existe e se o rei pode se mover para lá
		if(getBoard().positionExists(p) && canMove(p)) {
			// Marca esta posição como movimento possível
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Testa movimento para a esquerda/baixo
		p.setValues(position.getRow() + 1, position.getColumn() - 2);
		// Verifica se a posição existe e se o rei pode se mover para lá
		if(getBoard().positionExists(p) && canMove(p)) {
			// Marca esta posição como movimento possível
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Testa movimento para a esquerda/cima
		p.setValues(position.getRow() + 1, position.getColumn() - 2);
		// Verifica se a posição existe e se o rei pode se mover para lá
		if(getBoard().positionExists(p) && canMove(p)) {
			// Marca esta posição como movimento possível
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Testa movimento para cima/esquerda
		p.setValues(position.getRow() - 2, position.getColumn() - 1);
		// Verifica se a posição existe e se o rei pode se mover para lá
		if(getBoard().positionExists(p) && canMove(p)) {
			// Marca esta posição como movimento possível
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Retorna a matriz com todos os movimentos possíveis
		return mat;
	}
}
