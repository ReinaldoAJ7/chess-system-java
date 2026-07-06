package boardgame;

// Classe de exceção que herda de RuntimeException para erros relacionados ao tabuleiro
public class BoardException extends RuntimeException{

	// Identificador de versão da classe para serialização
	private static final long serialVersionUID = 1L;
	
	// Construtor que cria uma exceção com uma mensagem de erro
	public BoardException(String msg) {
		// Chama o construtor da classe pai (RuntimeException) passando a mensagem
		super(msg);
	}
	
	
}