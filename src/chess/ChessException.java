// Declara este arquivo como parte do pacote chess
package chess;

// Importa a classe BoardException do pacote boardgame
import boardgame.BoardException;

// Classe de exceção específica para erros de xadrez, herda de BoardException
public class ChessException extends BoardException{

	// Identificador de versão da classe para serialização
	private static final long serialVersionUID = 1L;

	// Construtor que cria uma exceção com uma mensagem de erro
	public ChessException(String msg) {
		// Chama o construtor da classe pai (BoardException) passando a mensagem
		super(msg);
	}
}