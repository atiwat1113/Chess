package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.*;

import java.awt.Point;
import java.util.ArrayList;

import game.base.Games;

public class BoardPane extends GridPane {
	private ObservableList<BoardCell> boardCellList = FXCollections.observableArrayList();
	
	private Cell[][] cellMap;
	private static final int row = 8;
	private static final int column = 8;
	private static final Color redTile = new Color((double) 200 / 255, (double) 200 / 255, (double) 200 / 255, 1);
	private static final Color blackTile = new Color((double) 89 / 255, (double) 89 / 255, (double) 89 / 255, 1);
	private BoardCell bc;
	private Point currentSelectedPoint;
	private ArrayList<Point> currentSelectedMoveList;
	private Text turnText;
	private boolean rotate;
	private boolean moved;

	public BoardPane(String gameType) {
		super();
		this.rotate = false;
		GameController.InitializeMap(gameType);
		this.turnText = new Text(GameController.getTurn().toString() + " TURN");
		turnText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
		this.cellMap = GameController.getDisplayCellMap();//setting rotate-----
		for (int i = 0; i < row; i++) {// Point (y,x) => (i,j)
			for (int j = 0; j < column; j++) {
				if ((i + j) % 2 == 0) {
					bc = new BoardCell(cellMap[i][j], new Point(i, j), redTile);
				} else {
					bc = new BoardCell(cellMap[i][j], new Point(i, j), blackTile);
				}
				this.boardCellList.add(bc);
				this.add(boardCellList.get(column * i + j), j, i);

			}
		}

		for (BoardCell bc : boardCellList) {
			bc.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					// TODO fill in this method
					try {
						addOnClickHandler(bc);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						System.out.println(e1.getMessage());
//						Alert alert = new Alert(AlertType.WARNING);
//						alert.setTitle("Warning");
//						alert.setHeaderText(null);
//						alert.setContentText(e1.getMessage());
//						alert.showAndWait();
					}
				}
			});
		}
	}

	private void addOnClickHandler(BoardCell myBoardCell) throws Exception {
		// TODO Auto-generated method stub
		// System.out.println("clicked");
		if (myBoardCell.isMoveable()) {
			// currentSelectedPoint = new Point(myBoardCell.getP().y,myBoardCell.getP().x);
			// System.out.println(currentSelectedPoint.toString());
			// System.out.println(currentSelectedMoveList.toString());
			GameController.move(currentSelectedPoint, myBoardCell.getP());//, currentSelectedMoveList);
			moved = true;
			currentSelectedPoint = null;
			//currentSelectedMoveList = null;
			GameController.nextTurn();
			updateBoard(myBoardCell);
			myBoardCell.update();
		} else {
			updateBoard(myBoardCell);
			currentSelectedMoveList = GameController.moveList(myBoardCell.getP());//setting rotate-----
			//GameController.printPointList(currentSelectedMoveList);
			if (myBoardCell.hasEntity() && GameController.isTurn(myBoardCell.getP(), GameController.getTurn())) {
				if (!myBoardCell.isClicked()) {
					for (BoardCell bc : this.getBoardCellList()) {
						if (currentSelectedMoveList.contains(bc.getP())) {
							if (bc.hasEntity())
								bc.setBackgroundTileColor(new Image(Sprites.WALKPATH),
										new Image(bc.getMyCell().getEntity().getSymbol()));
							else
								bc.setBackgroundTileColor(new Image(Sprites.WALKPATH));
							bc.setMoveable(true);
						}
					}
					currentSelectedPoint = myBoardCell.getP();
					myBoardCell.setBackgroundTileColor(new Image(myBoardCell.getMyCell().getEntity().getHighlightSymbol()));
					//currentSelectedMoveList = myBoardCell.getMyCell().getEntity().moveList(GameController.getBoard());
					myBoardCell.setClicked(true);
				} else {
					for (BoardCell bc : this.boardCellList) {
						if (myBoardCell.getMyCell().getEntity().moveList(GameController.getBoard()).contains(bc.getP())) {
							if (bc.hasEntity())
								bc.setBackgroundTileColor(new Image(bc.getMyCell().getEntity().getSymbol()));
							else
								bc.setBackgroundTileColor();
							bc.setMoveable(false);
						}
					}
					currentSelectedPoint = null;
					myBoardCell.setBackgroundTileColor(new Image(myBoardCell.getMyCell().getEntity().getSymbol()));
					//currentSelectedMoveList = null;
					myBoardCell.setClicked(false);
				}
			}
		}

//		if (GameController.isWin()) {
//			Alert alert = new Alert(AlertType.CONFIRMATION);
//			alert.setTitle("End Game");
//			alert.setHeaderText(null);
//			alert.setContentText(GameController.getTurn().toString() + " WIN!!!\nDo you want to exit?");
//			alert.showAndWait().ifPresent(response -> {
//				if (response == ButtonType.OK) {
//					System.exit(0);
//				}
//			});
//		}
		this.turnText.setText(GameController.getTurn().toString() + " TURN");
	}

	private void updateBoard(BoardCell myBoardCell) {
		// TODO Auto-generated method stub
		if (moved) {
			this.cellMap = GameController.getDisplayCellMap();//setting rotate-----------
			for (BoardCell bc : this.getBoardCellList()) {
				if (rotate) bc.setP(new Point(7-bc.getP().x,7-bc.getP().y));
				bc.setMyCell(cellMap[bc.getP().x][bc.getP().y]);	
				moved = false;
			}
		}
		for (BoardCell bc : this.getBoardCellList()) {
			if (!bc.equals(myBoardCell))
				bc.update();
		}
	}

	public ObservableList<BoardCell> getBoardCellList() {
		return boardCellList;
	}

	public Text getTurnText() {
		return turnText;
	}

}
