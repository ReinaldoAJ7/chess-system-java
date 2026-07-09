// Declara este arquivo como parte do pacote chess.pieces
package chess.pieces;

// Importa a classe Board do pacote boardgame
import boardgame.Board;
// Importa a classe Position do pacote boardgame
import boardgame.Position;
import chess.ChessMatch;
// Importa a classe ChessPiece do pacote chess
import chess.ChessPiece;
// Importa a enumeração Color do pacote chess
import chess.Color;

// Classe que representa a peça Rei no xadrez, herda de ChessPiece
public class King extends ChessPiece{

	private ChessMatch chessMatch;
	
	// Construtor que inicializa um rei com o tabuleiro e cor
	public King(Board board, Color color, ChessMatch chessMatch) {
		// Chama o construtor da classe pai (ChessPiece) passando tabuleiro e cor
		super(board, color);
		this.chessMatch = chessMatch;
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
	
 	 private boolean testRookCastling(Position position) {
 		 ChessPiece p = (ChessPiece)getBoard().piece(position);
 		 // deve existir uma peça, ela deve ser uma Rook da mesma cor e não pode ter se movido
 		 return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
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
		
		//Jogadas especiais "Roque (Castling)"
		if(getMoveCount() == 0 && !chessMatch.getCheck()) {
			//Jogadas especiais "Roque (Castling)" - King side Rook
			Position posT1 = new Position (position.getRow(), position.getColumn() + 3);
			if(testRookCastling(posT1)) {
				Position p1 = new Position(position.getRow(), position.getColumn() + 1);
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				if(getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			
					 //Jogadas especiais "Roque (Castling)" - Queen side Rook
					 // rook fica 4 colunas à esquerda do rei
					 Position posT2 = new Position (position.getRow(), position.getColumn() - 4);
			if(testRookCastling(posT2)) {
				Position p1 = new Position(position.getRow(), position.getColumn() - 1);
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3);
				if(getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
			}
		}
		// Retorna a matriz com todos os movimentos possíveis
		return mat;
	}
}
