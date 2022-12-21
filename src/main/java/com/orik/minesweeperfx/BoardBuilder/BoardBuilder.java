package com.orik.minesweeperfx.BoardBuilder;

import com.orik.minesweeperfx.MineSweeper;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;

import java.util.Random;

public class BoardBuilder
{
    // Board size
    int MAX_X;
    int MAX_Y;

    // Internal board-Array for generation
    int[][] internalBoard;
    int bombCount;

    // Finished board build of BoardPieces
    BoardPiece[][] board;

    // Board GUI
    GridPane boardGridPane;
    // Scale contains the size of the pieces
    int sizeOfPieces;

    // EasterEgg - Duck
    boolean ee_Active;
    Image ee_Duck;

    // Function for setting the Board-Settings -> Size, Bombs, Scale and if the ee is activated
    public void setBoardSettings(int max_x, int max_y, int bombCount, int scale, boolean ee )
    {
        // Set board size
        MAX_X = max_x;
        MAX_Y = max_y;

        // Assign new Arrays -> Reset board
        internalBoard = new int[MAX_Y][MAX_X];
        board = new BoardPiece[MAX_Y][MAX_X];

        // Setup EasterEgg - Load GIF-File
        this.ee_Active = ee;
        this.ee_Duck = new Image( MineSweeper.class.getResourceAsStream("Duck.gif") );

        this.sizeOfPieces = scale;
        this.bombCount = bombCount;

        // Generate bombs and corresponding numbers
        generateBombsAndNumbers();

        // Generate GUI
        generateBoardUI();
    }

    public GridPane getBoardPane()
    {
        return boardGridPane;
    }

    // Function for generating finished Gridpane (Board)
    private void generateBoardUI() {
        boardGridPane = new GridPane();

        // Go through whole array
        for( int i = 0 ; i < MAX_Y ; i++ )
        {
            for( int j = 0 ; j < MAX_X ; j++ )
            {
                // Generate piece -> Coordinates, Empty/Number/Bomb and the scale
                BoardPiece piece = new BoardPiece( internalBoard[i][j], sizeOfPieces);

                piece.setOnMouseClicked(mouseEvent -> {
                    BoardPiece currPiece = (BoardPiece) mouseEvent.getSource();

                    // Left-Click
                    if( mouseEvent.getButton() == MouseButton.PRIMARY )
                    {
                        // Get clicked piece and show it
                        currPiece.showPiece();
                    }
                    // Right-Click
                    if( mouseEvent.getButton() == MouseButton.SECONDARY )
                    {
                        // Get clicked piece and mark it
                        currPiece.markPiece();
                    }
                });

                if(ee_Active)
                    piece.setEasterEggImg( new ImageView(ee_Duck) );

                // Add references to UI and internal Array
                board[i][j] = piece;
                boardGridPane.add(piece, j, i);
            }
        }

        // Add nearby pieces to all pieces -> Mainly used for empty pieces => Show adjacent empty/number pieces
        for( int i = 0 ; i < MAX_Y ; i++ )
        {
            for( int j = 0 ; j < MAX_X ; j++ )
            {
                // Add all nearby pieces if they are not null
                // Top lane
                try {
                    board[i][j].addNearbyPiece(board[i - 1][j - 1]);
                } catch ( ArrayIndexOutOfBoundsException ignored) { }
                try {
                    board[i][j].addNearbyPiece(board[i - 1][  j  ]);
                } catch ( ArrayIndexOutOfBoundsException ignored ) { }
                try {
                    board[i][j].addNearbyPiece(board[i - 1][j + 1]);
                } catch ( ArrayIndexOutOfBoundsException ignored ) { }

                // Middle lane
                try {
                    board[i][j].addNearbyPiece(board[  i  ][j - 1]);
                } catch ( ArrayIndexOutOfBoundsException ignored ) { }
                try {
                    board[i][j].addNearbyPiece(board[  i  ][j + 1]);
                } catch ( ArrayIndexOutOfBoundsException ignored ) { }

                // Bottom lane
                try {
                    board[i][j].addNearbyPiece(board[i + 1][j - 1]);
                } catch ( ArrayIndexOutOfBoundsException ignored ) { }
                try {
                    board[i][j].addNearbyPiece(board[i + 1][  j  ]);
                } catch ( ArrayIndexOutOfBoundsException ignored ) { }
                try {
                    board[i][j].addNearbyPiece(board[i + 1][j + 1]);
                } catch ( ArrayIndexOutOfBoundsException ignored ) { }
            }
        }
    }

    // Function for placing random Bombs on the board. Also increases numbers around each bomb -> Possible to play
    private void generateBombsAndNumbers() {
        Random random = new Random();

        // Generate bombCount Bombs //
        int tmpCount = bombCount;
        while( tmpCount > 0 )
        {
            int x = random.nextInt(MAX_X);
            int y = random.nextInt(MAX_Y);

            // Check if bomb is already there => Not here, set bomb ( BOMB = 10 )
            if( internalBoard[y][x] != 10 ) {

                // Set Bomb on Field
                internalBoard[y][x] = 10;

                // Increase nearbyBombs => Try-Catch for OutOfBounds Exception (x/y < 0 or x/y >= MAX_X/Y)
                // Top lane
                try {
                    internalBoard[y - 1][x - 1] = ( internalBoard[y - 1][x - 1] != 10 ) ? internalBoard[y - 1][x - 1] + 1 : internalBoard[y - 1][x - 1];
                } catch( ArrayIndexOutOfBoundsException ignored ) {}
                try {
                    internalBoard[y - 1][  x  ] = ( internalBoard[y - 1][  x  ] != 10 ) ? internalBoard[y - 1][  x  ] + 1 : internalBoard[y - 1][  x  ];
                } catch( ArrayIndexOutOfBoundsException ignored ) {}
                try {
                    internalBoard[y - 1][x + 1] = ( internalBoard[y - 1][x + 1] != 10 ) ? internalBoard[y - 1][x + 1] + 1 : internalBoard[y - 1][x + 1];
                } catch( ArrayIndexOutOfBoundsException ignored ) {}

                // Middle lane
                try {
                    internalBoard[  y  ][x - 1] = ( internalBoard[  y  ][x - 1] != 10 ) ? internalBoard[  y  ][x - 1] + 1 : internalBoard[  y  ][x - 1];
                } catch( ArrayIndexOutOfBoundsException ignored ) {}
                try {
                    internalBoard[  y  ][x + 1] = ( internalBoard[  y  ][x + 1] != 10 ) ? internalBoard[  y  ][x + 1] + 1 : internalBoard[  y  ][x + 1];
                } catch( ArrayIndexOutOfBoundsException ignored ) {}

                // Bottom Lane
                try {
                    internalBoard[y + 1][x - 1] = ( internalBoard[y + 1][x - 1] != 10 ) ? internalBoard[y + 1][x - 1] + 1 : internalBoard[y + 1][x - 1];
                } catch( ArrayIndexOutOfBoundsException ignored ) {}
                try {
                    internalBoard[y + 1][  x  ] = ( internalBoard[y + 1][  x  ] != 10 ) ? internalBoard[y + 1][  x  ] + 1 : internalBoard[y + 1][  x  ];
                } catch( ArrayIndexOutOfBoundsException ignored ) { }
                try {
                    internalBoard[y + 1][x + 1] = ( internalBoard[y + 1][x + 1] != 10 ) ? internalBoard[y + 1][x + 1] + 1 : internalBoard[y + 1][x + 1];
                } catch( ArrayIndexOutOfBoundsException ignored ) {}

                tmpCount--;
            }
        }
    }
}
