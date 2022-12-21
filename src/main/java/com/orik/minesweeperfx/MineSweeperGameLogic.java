package com.orik.minesweeperfx;

import com.orik.minesweeperfx.BoardBuilder.BoardPiece;
import com.orik.minesweeperfx.Controllers.MineSweeperGameViewController;
import javafx.application.Platform;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MineSweeperGameLogic
{
    // Bomb logic - Count, marked and "True"-bombs (Marked pieces which are bombs)
    int bombCount;
    int markedBombs;
    int trueBombs;

    // Timer
    Timer timer;
    int minutes;
    int seconds;

    // How many pieces are inside the board
    int boardChildCount;
    int visiblePieces;

    MineSweeperGameViewController gvController;

    public MineSweeperGameLogic( MineSweeperGameViewController controller )
    {
        gvController = controller;
    }

    // Set infos about current game ( size, difficulty, bomb count )
    public void setGameInfo( String size, String diff, int bombs )
    {
        gvController.setGameInfoText(size + " - " + diff );
        visiblePieces = 0;

        // Bomb setup
        bombCount = bombs;
        markedBombs = 0;
        trueBombs = 0;
        gvController.setBombCountText("0 | " + bombs );

        // Setup timer
        seconds = 0;
        minutes = 0;

        // Create new Timer
        timer = new Timer();
        // Call run() every second -> Increase "seconds" by one -> Update Timer
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    seconds++;

                    if( seconds == 60 )
                    {
                        seconds = 0;
                        minutes++;
                    }

                    gvController.setTimerText(String.format("%02d:%02d", minutes, seconds));
                });
            }
        }, 0, 1000);

        // Get count of childs in gridpane
        boardChildCount = gvController.Board.getColumnCount() * gvController.Board.getRowCount();

        // Go through all childs ...
        for( int i = 0 ; i < boardChildCount ; i++ )
        {
            // ... and set variable to this object of this class ( Used by marking bombs )
            BoardPiece p = (BoardPiece) gvController.Board.getChildren().get(i);
            p.gameLogic = this;
        }
    }

    public void markBomb()
    {
        markedBombs++;
        gvController.setBombCountText( markedBombs + " | " + bombCount );
    }

    public void unmarkBomb()
    {
        markedBombs--;
        gvController.setBombCountText( markedBombs + " | " + bombCount );
    }

    // Increase "True Bombs" => Marked pieces is truly a bomb
    public void markedTrueBomb()
    {
        trueBombs++;
        checkIfWon();
    }

    // Reduce "True Bombs"
    public void unmarkTrueBomb()
    {
        trueBombs--;
    }

    public void pieceVisible()
    {
        visiblePieces++;
    }

    // Game Ends -> Show End screen
    public void showEndScreenFinishBoard( boolean win ) {
        // Stop timer thread
        timer.cancel();

        // Go through all childs ...
        for( int i = 0 ; i < boardChildCount ; i++ )
        {
            // ... and set them visible => Not clickable anymore
            BoardPiece piece = (BoardPiece) gvController.Board.getChildren().get(i);
            piece.visible = true;

            if( piece.isBomb )
                piece.showBombPieceOnly();
        }

        // Show the GameOver-Screen
        try {
            MineSweeper.showEndView( win, minutes, seconds );
        } catch ( IOException ignored) {}
    }

    public void checkIfWon()
    {
        // Win game when markedBombs == TrueBombs and all bombs are marked and also all pieces are visible
        if( markedBombs == trueBombs && trueBombs == bombCount && boardChildCount - bombCount == visiblePieces) {
            showEndScreenFinishBoard( true );
        }
    }
}
