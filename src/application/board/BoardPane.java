package application.board;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.*;
import myException.NullPointException;

import java.util.Scanner;

import Resource.Sprites;
import application.AppManager;
import application.SoundManager;

import java.awt.Point;
import java.util.ArrayList;
import game.base.Board;

import game.base.Games;

public class BoardPane extends GridPane {
	private ObservableList<BoardCell> boardCellList = FXCollections.observableArrayList();
	
	private Cell[][] cellMap;
	private static final int row = 8;
	private static final int column = 8;
	private static final Color redTile = new Color((double) 200 / 255, (double) 200 / 255, (double) 200 / 255, 1);
	private static final Color blackTile = new Color((double) 89 / 255, (double) 89 / 255, (double) 89 / 255, 1);
	private BoardCell bc;
	private BoardCell currenntSelectedBoardCell;
	private Point currentSelectedPoint;
	private ArrayList<Point> currentSelectedMoveList;
	private boolean moved;
	private String promotionPiece;
	private boolean isPromoted;

	public BoardPane(String gameType) {
		super();
		this.isPromoted = false;
		
		GameController.InitializeMap(gameType);
		this.cellMap = GameController.getDisplayCellMap();//setting rotate-----
		createBoardCell();
		setBoardCellListener();
	}

	private void createBoardCell() {
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
	}
	
