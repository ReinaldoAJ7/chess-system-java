// Declara este arquivo como parte do pacote boardgame
package boardgame;

// Classe que representa uma posição no tabuleiro usando coordenadas (linha e coluna)
public class Position {
	// Variável que armazena o número da linha da posição
	private Integer row;
	// Variável que armazena o número da coluna da posição
	private Integer column;
	
	// Construtor vazio que inicializa uma posição sem valores específicos
	public Position() {}

	// Construtor que inicializa uma posição com linha e coluna especificadas
	public Position(Integer row, Integer column) {
		// Chama o construtor da superclasse (Object)
		super();
		// Atribui o número da linha
		this.row = row;
		// Atribui o número da coluna
		this.column = column;
	}

	// Método que retorna o número da linha da posição
	public Integer getRow() {
		// Retorna a linha
		return row;
	}

	// Método que define o número da linha da posição
	public void setRow(Integer row) {
		// Atribui o valor de linha
		this.row = row;
	}

	// Método que retorna o número da coluna da posição
	public Integer getColumn() {
		// Retorna a coluna
		return column;
	}

	// Método que define o número da coluna da posição
	public void setColumn(Integer column) {
		// Atribui o valor de coluna
		this.column = column;
	}
	
	// Método que define simultaneamente a linha e a coluna da posição
	public void setValues(int row, int column) {
		// Atribui o número da linha
		this.row = row;
		// Atribui o número da coluna
		this.column = column;
	}

	// Método que retorna uma representação em texto da posição
	@Override
	public String toString() {
		// Retorna uma string formatada com informações da posição
		return  "Position: \n" +
				"row: " + row + ", column: " + column;
	}
	
	
}