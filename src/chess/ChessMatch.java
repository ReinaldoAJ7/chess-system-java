// Declara que este arquivo faz parte do pacote chess
package chess;

// Importa a classe ArrayList para criar listas dinâmicas que podem crescer
import java.util.ArrayList;
// Importa a interface List para trabalhar com listas genéricas
import java.util.List;
// Importa Collectors para usar operações de stream em coleções
import java.util.stream.Collectors;

// Importa a classe Board que representa o tabuleiro de jogo
import boardgame.Board;
// Importa a classe abstrata Piece que é a base para as peças
import boardgame.Piece;
// Importa a classe Position que armazena coordenadas no tabuleiro
import boardgame.Position;
import chess.pieces.Bishop;
// Importa a classe King que representa a peça Rei
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
// Importa a classe Rook que representa a peça Torre
import chess.pieces.Rook;

// Classe que gerencia toda a lógica de uma partida de xadrez
public class ChessMatch {
	// Variável privada que armazena o tabuleiro 8x8
	private Board board;
	// Variável privada que armazena qual cor de jogador está jogando (WHITE ou BLACK)
	private Color currentPlayer;
	// Variável privada que armazena o número da jogada atual (começa em 1)
	private Integer turn;
	// Variável privada booleana que indica se o rei está em xeque
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVunerable;
	private ChessPiece promoted;
	

	// Lista privada que armazena todas as peças que ainda estão no tabuleiro
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	// Lista privada que armazena todas as peças que foram capturadas durante o jogo
	private List<Piece> capturedPieces = new ArrayList<>();

	// Construtor que inicializa uma nova partida de xadrez
	public ChessMatch() {
		// Cria um tabuleiro padrão de xadrez com 8 linhas e 8 colunas
		board = new Board(8, 8);
		// Define o número inicial de jogada como 1 (primeira jogada)
		turn = 1;
		// Define a cor branca como o primeiro jogador (padrão no xadrez)
		currentPlayer = Color.WHITE;
		// Chama o método que coloca as peças nas posições iniciais do xadrez
		initialSetup();
	}
	
	// Método público que retorna o número da jogada atual
	public int getTurn() {
		// Retorna o valor inteiro do número da jogada
		return turn;
	}
	
	// Método público que retorna qual jogador está jogando neste momento
	public Color getCurrentPlayer() {
		// Retorna a cor do jogador atual (WHITE ou BLACK)
		return currentPlayer;
	}
	
	// Método público que informa se o rei está em xeque
	public boolean getCheck() {
		// Retorna true se há xeque, false caso contrário
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}
	
	public ChessPiece getEnPassantVunerable() {
		return enPassantVunerable;
	}
	
	//
	public ChessPiece getPromoted() {
		return promoted;
	}
	
	// Método público que retorna uma matriz com todas as peças do tabuleiro
	public ChessPiece[][] getPieces() {
		// Cria uma matriz bidimensional com as mesmas dimensões do tabuleiro
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		// Loop externo: itera sobre cada linha do tabuleiro (0 a 7)
		for(int i = 0; i < board.getRows();  i++) {
			// Loop interno: itera sobre cada coluna do tabuleiro (0 a 7)
			for(int j = 0; j < board.getColumns(); j++) {
				// Obtém a peça na posição [i,j] do tabuleiro e a coloca na matriz
				// O casting converte de Piece para ChessPiece
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		// Retorna a matriz com todas as peças
		return mat;
	}
	
	// Método público que retorna uma matriz booleana dos movimentos possíveis de uma peça
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		 // Converte a posição de notação de xadrez (ex: "WHITEpara coordenadas de tabuleiro
		 Position position = sourcePosition.toPosition();
		 // Valida se a posição de origem contém uma peça válida do jogador atual
		 validateSourcePosition(position);
		 // Obtém e retorna a matriz de movimentos possíveis da peça naquela posição
		 return board.piece(position).possibleMoves();
	}
	
	// Método público que executa um movimento de xadrez
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		// Converte a posição de origem de notação de xadrez para coordenadas do tabuleiro
		Position source = sourcePosition.toPosition();
		// Converte a posição de destino de notação de xadrez para coordenadas do tabuleiro
		Position target = targetPosition.toPosition();
		// Valida se a posição de origem tem uma peça válida do jogador atual
		validateSourcePosition(source);
		// Valida se o movimento para a posição de destino é permitido
		validateTargetPosition(source, target);
		// Executa o movimento e armazena a peça capturada (se houver alguma)
		Piece capturedPiece = makeMove(source, target);
		
		// Verifica se o jogador atual colocou seu próprio rei em xeque (movimento inválido)
		if(testCheck(currentPlayer)) {
			// Se o rei ficou em xeque, desfaz o movimento que foi feito
			undoMove(source, target, capturedPiece);
			// Lança uma exceção informando que o movimento é ilegal
			throw new ChessException("Você não pode se colocar em xeque.");
		}
		
		ChessPiece movedPiece = (ChessPiece)board.piece(target);
		
		//Movimento especial - Promoted(promoção)
		promoted = null;
		if(movedPiece instanceof Pawn) {
			if((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
				promoted = (ChessPiece)board.piece(target);
				promoted = replacePromotedPiece("Q");				
			}
		}
		
		
		
		// Verifica se o oponente está em xeque após o movimento
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		if(testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}
		else {		
			// Passa a vez para o próximo jogador
			nextTurn();
		}
		
		//Movivimento especial - en passant
		if(movedPiece instanceof Pawn && (target.getRow() == source.getRow() + 2 || target.getRow() == source.getRow() - 2)) {
			enPassantVunerable = movedPiece;
		}
		else {
			enPassantVunerable = null;
		}
		
		// Retorna a peça capturada (ou null se nenhuma peça foi capturada)
		return (ChessPiece)capturedPiece;
	}
	
	public ChessPiece replacePromotedPiece(String type) {
		if(promoted == null) {
			throw new IllegalStateException("There's no piece to be promoted! ");
		}
		if(!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")){
			return promoted;
		}
		
		Position pos = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);
		
		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);
		
