import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Launcher extends Application {

    Button traj = new Button("2DTrajectory");
    Button sol = new Button("Solar System");
    Label whichEquation = new Label("Select an option");

    public void start(Stage primaryStage){

        traj.setPrefSize(180, 50);
        whichEquation.setUnderline(true);
        whichEquation.setFont(new Font(35));
        sol.setId("buttonNum");
        traj.setId("buttonNum");

        VBox v = new VBox();

        HBox labelBox = new HBox();
        labelBox.getChildren().add(whichEquation);
        labelBox.setAlignment(Pos.CENTER);

        HBox chooser = new HBox();
        chooser.setAlignment(Pos.CENTER);
        chooser.setSpacing(50);
        chooser.getChildren().addAll(traj, sol);

        v.setAlignment(Pos.CENTER);
        v.setSpacing(20);
        v.getChildren().addAll(labelBox, chooser);

        Scene launcher = new Scene(v, 500, 250);
        launcher.getStylesheets().add("styleChoser.css");

        traj.setOnAction((ActionEvent event) -> {
            Trajectory2D traj2D = new Trajectory2D();
            Stage SortStage = new Stage();
            try {
                traj2D.start(SortStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        sol.setOnAction((ActionEvent event) -> {
            Main main = new Main();
            Stage SortStage = new Stage();
            try {
                main.start(SortStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        primaryStage.setScene(launcher);
        primaryStage.show();

    }
}
