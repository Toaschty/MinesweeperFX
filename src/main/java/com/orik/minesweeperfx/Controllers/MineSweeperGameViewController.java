package com.orik.minesweeperfx.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class MineSweeperGameViewController {

    @FXML
    public Label CurrentSettings;

    @FXML
    public Label Time;

    @FXML
    public Label Bombs;

    @FXML
    public GridPane Board;

    // Copy all childs of generated Board to internal gridpane
    public void setBoard( GridPane b )
    {
        Board.getChildren().addAll( b.getChildren() );
    }

    public void setGameInfoText( String text )
    {
        CurrentSettings.setText(text);
    }

    public void setBombCountText( String text )
    {
        Bombs.setText(text);
    }

    public void setTimerText( String text )
    {
        Time.setText(text);
    }
}