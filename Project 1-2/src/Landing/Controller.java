package Landing;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * IMPORTANT:
 * NOT ACCURATE ENOUGH, OPEN FOR EXTENSION
 * THIS IS A WAY OF SEEING THE ROCKET VELOCITY
 * DURING THE LANDING
 * IT IS JUST BASED ON EXPERIMENTS
 */
public class Controller {

    @FXML LineChart<Integer,Number> lineChart;
    @FXML Label label;
    public void butAltitudeVelX(){

        XYChart.Series<Integer, Number> lines = new XYChart.Series();
        lines.getData().add(new XYChart.Data(0, 5000));
        lines.getData().add(new XYChart.Data(60, 6000));
        lines.getData().add(new XYChart.Data(120, 7000));
        lines.getData().add(new XYChart.Data(180, 8000));
        lines.getData().add(new XYChart.Data(240, 4000));
        lines.getData().add(new XYChart.Data(300, 2000));
        lines.getData().add(new XYChart.Data(360, 1000));
        lines.getData().add(new XYChart.Data(420, 800));
        lines.getData().add(new XYChart.Data(480, 200));
        lines.setName("xVelocity");
        lineChart.getData().add(lines);
        for(XYChart.Data<Integer, Number> data : lines.getData()){
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    label.setText("Seconds :" + String.valueOf(data.getXValue()) + "\n" + "xVelocity :" + String.valueOf(data.getYValue()));
                }
            });
        }
    }
    public void butAltitudeVelY(){

        XYChart.Series<Integer, Number> lines = new XYChart.Series();
        lines.getData().add(new XYChart.Data(0, 3000));
        lines.getData().add(new XYChart.Data(60, 5000));
        lines.getData().add(new XYChart.Data(120, 6500));
        lines.getData().add(new XYChart.Data(180, 7250));
        lines.getData().add(new XYChart.Data(240, 4000));
        lines.getData().add(new XYChart.Data(300, 2000));
        lines.getData().add(new XYChart.Data(360, 1000));
        lines.getData().add(new XYChart.Data(420, 750));
        lines.getData().add(new XYChart.Data(480, 200));
        lines.setName("yVelocity");
        lineChart.getData().add(lines);

        for(XYChart.Data<Integer, Number> data : lines.getData()){
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    label.setText("Seconds :" + data.getXValue() + "\n" + "yVelocity :" + data.getYValue());
                }
            });
        }
    }

}
