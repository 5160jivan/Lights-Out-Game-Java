
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LightsOut extends Application
{
	private int SIZE;
	private Stage primaryStage;
	private Button buttons[][];
	private GridPane board;

	Insets inset = new Insets(1);
	CornerRadii cornerRadius = new CornerRadii(10);
	final Background OFF = new Background(new BackgroundFill(Color.BLACK, cornerRadius, inset));
	final Background ON = new Background(new BackgroundFill(Color.YELLOW, cornerRadius, inset));

	public static void main(String[] args)
	{
		Application.launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		this.primaryStage = primaryStage;

		//set the title 
		primaryStage.setTitle("Lights Out");

		//text 
		Text text = new Text("Please select a size");

		//create a VBox for selection 
		VBox firstMenu = new VBox(5);
		//create radio buttons
		RadioButton size3 = new RadioButton("3");
		RadioButton size4 = new RadioButton("4");
		RadioButton size5 = new RadioButton("5");
		RadioButton size6 = new RadioButton("6");
		RadioButton size7 = new RadioButton("7");
		RadioButton size8 = new RadioButton("8");
		RadioButton size9 = new RadioButton("9");

		//create toggle group to make radio button selection exclusive 
		ToggleGroup group =  new ToggleGroup();
		size3.setToggleGroup(group);
		size4.setToggleGroup(group);
		size5.setToggleGroup(group);
		size6.setToggleGroup(group);
		size7.setToggleGroup(group);
		size8.setToggleGroup(group);
		size9.setToggleGroup(group);

		//create button 
		Button selectButton = new Button("Create Puzzle");

		//Add all to Vbox
		firstMenu.getChildren().addAll( text, size3, size4, size5, size6, size7, size8, size9, selectButton);
		firstMenu.setAlignment(Pos.CENTER);

		//Register event handlers to radio buttons by using lambda expressions
		//???????may be for loop would make it efficient but not sure????
		//if the size is 3
		size3.setOnAction( e ->
		{
			if(size3.isSelected())
			{
				SIZE = 3;
				System.out.println("The selected size was "+ SIZE);
			}
		});

		//if the size is 4
		size4.setOnAction( e ->
		{
			if(size4.isSelected())
			{
				SIZE = 4;
				System.out.println("The selected size was "+ SIZE);
			}
		});

		//if the size is 5
		size5.setOnAction( e ->
		{
			if(size5.isSelected())
			{
				SIZE = 5;
				System.out.println("The selected size was "+ SIZE);
			}
		});

		//if the size is 6
		size6.setOnAction( e ->
		{
			if(size6.isSelected())
			{
				SIZE = 6;
				System.out.println("The selected size was "+ SIZE);
			}
		});

		//if the size is 7
		size7.setOnAction( e ->
		{
			if(size7.isSelected())
			{
				SIZE = 7;
				System.out.println("The selected size was "+ SIZE);
			}
		});

		//if the size is 8
		size8.setOnAction( e ->
		{
			if(size8.isSelected())
			{
				SIZE = 8;
				System.out.println("The selected size was "+ SIZE);
			}
		});

		//if the size is 9
		size9.setOnAction( e ->
		{
			if(size9.isSelected())
			{
				SIZE = 9;
				System.out.println("The selected size was "+ SIZE);
			}
		});

		//eventHandler for Button 
		selectButton.setOnAction( e ->
		{
			puzzleMaker();
		});

		// set scene of menu
		Scene scene = new Scene(firstMenu, 250, 250);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void puzzleMaker() 
	{
		// start all lights off then go through each one 
		// and call pressed on each one with a 50% chance
		
		primaryStage.close();
		buttons = new Button[SIZE][SIZE];
		Stage puzzleStage = new Stage();
		puzzleStage.setResizable(false);
		puzzleStage.setMinWidth(250);
		puzzleStage.setTitle("Lights Out");
		BorderPane background = new BorderPane();
		HBox buttonBar = new HBox(20);
		board = new GridPane();
		board.setStyle("-fx-background-color: #555555;");
		for(int i = 0; i < SIZE * SIZE; i++)
		{
			Button lightButton = new Button();
			lightButton.setPrefHeight(50);
			lightButton.setPrefWidth(50);
			lightButton.setBackground(OFF);

			final int tempI = i;
			board.add(lightButton, i % SIZE, i / SIZE);
			buttons[i / SIZE][i % SIZE] = lightButton;

			lightButton.setOnAction( e ->
			{
				pressed(tempI / SIZE, tempI % SIZE);
			});

		}
		randomize();
		
		background.setCenter(board);
		background.setBottom(buttonBar);

		Button randomize = new Button("Randomize");
		randomize.setOnAction( e ->
		{
			randomize();
		});
		
		Button chaseLights = new Button("Chase Lights");
		chaseLights.setOnAction( e ->
		{
			chaseLights();
		});

		buttonBar.getChildren().addAll(randomize, chaseLights);
		buttonBar.setAlignment(Pos.CENTER);
		buttonBar.setSpacing(20);
		board.setAlignment(Pos.CENTER);
		Scene puzzleScene = new Scene(background, background.getPrefWidth(), background.getPrefHeight());
		puzzleStage.setScene(puzzleScene);
		puzzleStage.show();
	}

	private void randomize()
	{
		for(int row = 0; row < SIZE; row++) {
			for(int col = 0; col < SIZE; col++)
			{
				if(Math.random() < .5)
					pressed(row,col);
			}
		}
	}

	private void chaseLights()
	{
		//System.out.println("Maybe?");
		for(int row = 0; row < SIZE - 1; row++)
		{
			for(int col = 0; col < SIZE; col++)
			{
				if(!buttons[row][col].getBackground().equals(OFF))
				{
					//System.out.println("Why not?");
					pressed(row + 1,col);
				}
			}
		}
	}

	private void pressed(int row, int col) 
	{
		int check = SIZE - 1;
		for(int i = -1; i < 2; i++)
		{
			if(col + i >= 0 && col + i <= check)
			{
				if(buttons[row][col + i].getBackground().equals(OFF))
				{
					buttons[row][col + i].setBackground(ON);
				}
				else
				{
					buttons[row][col + i].setBackground(OFF);
				}
			}
		}
		for(int i = -1; i < 2; i++)
		{
			if(row + i >= 0 && row + i <= check && i != 0)
			{
				if(buttons[row + i][col].getBackground().equals(OFF))
				{
					buttons[row + i][col].setBackground(ON);
				}
				else
				{
					buttons[row + i][col].setBackground(OFF);
				}
			}
		}
	}

	private void toggleLight(int row, int col)
	{

	}

	private boolean isLightOn(int row, int col)
	{
		//
		boolean result = false;
		return result;
	}



}