	private void setBoardCellListener() {
		for (BoardCell bc : boardCellList) {
			bc.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					// TODO fill in this method
					try {
						addOnClickHandler(bc);
					} catch (Exception e1) {//NullEntityException WrongPieceException
						// TODO Auto-generated catch block
						if(!(e1 instanceof NullPointerException)) {
							SoundManager.playWrongSelected();
							AppManager.displayMessage(e1.getMessage());
						}
						//System.out.println(e1.getMessage());
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
		currenntSelectedBoardCell = myBoardCell;
		if (myBoardCell.isMoveable()) {
			// currentSelectedPoint = new Point(myBoardCell.getP().y,myBoardCell.getP().x);
			// System.out.println(currentSelectedPoint.toString());
			// System.out.println(currentSelectedMoveList.toString());
			GameController.move(currentSelectedPoint, myBoardCell.getP());//, currentSelectedMoveList);
			updateBoard(myBoardCell);
			myBoardCell.update();
			if (GameController.isPromotion()) {
				setPromoted(true);
				AppManager.showPromotion();
				//AppManager.hidePromotion();
			}
			moved = true;
			currentSelectedPoint = null;
			//currentSelectedMoveList = null;
			if(!isPromoted) {
				AppManager.getStatusDisplay(GameController.getTurn()).endTurn();
				GameController.nextTurn();
				checkEndGame();
				updateBoard(myBoardCell);
				myBoardCell.update();
				AppManager.getStatusDisplay(GameController.getTurn()).startTurn();
			}
		} 
		
		else {
			updateBoard(myBoardCell);
			currentSelectedMoveList = GameController.moveList(myBoardCell.getP());//setting rotate-----
			//GameController.printPointList(currentSelectedMoveList);
			if (myBoardCell.hasEntity() && GameController.isTurn(myBoardCell.getP(), GameController.getTurn())) {
				//AppManager.playEntitySelected();
				SoundManager.playClickingSound();
				if (!myBoardCell.isClicked()) {
					showWalkPath();
					currentSelectedPoint = myBoardCell.getP();
					myBoardCell.setBackgroundTileColor(new Image(myBoardCell.getMyCell().getEntity().getHighlightSymbol()));
					//currentSelectedMoveList = myBoardCell.getMyCell().getEntity().moveList(GameController.getBoard());
					myBoardCell.setClicked(true);
				} else {
					for (BoardCell bc : this.boardCellList) {
						bc.update();
					}
					currentSelectedPoint = null;
					//currentSelectedMoveList = null;
				}
			}
		}
		//print(GameController.getBoard());//---------------------------------
		if (GameController.isCheck()) {
			//System.out.println(GameController.getAnotherSide(GameController.getTurn()).toString() + " Check");
			AppManager.displayMessage(GameController.getAnotherSide(GameController.getTurn()).toString() + " Check");
		}
		

	}

	private void checkEndGame() {
		if (GameController.isWin()) 
			showEndGameWindow(GameController.getAnotherSide(GameController.getTurn()).toString() + " WIN!!!\nReturn to Menu");
		else if (GameController.isDraw()) 
			showEndGameWindow("DRAW!!!\nReturn to Menu");
	}
	
	private void showWalkPath() {
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
	}
	
	public void showEndGameWindow(String text) {
		AppManager.stopTimer();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("End Game");
		alert.setHeaderText(null);
		alert.setContentText(text);
		alert.showAndWait();
		AppManager.showMenu();
		SoundManager.playMenuBgm();
	}
	
	public void updateBoard(BoardCell myBoardCell) {
		// TODO Auto-generated method stub
		if (moved) {
			this.cellMap = GameController.getDisplayCellMap();//setting rotate-----------
			for (BoardCell bc : this.getBoardCellList()) {
				bc.setMyCell(cellMap[bc.getP().x][bc.getP().y]);	
				moved = false;
			}
			if(AppManager.getRotateStatus()) AppManager.rotateBoard();
		}
		for (BoardCell bc : this.getBoardCellList()) {
			if (!bc.equals(myBoardCell))
				bc.update();
		}
		AppManager.displayMessage("");
	}
	
	public void updateBoard() {
		// TODO Auto-generated method stub
		if (moved) {
			this.cellMap = GameController.getDisplayCellMap();//setting rotate-----------
			for (BoardCell bc : this.getBoardCellList()) {
				bc.setMyCell(cellMap[bc.getP().x][bc.getP().y]);	
				moved = false;
			}
			if(AppManager.getRotateStatus()) AppManager.rotateBoard();
		}
		for (BoardCell bc : this.getBoardCellList()) {
				bc.update();
		}
		AppManager.displayMessage("");
	}
	
	public void rotateBoard() {
		for (BoardCell bc : this.getBoardCellList()) {
			bc.setP(new Point(7-bc.getP().x,7-bc.getP().y));
			bc.setMyCell(cellMap[bc.getP().x][bc.getP().y]);
		}
	}
	
	public void promotion(String text) {
		setPromotionPiece(text);
		GameController.promotion(this.getPromotionPiece());
		setPromoted(false);
		AppManager.getStatusDisplay(GameController.getTurn()).endTurn();
		GameController.nextTurn();
		AppManager.getStatusDisplay(GameController.getTurn()).startTurn();
		updateBoard(this.getCurrenntSelectedBoardCell());
		getCurrenntSelectedBoardCell().update();
		GameController.setPromotion(null, Side.EMPTY);
	}
	
	public boolean isPromoted() {
		return isPromoted;
	}

	public void setPromoted(boolean isPromoted) {
		this.isPromoted = isPromoted;
	}

	public BoardCell getCurrenntSelectedBoardCell() {
		return currenntSelectedBoardCell;
	}

	public void setCurrentSelectedPoint(Point currentSelectedPoint) {
		this.currentSelectedPoint = currentSelectedPoint;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public String getPromotionPiece() {
		return promotionPiece;
	}

	public void setPromotionPiece(String promotionPiece) {
		this.promotionPiece = promotionPiece;
	}

	public ObservableList<BoardCell> getBoardCellList() {
		return boardCellList;
	}

	public static void print(Board board) {
		System.out.println("  -a---b---c---d---e---f---g---h--");
		for (int i = 0; i < 8; i++) {
			System.out.print("" + (8 - i) + " ");
			for (int j = 0; j < 8; j++) {
				if (board.getEntity(new Point(i, j)) == null) {
					System.out.print("---|");
					continue;
				}
				String pr = board.getEntity(new Point(i, j)).getSymbol().substring(0, 4);
				if (pr.substring(2, 4).equals("Kn")) pr=pr.substring(0, 2)+"N";
				System.out.print(pr.substring(0, 3) + "|");
			}
			System.out.println();
		}
	}

}