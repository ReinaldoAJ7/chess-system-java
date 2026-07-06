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
public class King extends ChessPiece{

	// Construtor que inicializa um rei com o tabuleiro e cor
	public King(Board board, Color color) {
		// Chama o construtor da classe pai (ChessPiece) passando tabuleiro e cor
		super(board, color);
	}
	
	// Método que retorna a representação em texto do rei
	@Override
	public String toString() {
		// Retorna a letra K para representar o rei
		return "K";
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
		
		// Testa movimento para cima (linha - 1)
		// Define a nova posição uma linha acima
		p.setValues(position.getRow() -1, position.getColumn());
		// Verifica se a posição existe e se o rei pode se mover para lá
		if(getBoard().positionExists(p) && canMove(p)) {
			// Marca esta posição como movimento possível
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Testa movimento para baixo (linha + 1)
		// Define a nova posição uma linha abaixo
		p.setValues(position.getRow() +1, position.getColumn());
		// Verifica se a posição existe e se o rei pode se mover para lá
		if(getBoard().positionExists(p) && canMove(p)) {
			// Marca esta posição como movimento possível
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Testa movimento para o lado direito (coluna + 1)
		// Define a nova posição uma coluna à direita
		p.setValues(position.getRow(), position.getColumn() + 1);
		// Verifica se a posição existe e se o rei pode se mover para lá
		if(getBoard().positionExists(p) && canMove(p)) {
			// Marca esta posição como movimento possível
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Testa movimento para o lado esquerdo (coluna - 1)
		// Define a nova posição uma coluna à esquerda
		p.setValues(position.getRow(), position.getColumn() - 1);
		// Verifica se a posição existe e se o rei pode se mover para lá
		if(getBoard().positionExists(p) && canMove(p)) {
			// Marca esta posição como movimento possível
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Testa movimento para o canto superior esquerdo (NO)
		// Define a nova posição diagonal para cima e à esquerda
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		// Verifica se a posição existe e se o rei pode se mover para lá
		if(getBoard().positionExists(p) && canMove(p)) {
			// Marca esta posição como movimento possível
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Testa movimento para o canto superior direito (NE)
		// Define a nova posição diagonal para cima e à direita
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		// Verifica se a posição existe e se o rei pode se mover para lá
		if(getBoard().positionExists(p) && canMove(p)) {
			// Marca esta posição como movimento possível
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Testa movimento para o canto inferior esquerdo (SO)
		// Define a nova posição diagonal para baixo e à esquerda
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		// Verifica se a posição existe e se o rei pode se mover para lá
		if(getBoard().positionExists(p) && canMove(p)) {
			// Marca esta posição como movimento possível
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Testa movimento para o canto inferior direito (SE)
		// Define a nova posição diagonal para baixo e à direita
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		// Verifica se a posição existe e se o rei pode se mover para lá
		if(getBoard().positionExists(p) && canMove(p)) {
			// Marca esta posição como movimento possível
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Retorna a matriz com todos os movimentos possíveis
		return mat;
	}
}
