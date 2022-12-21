package com.orik.minesweeperfx;

import com.orik.minesweeperfx.BoardBuilder.BoardBuilder;
import com.orik.minesweeperfx.Controllers.MineSweeperEndViewController;
import com.orik.minesweeperfx.Controllers.MineSweeperGameViewController;
import com.orik.minesweeperfx.Controllers.MineSweeperStartViewController;
import com.orik.minesweeperfx.Controllers.MineSweeperStartViewCustomController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class MineSweeper extends Application {

    // Stages
    static Stage menuStage;
    static Stage gameStage;
    static Stage endStage;

    // Loader
    static FXMLLoader startViewLoader;
    static FXMLLoader startViewCustomLoader;
    static FXMLLoader endViewLoader;

    // Scenes
    static Scene startViewScene;
    static Scene startViewCustomScene;
    static Scene gameViewScene;
    static Scene endViewScene;

    // Controller
    static MineSweeperStartViewController svController;
    static MineSweeperStartViewCustomController svcController;
    static MineSweeperEndViewController evController;

    // EasterEgg
    static String ee_string = "";
    static boolean ee_activated = false;

    @Override
    public void start(Stage menuStage) throws IOException {

        // Set given stage as "menuStage"
        MineSweeper.menuStage = menuStage;

        // Setup "static" scenes and stages -> Game-Scene and -View excluded
        setupStages();

        // Set default selection
        svController.setDropDownSelection( 0, 0 );

        // Show start-menu
        menuStage.show();
    }

    void setupStages() throws IOException
    {
        // Load all FXMLLoaders
        startViewLoader = new FXMLLoader(MineSweeper.class.getResource("MineSweeperStartView.fxml"));
        startViewCustomLoader = new FXMLLoader(MineSweeper.class.getResource("MineSweeperStartViewCustom.fxml"));
        endViewLoader = new FXMLLoader(MineSweeper.class.getResource("MineSweeperEndView.fxml"));

        // Create all scenes
        startViewScene = new Scene( startViewLoader.load(), 300, 225 );
        startViewCustomScene = new Scene( startViewCustomLoader.load(), 300, 280 );
        endViewScene = new Scene( endViewLoader.load(), 300, 170 );

        // Set stylesheets
        startViewScene.getStylesheets().add("startViewStyleSheet.css");
        startViewCustomScene.getStylesheets().add("startViewStyleSheet.css");
        endViewScene.getStylesheets().add("endViewStyleSheet.css");

        // Get controller
        svController = startViewLoader.getController();
        svcController = startViewCustomLoader.getController();
        evController = endViewLoader.getController();

        // Set stages -> Scene, Title, Resizable
        menuStage.setScene( startViewScene );
        menuStage.setTitle("MineSweeperFX");
        menuStage.setResizable(false);

        gameStage = new Stage();
        gameStage.setTitle("MineSweeperFX");
        gameStage.setResizable(false);

        endStage = new Stage();
        endStage.setScene( endViewScene );
        endStage.setTitle("MineSweeperFX");
        endStage.setResizable(false);

        // Set icon
        Image icon = new Image(MineSweeper.class.getResourceAsStream("Icon.png"));
        menuStage.getIcons().add( icon );
        gameStage.getIcons().add( icon );
        endStage.getIcons().add( icon );
    }

    // Build Gamescene with settings (size, difficulty), timer, marked bombs and the actual board
    public static void startGame(String size, String diff ) throws IOException
    {
        int xSize, ySize;
        int bombCount;

        // Decide the size of the board -> Depending on the selected choice
        switch( size )
        {
            case "Small":   xSize = 10; ySize = 10; break;
            case "Middle":  xSize = 20; ySize = 10; break;
            case "Large":   xSize = 30; ySize = 15; break;
            case "Custom":  xSize = svcController.getXSize(); ySize = svcController.getYSize(); break;
            default:        xSize = 1; ySize = 1;
        }

        // Decide the bombCount of the board -> Depending on the selected Choice
        switch( diff )
        {
            case "Easy":    bombCount = xSize * ySize / 10; break;
            case "Normal":  bombCount = xSize * ySize / 7; break;
            case "Hard":    bombCount = xSize * ySize / 5; break;
            default:        bombCount = 1;
        }

        // Calculate Scale / Size of pieces //
        int scale = 50;

        Rectangle2D screen = Screen.getPrimary().getBounds();

        // Calculate needed height / width with current settings for the gameScene
        int neededWidth = xSize * 50 + (xSize - 1) * 5 + 10;
        int neededHeight = ySize * 50 + (ySize - 1) * 5 + 65;

        // Reduce scale by 10 until needed space can be displayed on screen
        while( neededWidth > screen.getWidth() || neededHeight > screen.getHeight() )
        {
            scale -= 10;
            neededWidth = xSize * scale + (xSize - 1) * 5 + 10;
            neededHeight = ySize * scale + (ySize - 1) * 5 + 65;
        }

        BoardBuilder builder = new BoardBuilder();
        builder.setBoardSettings( xSize, ySize, bombCount, scale, ee_activated );
        GridPane boardPane = builder.getBoardPane();

        // Create new Scene with stylesheet
        FXMLLoader gameViewLoader = new FXMLLoader(MineSweeper.class.getResource("MineSweeperGameView.fxml"));
        gameViewScene = new Scene(gameViewLoader.load(), neededWidth, neededHeight);
        gameViewScene.getStylesheets().add("gameViewStyleSheet.css");

        // Depending on the scale -> Set font size
        switch (scale) {
            case 50 -> gameViewScene.getRoot().setStyle("-fx-font: 20px 'Aharoni'; -fx-font-weight: bold;");
            case 40 -> gameViewScene.getRoot().setStyle("-fx-font: 17px 'Aharoni'; -fx-font-weight: bold;");
            case 30 -> gameViewScene.getRoot().setStyle("-fx-font: 14px 'Aharoni'; -fx-font-weight: bold;");
            case 20 -> gameViewScene.getRoot().setStyle("-fx-font: 10px 'Aharoni'; -fx-font-weight: bold;");
            default -> gameViewScene.getRoot().setStyle("-fx-font: 7px 'Aharoni'; -fx-font-weight: bold;");
        }

        // Set GridPane from FXML-File to Board-Pane
        MineSweeperGameViewController gvController = gameViewLoader.getController();
        gvController.setBoard( boardPane );

        // Set Infos on Game Scene
        MineSweeperGameLogic gameLogic = new MineSweeperGameLogic( gvController );
        gameLogic.setGameInfo( size, diff, bombCount );

        // If gameStage (Window) is closed -> Stop timer so application can shut down properly
        gameStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                gameLogic.timer.cancel();
            }
        });

        menuStage.close();
        gameStage.setScene( gameViewScene );
        gameStage.show();
    }

    // Show GameOver-Screen with data (Win/Loss, time)
    public static void showEndView( boolean win, int minutes, int seconds ) throws IOException
    {
        evController.setEndInfo( win, minutes, seconds );
        endStage.show();
    }

    // Restart Program -> Close all stage. Show menuStage
    public static void restartProgramm() throws IOException
    {
        // Close remaining windows
        gameStage.close();
        endStage.close();

        menuStage.show();
    }

    // If Size "Custom" is selected -> Change to different FXML File / Scene
    public static void changeToCustomFXML() throws IOException
    {
        // Show customSize-Scene
        menuStage.setScene( startViewCustomScene );

        // Change selection to 3 ("Custom") and to already selected difficulty
        svcController.setDropDownSelection( 3, svController.getDiffSelection() );
    }

    // If Size != "Custom" is selected -> Change to normal FXML File / Scene
    public static void changeToNormalStartFXML() throws IOException
    {
        // Show normal startView-Scene
        menuStage.setScene( startViewScene );

        // Change selection to selected size and difficulty
        svController.setDropDownSelection( svcController.getSizeSelection(), svcController.getDiffSelection() );
    }

    // Handle the EasterEgg
    public static void handleEasterEgg(String character)
    {
        // Add character to string
        ee_string += character;

        // Reset string if "d" is typed
        if( character.equals("d") )
            ee_string = "d";

        // If "duckOn" is typed => Activate easteregg
        if( ee_string.equals("duckon") )
            ee_activated = true;
        // If "duckOff" is typed => Deactivate easteregg
        if( ee_string.equals("duckoff") )
            ee_activated = false;

        // If string is longer than 7 => Reset string
        if( ee_string.length() > 7 )
            ee_string = "";
    }

    public static void main(String[] args) {
        launch();
    }
}