package application;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import logic.*;

import java.awt.Point;


public class BoardPane extends GridPane { 
	static ObservableList<BoardCell> boardCellList = FXCollections.observableArrayList();
	private static String[] w_p = { "blank", "W_King.png", "W_Queen.png", "W_Bishop.png", "W_Knight.png", "W_Rook.png", "W_Pawn.png" };
	private static String[] b_p = { "blank", "B_King.png", "B_Queen.png", "B_Bishop.png", "B_Knight.png", "B_Rook.png", "B_Pawn.png" };
	private Cell[][] cellMap;
	private static final int row = 8;
	private static final int column = 8;
	private static final Color redTile = new Color((double)215/255,(double)89/255,(double)89/255,1);
	private static final Color blackTile = new Color((double)89/255,(double)89/255,(double)89/255,1);
	private BoardCell bc;
	
	public BoardPane () {
		super();
		String[][] nb = { { b_p[5], b_p[4], b_p[3], b_p[2], b_p[1], b_p[3], b_p[4], b_p[5] },
				{ b_p[6], b_p[6], b_p[6], b_p[6], b_p[6], b_p[6], b_p[6], b_p[6] },
				{ b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0] },
				{ b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0] },
				{ w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0] },
				{ w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0] },
				{ w_p[6], w_p[6], w_p[6], w_p[6], w_p[6], w_p[6], w_p[6], w_p[6] },
				{ w_p[5], w_p[4], w_p[3], w_p[1], w_p[2], w_p[3], w_p[4], w_p[5] } };
		GameController.IntializeMap(nb);
		this.cellMap = GameController.getBoard().getCellmap();
		for (int i = 0; i < row; i++) {// Point (y,x) => (i,j)
			for (int j = 0; j < column; j++) {
				if ((i+j)%2 == 0) {
					bc = new BoardCell(cellMap[i][j], new Point(i,j), redTile);
				}
				else {
					bc = new BoardCell(cellMap[i][j], new Point(i,j), blackTile);
				}
				this.boardCellList.add(bc);
				this.add(boardCellList.get(column*i+j), j, i);
				
			}
		}
		
		for (BoardCell cell : boardCellList) {
			cell.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					// TODO fill in this method
					addOnClickHandler(cell);
				}
			});
		}
	}
	
	private void addOnClickHandler(BoardCell myBoardCell) {
		// TODO Auto-generated method stub
		System.out.println("clicked");
		for (BoardCell bc : BoardPane.getBoardCellList()) {
			if (!bc.equals(myBoardCell)) bc.update();
		}
		if (myBoardCell.hasEntity()) {
				if (!myBoardCell.isClicked()) {
					for (BoardCell bc : BoardPane.getBoardCellList()) {	
						if (myBoardCell.getMyCell().getEntity().moveList(GameController.getBoard()).contains(bc.getP())) {
							if (bc.hasEntity())
								bc.setBackgroundTileColor(new Image(Sprites.WALKPATH), new Image(bc.getMyCell().getEntity().getSymbol()));
								
							else
								bc.setBackgroundTileColor(new Image(Sprites.WALKPATH));
						}
					}
					myBoardCell.setClicked(true);
				}
				else {
					for (BoardCell bc : BoardPane.boardCellList) {	
						if (myBoardCell.getMyCell().getEntity().moveList(GameController.getBoard()).contains(bc.getP())) {
							if (bc.hasEntity())
								bc.setBackgroundTileColor(new Image(bc.getMyCell().getEntity().getSymbol()));
							else
								bc.setBackgroundTileColor();
						}
					}
					myBoardCell.setClicked(false);
				}
			}
//		if (myBoardCell.hasEntity())
//			myBoardCell.setBackgroundTileColor(new Image(Sprites.WALKPATH), new Image(myBoardCell.getMyCell().getEntity().getSymbol()));
//			
//		else
//			myBoardCell.setBackgroundTileColor(new Image(Sprites.WALKPATH));
	}

	public static ObservableList<BoardCell> getBoardCellList() {
		return boardCellList;
	}
	
	
}
