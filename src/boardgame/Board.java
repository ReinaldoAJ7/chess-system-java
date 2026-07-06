package boardgame;

// Classe que representa o tabuleiro de jogo
public class Board {
	// Variável que armazena o número de linhas do tabuleiro
	private Integer rows;
	// Variável que armazena o número de colunas do tabuleiro
	private Integer columns;
	// Variável que armazena a matriz bidimensional de peças do tabuleiro
	private Piece[][] pieces;
	

	// Construtor que inicializa o tabuleiro com dimensões especificadas
	public Board(Integer rows, Integer columns) {
		// Verifica se o número de linhas e colunas é válido (maior que 0)
		if(rows < 1 || columns <1) {
			// Lança uma exceção se o tabuleiro for inválido
			throw new BoardException("          Error creating board!!\n " +	
									 "There must be at least 1 row and 1 column...");
		}
		// Atribui o número de linhas
		this.rows = rows;
		// Atribui o número de colunas
		this.columns = columns;
		// Cria a matriz de peças com as dimensões especificadas
		pieces = new Piece[rows][columns];
	}

	// Método que retorna o número de linhas do tabuleiro
	public Integer getRows() {
		// Retorna a quantidade de linhas
		return rows;
	}
	
	// Método que retorna o número de colunas do tabuleiro
	public Integer getColumns() {
		// Retorna a quantidade de colunas
		return columns;
	}	
	
	// Método que retorna uma peça em uma posição específica usando coordenadas
	public Piece piece(int row, int column) {
		// Verifica se a posição existe no tabuleiro
		if(!positionExists(row, column)) {
			// Lança uma exceção se a posição não existir
			throw new BoardException("position not on the board.");
		}
		// Retorna a peça na posição especificada
		return pieces[row][column];
	}
	
	// Método que retorna uma peça em uma posição específica usando objeto Position
	public Piece piece(Position position) {
		// Verifica se a posição existe no tabuleiro
		if(!positionExists(position)) {
			// Lança uma exceção se a posição não existir
			throw new BoardException("position not on the board.");
		}
		// Retorna a peça na posição especificada
		return pieces[position.getRow()][position.getColumn()];
	}
	
	// Método que coloca uma peça em uma posição específica do tabuleiro
	public void placePiece(Piece piece, Position position) {
		// Verifica se já existe uma peça nessa posição
		if(thereIsAPiece(position)) {
			// Lança uma exceção se já houver uma peça
			throw new BoardException("There's already a piece on position.");
		}
		// Coloca a peça na matriz de peças
		pieces[position.getRow()][position.getColumn()] = piece;
		// Define a posição da peça
		piece.position = position;
	}
	
	// Método que remove e retorna uma peça de uma posição específica do tabuleiro
	public Piece removePiece(Position position) {
		// Verifica se a posição existe no tabuleiro
		if(!positionExists(position)) {
			// Lança uma exceção se a posição não existir
			throw new BoardException("position not on the board.");
		}
		// Obtém a peça na posição
		if(piece(position) == null) {
			// Retorna null se não houver peça
			return null;
		}
		// Armazena a peça em uma variável auxiliar
		Piece aux = piece(position);
		// Remove a posição da peça
		aux.position = null;
		// Remove a peça da matriz
		pieces[position.getRow()][position.getColumn()] = null;
		// Retorna a peça removida
		return aux;
	}
	
	// Método que verifica se uma posição existe no tabuleiro usando coordenadas
    public boolean positionExists(int row, int column) {
		// Verifica se a linha e coluna estão dentro dos limites do tabuleiro
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	
	// Método que verifica se uma posição existe no tabuleiro usando objeto Position
	public boolean positionExists(Position position) {
		// Chama o método positionExists com as coordenadas do objeto Position
		return positionExists(position.getRow(), position.getColumn());
	}
	
	// Método que verifica se existe uma peça em uma posição específica do tabuleiro
	public boolean thereIsAPiece(Position position) {
		// Verifica se a posição existe no tabuleiro
		if(!positionExists(position)) {
			// Lança uma exceção se a posição não existir
			throw new BoardException("position not on the board.");
		}
		// Retorna true se houver uma peça, false caso contrário
		return piece(position) != null;
	}
}