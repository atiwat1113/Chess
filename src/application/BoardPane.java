package application;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import logic.*;
import entity.base.Entity;
import game.base.Board;
import gui.ItemButton;
import gui.SimulationManager;

import java.awt.Point;

public class BoardPane extends GridPane{
	private ObservableList<CellButton> cellButtonList = FXCollections.observableArrayList();
	private Cell[][] cellBoard;
	private static String[] w_p = { "--", "WK", "WQ", "WB", "WN", "WR", "WP" };
	private static String[] b_p = { "--", "BK", "BQ", "BB", "BN", "BR", "BP" };
	public BoardPane () {
		super();
		String[][] nb = { { b_p[5], b_p[4], b_p[3], b_p[2], b_p[1], b_p[3], b_p[4], b_p[5] },
				{ b_p[6], b_p[6], b_p[6], b_p[6], b_p[6], b_p[6], b_p[6], b_p[6] },
				{ b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0] },
				{ b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0] },
				{ w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0] },
				{ w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0] },
				{ w_p[6], w_p[6], w_p[6], w_p[6], w_p[6], w_p[6], w_p[6], w_p[6] },
				{ w_p[5], w_p[4], w_p[3], w_p[2], w_p[1], w_p[3], w_p[4], w_p[5] } };
		String[][] ab = { { b_p[5], b_p[4], b_p[3], b_p[2], b_p[1], b_p[3], b_p[4], b_p[5] },
				{ b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0] },
				{ b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0] },
				{ b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0] },
				{ w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0] },
				{ w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0] },
				{ w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0] },
				{ w_p[5], w_p[4], w_p[3], w_p[2], w_p[1], w_p[3], w_p[4], w_p[5] } };
		GameController.IntializeMap(nb, 8, 8);
		this.cellBoard = GameController.getBoard().getCellmap();
	}
	public Point click() {
		for (CellButton cellButton : cellButtonList) {
			cellButton.setOnAction(e -> {
				return cellButton.getPoint();
			});
		}
	}
}
