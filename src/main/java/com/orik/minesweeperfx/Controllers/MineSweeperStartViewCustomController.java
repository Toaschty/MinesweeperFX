package com.orik.minesweeperfx.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class MineSweeperStartViewCustomController extends MineSweeperStartViewController
{
    @FXML
    public TextField XSizeField;

    @FXML
    public TextField YSizeField;

    public int getXSize()
    {
        return Integer.parseInt( XSizeField.getText() );
    }

    public int getYSize()
    {
        return Integer.parseInt( YSizeField.getText() );
    }

    // X- / Y-Textfield changed
    public void XFieldChanged(KeyEvent keyEvent)
    {
        try {
            // Try to parse string to int
            int x = Integer.parseInt( XSizeField.getText() );

            // Check if x is outside possible values -> Set new value
            if( x > 100 )
                XSizeField.setText("100");
            if( x < 5 )
                XSizeField.setText("5");
        }
        catch ( NumberFormatException e )
        {
            // Delete all non numeric values
            XSizeField.setText(XSizeField.getText().replaceAll("[^\\d.]", ""));
        }

        // Set cursor to end
        XSizeField.end();
    }

    public void YFieldChanged(KeyEvent keyEvent)
    {
        try {
            // Try to parse string to int
            int y = Integer.parseInt( YSizeField.getText() );

            // Check if x is outside possible values
            if( y > 60 )
                YSizeField.setText("60");
            if( y < 5 )
                YSizeField.setText("5");
        }
        catch ( NumberFormatException e )
        {
            // Delete all non numeric values
            YSizeField.setText(YSizeField.getText().replaceAll("[^\\d.]", ""));
        }

        // Set cursor to end
        YSizeField.end();
    }
}
