package application.board;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.*;
import Resource.Resource;
import Resource.Sprites;
import application.AppManager;
import application.SoundManager;
import application.menu.MyButton;
import java.awt.Point;
import java.util.ArrayList;

public class BoardPane extends GridPane {
	private ObservableList<BoardCell> boardCellList = FXCollections.observableArrayList();

	private Cell[][] cellMap;
	private static final int row = 8;
	private static final int column = 8;
	private static final Color whiteTile = new Color((double) 200 / 255, (double) 200 / 255, (double) 200 / 255, 1);
	private static final Color blackTile = new Color((double) 89 / 255, (double) 89 / 255, (double) 89 / 255, 1);
	private BoardCell bc; // use for creating board cell
	private Point currentSelectedPoint; // contain point of selected entity
	private ArrayList<Point> currentSelectedMoveList; // contain move list of selected entity
	private boolean move; // check whether the entity move
	private String promotionPiece;

	public BoardPane(String gameType) {
		super();
		GameController.InitializeMap(gameType);
		this.cellMap = GameController.getDisplayCellMap();
		createBoardCell();
		setBoardCellListener();
	}

	private void createBoardCell() {
		for (int i = 0; i < row; i++) { // Point (y,x) => (i,j)
			for (int j = 0; j < column; j++) {
				if ((i + j) % 2 == 0) {
					bc = new BoardCell(cellMap[i][j], new Point(i, j), whiteTile);
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
					try {
						addOnClickHandler(bc);
					} catch (Exception e1) {// NullEntityException WrongPieceException IsPromotingException
						if (!(e1 instanceof NullPointerException)) {
							SoundManager.playWrongSelected();
							AppManager.displayMessage(e1.getMessage());
						}
					}
				}
			});
		}
	}

	// add handler for board cell
	private void addOnClickHandler(BoardCell myBoardCell) throws Exception {
		if (myBoardCell.isMoveable()) {
			GameController.startAnimation(currentSelectedPoint, myBoardCell.getP());
			updateBoard();

			Thread thread = new Thread(() -> {
				try {
					Thread.sleep(330); // wait for moving animation to be finished
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							GameController.continueMove();
							AppManager.removeTransitionCanvas();
							move = true;
							currentSelectedPoint = null;
							if (GameController.isPromotion()) {
								myBoardCell.update();
								AppManager.showPromotion();
							} else {
								AppManager.getStatusDisplay(GameController.getTurn()).endTurn();
								GameController.nextTurn();
								checkEndGame();
								updateBoard();
								AppManager.getStatusDisplay(GameController.getTurn()).startTurn();
							}
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			thread.start();

		} else {
			updateBoard();
			currentSelectedMoveList = GameController.moveList(myBoardCell.getP());
			if (myBoardCell.hasEntity() && GameController.isTurn(myBoardCell.getP(), GameController.getTurn())) {
				SoundManager.playClickingSound();
				if (!myBoardCell.isClicked()) {
					showWalkPath();
					currentSelectedPoint = myBoardCell.getP();
					myBoardCell.setBackgroundTileColor(new Image(myBoardCell.getMyCell().getEntity().getHighlightSymbol()));
					myBoardCell.setClicked(true);
				} else {
					updateBoard();
					myBoardCell.setClicked(false);
					currentSelectedPoint = null;
				}
			}
		}
		if (GameController.isCheck()) {
			AppManager.displayMessage(GameController.getAnotherSide(GameController.getTurn()).toString() + " Check");
		}

	}

	private void checkEndGame() {
		if (GameController.isDraw())
			showEndGameWindow("DRAW!!!\n");
		else if (GameController.isWin())
			showEndGameWindow(GameController.getAnotherSide(GameController.getTurn()).toString() + " WIN!!!\n");
	}

	private void showWalkPath() {
		for (BoardCell bc : boardCellList) {
			if (currentSelectedMoveList.contains(bc.getP())) {
				if (bc.hasEntity())
					bc.setBackgroundTileColor(new Image(Sprites.WALKPATH), new Image(bc.getMyCell().getEntity().getSymbol()));
				else
					bc.setBackgroundTileColor(new Image(Sprites.WALKPATH));
				bc.setMoveable(true);
			}
		}
	}

	public void showEndGameWindow(String text) {
		VBox endBox = new VBox();
		Label endText = new Label(text);
		MyButton returnBtn = new MyButton("Return to Menu", 20);

		endBox.setSpacing(20);
		endBox.setAlignment(Pos.CENTER);
		endBox.setPrefSize(AppManager.getGamePane().getPrefWidth(), AppManager.getGamePane().getPrefHeight());
		endBox.setBackground(new Background(new BackgroundFill(
				new Color((double) 200 / 255, (double) 200 / 255, (double) 200 / 255, 0.6), CornerRadii.EMPTY, Insets.EMPTY)));

		endText.setFont(Font.loadFont(Resource.ROMAN_FONT, 50));
		endText.setTextFill(Color.BLACK);

		returnBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				SoundManager.playClickingSound();
				AppManager.showMenu();
				SoundManager.playMenuBgm();
			}
		});

		endBox.getChildren().addAll(endText, returnBtn);
		AppManager.getGamePane().getChildren().add(endBox);
		AppManager.stopTimer();
		SoundManager.playWinningSound();
	}

	public void updateBoard() {
		if (move) {
			this.cellMap = GameController.getDisplayCellMap();
			for (BoardCell bc : boardCellList) {
				bc.setMyCell(cellMap[bc.getP().x][bc.getP().y]);
				move = false;
			}
			if (AppManager.getRotateStatus())
				AppManager.rotateBoard();
		}
		for (BoardCell bc : boardCellList) {
			bc.update();
		}
		AppManager.displayMessage("");
	}

	public void rotateBoard() {
		for (BoardCell bc : boardCellList) {
			bc.setP(new Point(7 - bc.getP().x, 7 - bc.getP().y));
			bc.setMyCell(cellMap[bc.getP().x][bc.getP().y]);
		}
	}

	public void promotion(String text) {
		setPromotionPiece(text);
		GameController.promotion(this.getPromotionPiece());
		AppManager.getStatusDisplay(GameController.getTurn()).endTurn();
		GameController.nextTurn();
		AppManager.getStatusDisplay(GameController.getTurn()).startTurn();
		updateBoard();
		GameController.setPromotion(null, Side.EMPTY);
	}

	public void setCurrentSelectedPoint(Point currentSelectedPoint) {
		this.currentSelectedPoint = currentSelectedPoint;
	}

	public String getPromotionPiece() {
		return promotionPiece;
	}

	public void setPromotionPiece(String promotionPiece) {
		this.promotionPiece = promotionPiece;
	}

}