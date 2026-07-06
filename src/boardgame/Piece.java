package boardgame;

// Classe abstrata que representa uma peça genérica do jogo
public abstract class Piece {
	
	// Variável protegida que armazena a posição atual da peça no tabuleiro
	protected Position position;
	// Variável que armazena a referência do tabuleiro onde a peça está
	private Board board;
	
	
	// Construtor que inicializa uma peça com referência a um tabuleiro
	public Piece(Board board) {
		// Atribui a referência do tabuleiro
		this.board = board;
		// Inicializa a posição como null (peça ainda não foi colocada)
		position = null;
		
	}

	// Método protegido que retorna o tabuleiro ao qual a peça pertence
	protected  Board getBoard() {
		// Retorna a referência do tabuleiro
		return board;
	}

	// Método abstrato que define movimentos possíveis de uma peça
	public abstract boolean[][] possibleMoves();
	
	// Método que verifica se há um movimento possível para uma posição específica
	public boolean possibleMove(Position position) {
		// Retorna true se o movimento para essa posição está marcado como possível
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
	// Método que verifica se a peça tem pelo menos um movimento possível
	public boolean isThereAnyPossibleMove() {
		// Obtém a matriz de movimentos possíveis
		boolean[][] mat = possibleMoves();
		// Loop externo que itera sobre as linhas da matriz
		for(int i = 0; i < mat.length; i++) {
			// Loop interno que itera sobre as colunas da matriz
			for(int j = 0; j < mat.length; j++) {
				// Se encontra um movimento possível (valor true)
				if(mat[i][j]) {
					// Retorna true indicando que há pelo menos um movimento
					return true;
				}
			}
		}
		// Retorna false se nenhum movimento foi encontrado
		return false;		
	}
}