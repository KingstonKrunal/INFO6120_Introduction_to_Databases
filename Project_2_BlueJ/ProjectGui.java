import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import java.sql.*;

/**
 * GUI for Project1 to interact with sakila.db
 *
 * @author Krunal Shah
 */
public class ProjectGui extends Application
{
    private static final int WIDTH = 500;
    private static final int HEIGHT = 700;
    
    Text title = new Text("Project1 GUI");
    
    Label fNLabel = new Label("First Name");
    TextField firstName = new TextField();
    
    Label lNLabel = new Label("Last Name");
    TextField lastName = new TextField();
    
    Button addBtn = new Button("Add");
    Button remBtn = new Button("Remove");
    
    Label status = new Label();
    
    Label actors = new Label("Actors list");
    Button refresh = new Button("Fetch list");
    ListView actorList = new ListView();
    
    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage)
    {        
        // Linking css class names to the JavaFX objects
        title.getStyleClass().add("title");

        fNLabel.getStyleClass().add("label");
        lNLabel.getStyleClass().add("label");
        actors.getStyleClass().add("label");

        addBtn.getStyleClass().add("btn");
        remBtn.getStyleClass().add("btn");
        refresh.getStyleClass().add("btn");

        // Create a new grid pane
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(10);
        
        // Add components to the grid pane
        pane.add(title, 0, 0, 1, 1);
        
        pane.add(fNLabel, 0, 1, 1, 1);
        pane.add(firstName, 1, 1, 1, 1);
        
        pane.add(lNLabel, 0, 2, 1, 1);
        pane.add(lastName, 1, 2, 1, 1);
        
        pane.add(addBtn, 0, 3, 1, 1);
        pane.add(remBtn, 1, 3, 1, 1);
        
        pane.add(status, 0, 4, 2, 1);
        
        pane.add(actors, 0, 5, 1, 1);
        pane.add(refresh, 1, 5, 1, 1);
        pane.add(actorList, 0, 6, 2, 1);
        
        // add action listeners
        addBtn.setOnAction(this::addActor);
        remBtn.setOnAction(this::removeActor);
        refresh.setOnAction(this::refreshList);
        
        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene scene = new Scene(pane);
        stage.setTitle("Project1 GUI");
        scene.getStylesheets().add("Style.css");
        stage.setScene(scene);
        
        // Show the Stage (window)
        stage.show();
    }
    
    /**
     * This will be executed when the add button is clicked
     */
    private void addActor(ActionEvent event)
    {
        try{
            Connection cn = DriverManager.getConnection("jdbc:sqlite:sakila.db");
            Statement st = cn.createStatement();
            if(firstName.getText().isEmpty() || lastName.getText().isEmpty()){
                status.setText("Please enter a first and last name");
                status.setTextFill(javafx.scene.paint.Color.RED);
            }
            else{
                String sql = "INSERT INTO actor (first_name, last_name) VALUES ('" + firstName.getText() + "', '" + lastName.getText() + "')";
                int added = st.executeUpdate(sql);
                if(added > 0){
                    status.setText("Actor added");
                    status.setTextFill(javafx.scene.paint.Color.GREEN);
                    firstName.clear();
                    lastName.clear();
                    refreshList(event);
                }
                else{
                    status.setText("Actor not added");
                    status.setTextFill(javafx.scene.paint.Color.RED);
                }
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * This will be executed when the remove button is clicked
     */
    private void removeActor(ActionEvent event)
    {
        try{
            Connection cn = DriverManager.getConnection("jdbc:sqlite:sakila.db");
            Statement st = cn.createStatement();
            if(firstName.getText().isEmpty() || lastName.getText().isEmpty()){
                status.setText("Please enter a first and last name");
                status.setTextFill(javafx.scene.paint.Color.RED);
            }
            else{
                String sql = "DELETE FROM actor WHERE first_name = '" + firstName.getText() + "' AND last_name = '" + lastName.getText() + "'";
                int deleted = st.executeUpdate(sql);
                if(deleted == 0){
                    status.setText("Actor not found");
                    status.setTextFill(javafx.scene.paint.Color.RED);
                }
                else{
                    status.setText("Actor removed");
                    status.setTextFill(javafx.scene.paint.Color.GREEN);
                    firstName.clear();
                    lastName.clear();
                    refreshList(event);
                }
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * This will be executed when the refresh button is clicked
     */
    private void refreshList(ActionEvent event)
    {
        try{
            Connection cn = DriverManager.getConnection("jdbc:sqlite:sakila.db");
            Statement st = cn.createStatement();
            String sql = "SELECT first_name, last_name FROM actor";
            ResultSet rs = st.executeQuery(sql);
            actorList.getItems().clear();
            while(rs.next()){
                actorList.getItems().add(rs.getString("first_name") + " " + rs.getString("last_name"));
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
