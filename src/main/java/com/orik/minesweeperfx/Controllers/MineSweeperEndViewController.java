package com.orik.minesweeperfx.Controllers;

import com.orik.minesweeperfx.MineSweeper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class MineSweeperEndViewController
{
    @FXML
    public Label Message;

    @FXML
    public Label Time;

    // Set infos about last game ( win / loss and time )
    public void setEndInfo( boolean win, int minutes, int seconds )
    {
        if( win )
            Message.setText("You win!");
        else
            Message.setText("You loose!");

        Time.setText("> " + String.format("%02d:%02d", minutes, seconds) + " <");
    }

    // Show start view again after button is clicked => User can start new Game
    public void showStartView(ActionEvent actionEvent)
    {
        try {
            MineSweeper.restartProgramm();
        } catch ( IOException ignored ) {}
    }
}
