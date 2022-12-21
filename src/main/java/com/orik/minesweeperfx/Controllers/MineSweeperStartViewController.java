package com.orik.minesweeperfx.Controllers;

import com.orik.minesweeperfx.MineSweeper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MineSweeperStartViewController {

    @FXML
    public Button StartButton;

    @FXML
    public ComboBox SizeBox;

    @FXML
    public ComboBox DiffBox;

    @FXML
    public VBox ee_listener;

    boolean isCustomView = false;

    // Button "Start"
    public void StartGame(ActionEvent actionEvent)
    {
        String size = (String) SizeBox.getSelectionModel().getSelectedItem();
        String diff = (String) DiffBox.getSelectionModel().getSelectedItem();

        // Show GameScene with selected settings
        try {
            MineSweeper.startGame( size, diff );
        } catch ( IOException ignored ) { }
    }

    // Set selected dropdown item (Size, Difficulty)
    public void setDropDownSelection(int size, int difficulty )
    {
        SizeBox.getSelectionModel().select(size);
        DiffBox.getSelectionModel().select(difficulty);
    }

    public int getSizeSelection()
    {
        return SizeBox.getSelectionModel().getSelectedIndex();
    }

    public int getDiffSelection()
    {
        return DiffBox.getSelectionModel().getSelectedIndex();
    }

    // If the selection changed of size or difficulty
    public void SelectionChanged(ActionEvent actionEvent)
    {
        String selected = (String) SizeBox.getSelectionModel().getSelectedItem();

        // If "Custom" is selected => Change to CustomSizeView
        if( selected.equals("Custom") && !isCustomView )
        {
            isCustomView = true;

            // Change to CustomSizeView
            try {
                MineSweeper.changeToCustomFXML();
            } catch ( IOException ignored ) {}

            return;
        }

        // If something else is selected => Change to StartView
        if( isCustomView )
        {
            isCustomView = false;

            try {
                MineSweeper.changeToNormalStartFXML();
            } catch ( IOException ignored ) {}
        }
    }

    // EasterEgg -> Key Pressed Event
    public void ee_KeyPressed(KeyEvent keyEvent)
    {
        MineSweeper.handleEasterEgg( keyEvent.getCharacter() );
    }
}
