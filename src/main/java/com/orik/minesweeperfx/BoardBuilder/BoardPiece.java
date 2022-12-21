package com.orik.minesweeperfx.BoardBuilder;

import com.orik.minesweeperfx.MineSweeperGameLogic;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class BoardPiece extends Label
{
    public boolean isBomb;

    int nearbyBombs;

    public boolean visible;

    // Is piece marked?
    Marking marked;

    ArrayList<BoardPiece> nearbyPieces;

    public MineSweeperGameLogic gameLogic;

    // EasterEgg
    ImageView ee_Duck;

    // Used for different marking states
    enum Marking { UNMARKED, BOMB, FLAG }

    BoardPiece( int nearbyBombs, int scale )
    {
        this.nearbyBombs = nearbyBombs;

        // Is piece a bomb?
        if( nearbyBombs == 10 )
            isBomb = true;

        // Default settings -> Not Visible nor marked
        visible = false;
        marked = Marking.UNMARKED;

        nearbyPieces = new ArrayList<>();

        // Label Settings -> Set scale and alignment
        this.setMinHeight(scale);
        this.setMinWidth(scale);
        this.setAlignment(Pos.CENTER);
    }

    // Add given BoardPiece to adjacent Pieces (nearbyPieces)
    public void addNearbyPiece( BoardPiece piece )
    {
        nearbyPieces.add(piece);
    }

    // Sets the text of label to "BOOM" without triggering any other logic
    public void showBombPieceOnly()
    {
        this.setText("💥");
    }

    // Function for handling the reveal of pieces
    public void showPiece()
    {
        if( visible )
            return;

        visible = true;
        hideEasterEggImg();

        // Piece is bomb => Lose game
        if( isBomb ) {
            showBombPieceOnly();
            gameLogic.showEndScreenFinishBoard( false );
            return;
        }

        // Check if marked as Bomb
        if( marked == Marking.BOMB )
            // Decrease markedBomb count in controller -> Not marked anymore (Win condition)
            gameLogic.unmarkBomb();

        // GameLogic -> Piece is now visible (Every piece needs to be visible for winning)
        gameLogic.pieceVisible();
        gameLogic.checkIfWon();
        this.setDisable(true);

        if( nearbyBombs > 0 ) {
            this.setText(Integer.toString(nearbyBombs));
            return;
        }

        this.setText("");

        // Show all nearby pieces
        for ( BoardPiece piece : nearbyPieces ) {
            piece.showPiece();
        }
    }

    // Used for marking pieces
    public void markPiece()
    {
        if( visible )
            return;

        // If piece is currently unmarked
        if( marked == Marking.UNMARKED ) {
            this.setText("X");

            // EasterEgg -> Hide image
            hideEasterEggImg();

            // Increase markedBomb count in controller
            gameLogic.markBomb();

            // If piece is truly a bomb => Increase "True Bomb" count
            if( isBomb )
                gameLogic.markedTrueBomb();

            marked = Marking.BOMB;
        }
        else if ( marked == Marking.BOMB )
        {
            this.setText("?");

            // Decrease markedBomb count in controller
            gameLogic.unmarkBomb();

            // If piece is truly a bomb => Decrease "True Bomb" count
            if( isBomb )
                gameLogic.unmarkTrueBomb();

            marked = Marking.FLAG;
        } else {
            this.setText("");

            // EasterEgg -> Show image
            showEasterEggImg();
            marked = Marking.UNMARKED;
        }
    }

    // EasterEgg -> Setup Image
    public void setEasterEggImg(ImageView imgView )
    {
        // Scale down ImgView
        imgView.setFitHeight(50);
        imgView.setFitWidth(50);
        ee_Duck = imgView;

        // Show Image
        showEasterEggImg();
    }

    // EasterEgg -> Show Image
    public void showEasterEggImg()
    {
        this.setGraphic(ee_Duck);
    }

    // EasterEgg -> Hide Image
    public void hideEasterEggImg()
    {
        this.setGraphic( null );
    }
}