		return newPiece;
	}
	
	private ChessPiece newPiece(String type, Color color) {
		if(type.equals("B")) return new Bishop(board, color);
		if(type.equals("N")) return new Knight(board, color);
		if(type.equals("R")) return new Rook(board, color);
		return new Queen(board, color);
		
	}
	
	// Método privado que executa o movimento físico de uma peça no tabuleiro
	private Piece makeMove(Position source, Position target) {
		 // Remove a peça da posição de origem
		 ChessPiece p = (ChessPiece)board.removePiece(source);
		 
		 p.increaseMoveCount();
		 
		 // Tenta remover uma peça na posição de destino (se houver uma)
		 Piece capturedPiece = board.removePiece(target);
		 // Coloca a peça movida na posição de destino
		 board.placePiece(p, target);
		 
		 // Se uma peça foi capturada (não é null)
		 if(capturedPiece != null) {
			 // Remove a peça capturada da lista de peças em jogo
			 piecesOnTheBoard.remove(capturedPiece);
			 // Adiciona a peça capturada à lista de peças capturadas
			 capturedPieces.add(capturedPiece);
		 }
		 
		 //Movimentos especiais King side rook
		 if(p instanceof King && target.getColumn() == source.getColumn() + 2) {
			 // King-side castling: rook is 3 columns to the right and moves to king column +1
			 Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			 Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			 ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			 board.placePiece(rook, targetT);
			 rook.increaseMoveCount();
		 }
		 
		//Movimentos especiais Queen side rook
		 if(p instanceof King && target.getColumn() == source.getColumn() - 2) {
			 // Queen-side castling: rook is 4 columns to the left and moves to king column -1
			 Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			 Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			 ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			 board.placePiece(rook, targetT);
			 rook.increaseMoveCount();
		 }
		 
		//Movimentos especiais en passant
		 if(p instanceof Pawn) {
			 if(source.getColumn() != target.getColumn() && capturedPiece == null) {
				 Position pawnPosition;
				 if(p.getColor() == Color.WHITE) {
					 pawnPosition = new Position(target.getRow() + 1, target.getColumn());
				 }
				 else {
					 pawnPosition = new Position(target.getRow() - 1, target.getColumn());
				 }
				 capturedPiece = board.removePiece(pawnPosition);
				 capturedPieces.add(capturedPiece);
				 piecesOnTheBoard.remove(capturedPiece);
			 }
		 }
		 
		 // Retorna a peça capturada (pode ser null)
		 return capturedPiece;
	}
	
	// Método privado que desfaz um movimento que foi feito
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		// Remove a peça que foi movida para a posição de destino
		ChessPiece p = (ChessPiece)board.removePiece(target);
		
		p.decreaseMoveCount();
		
		// Coloca a peça de volta na posição de origem
		board.placePiece(p, source);
		
		// Se havia uma peça capturada neste movimento
		if(capturedPiece != null) {
			// Coloca a peça capturada de volta na posição de destino
			board.placePiece(capturedPiece, target);
			
			// Remove a peça capturada da lista de peças capturadas
			capturedPieces.remove(capturedPiece);
			// Coloca a peça capturada de volta na lista de peças em jogo
			piecesOnTheBoard.add(capturedPiece);
		}
		
		//Movimentos especiais King side rook
		 if(p instanceof King && target.getColumn() == source.getColumn() + 2) {
			 Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			 Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			 ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			 board.placePiece(rook, sourceT);
			 // Ao desfazer um roque, devemos decrementar o contador de movimentos do rook
			 rook.decreaseMoveCount();
		 }
		 
		//Movimentos especiais Queen side rook
		 if(p instanceof King && target.getColumn() == source.getColumn() - 2) {
			 Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			 Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			 ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			 board.placePiece(rook, sourceT);
			 rook.decreaseMoveCount();
		 }
		 
		//Movimentos especiais en passant
		 if(p instanceof Pawn) {
			 if(source.getColumn() != target.getColumn() && capturedPiece == enPassantVunerable) {
				 ChessPiece pawn = (ChessPiece)board.removePiece(target);
				 Position pawnPosition;
				 if(p.getColor() == Color.WHITE) {
					 pawnPosition = new Position(3, target.getColumn());
				 }
				 else {
					 pawnPosition = new Position(4, target.getColumn());
				 }
				 board.placePiece(pawn,pawnPosition);
				 
			 }
		 }
	}
	
	// Método privado que valida se a posição de origem é válida para um movimento
	private void validateSourcePosition(Position position) {
		// Se não há peça na posição de origem
		if(!board.thereIsAPiece(position)) {
			// Lança uma exceção
			throw new ChessException("Não há peça na posição de origem.");
		}
		// Se a peça na posição de origem não pertence ao jogador atual
		if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
			// Lança uma exceção
			throw new ChessException("A peça escolhida não é sua.");
		}
		// Se a peça não possui nenhum movimento possível
		if(!board.piece(position).isThereAnyPossibleMove()) {
			// Lança uma exceção
			throw new ChessException("Não há movimentos possíveis para a peça escolhida.");
		}
	}
	
	// Método privado que valida se o movimento para a posição de destino é permitido
	private void validateTargetPosition(Position source, Position target) {
		// Se a peça não pode se mover para a posição de destino
		if(!board.piece(source).possibleMove(target)) {
			// Lança uma exceção
			throw new ChessException("A peça escolhida não pode se mover para a posição de destino.");
		}
	}
	
	// Método privado que passa a jogada para o próximo jogador
	private void nextTurn() {
		// Incrementa o número da jogada em 1
		turn++;
		// Alterna o jogador atual: se era WHITE passa para BLACK, se era BLACK passa para WHITE
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	// Método privado que retorna a cor do oponente de um jogador especificado
	private Color opponent(Color color) {
		// Se a cor é WHITE, retorna BLACK; se é BLACK, retorna WHITE
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	// Método privado que encontra e retorna o rei de uma determinada cor
	private ChessPiece king(Color color) {
		// Usa stream para filtrar apenas as peças da cor especificada
		// Cria uma lista contendo apenas as peças daquela cor
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); 
		
		// Loop que itera sobre as peças filtradas
		for(Piece p : list){
			// Retorna a primeira peça encontrada (assumindo ser o rei)
			// Na prática deveria verificar se é uma instância de King, mas assumimos que há um rei
			if(p instanceof King) {
				return (ChessPiece)p;
			}
		}
		// Se não encontrou nenhum rei, lança uma exceção indicando erro crítico no jogo
		throw new IllegalStateException("Não há rei da cor " + color + " no tabuleiro!");
  	}
	
	// Método privado que verifica se o rei de uma cor está em xeque
	private boolean testCheck(Color color) {

		Position kingPosition = king(color).getChessPosition().toPosition();
		// Usa stream para obter todas as peças do oponente
		// Filtra apenas as peças da cor oposta e cria uma lista
		List<Piece> opponentPieces = piecesOnTheBoard.stream()
			.filter(x -> ((ChessPiece)x).getColor() == opponent(color))
			.collect(Collectors.toList());
		
		// Loop que itera sobre cada peça do oponente
		for(Piece p : opponentPieces) {
			// Obtém a matriz de movimentos possíveis da peça do oponente
			boolean[][] mat = p.possibleMoves();
			// Se a peça do oponente pode atacar a posição onde o rei está
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				// Retorna true indicando que o rei está em xeque
				return true;
			}
		}
		// Se nenhuma peça do oponente pode atacar o rei, retorna false
		return false;
		
	}
	
	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		
		// Iterar sobre as peças da cor 'color' (corrigido: antes usava opponent(color))
		List<Piece> list = piecesOnTheBoard.stream()
			.filter(x -> ((ChessPiece) x).getColor() == color)
			.collect(Collectors.toList());
		
		for(Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					if(mat[i][j]) {
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if(!testCheck) {
							return false;
						}
					}
				}
			}
		}
		
		return true;
	}
	
	// Método privado que coloca uma nova peça no tabuleiro
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		// Converte a notação de xadrez para coordenadas do tabuleiro e coloca a peça
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		// Adiciona a peça à lista de peças em jogo
		piecesOnTheBoard.add(piece);
	}
	
	// Método privado que configura as peças nas posições iniciais de uma partida de xadrez
	private void initialSetup() {
		
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE, this));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('g', 1, new Knight(board, Color.WHITE));
		placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));		
		placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));
		
		
		placeNewPiece('a', 8, new Rook(board, Color.BLACK));
		placeNewPiece('e', 8, new King(board, Color.BLACK, this));
		placeNewPiece('d', 8, new Queen(board, Color.BLACK));
		placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('b', 8, new Knight(board, Color.BLACK));
		placeNewPiece('g', 8, new Knight(board, Color.BLACK));
		placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));		
		placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
		
	}
}
