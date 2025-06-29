package pl.umcs.oop.powt2;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pl.umcs.oop.powt2.client.ServerThread;
import pl.umcs.oop.powt2.server.Server;

import java.io.IOException;

public class Controller {
    @FXML
    private TextField addressField;
    @FXML
    private TextField portField;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Canvas canvas;
    @FXML
    private Slider radiusSlider;

    private ServerThread serverThread;


    @FXML
    private void onStartServerClicked() {
        System.err.println("onStartServerClicked not implemented!");
    }

    @FXML
    private void onConnectClicked() {
        String address = this.addressField.getText().trim();
        int port = Integer.parseInt(this.portField.getText().trim());
        try {
            this.serverThread = new ServerThread(address, port);
            this.serverThread.setDrawFunction((Dot dot) -> {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(dot.color());
                gc.fillOval(dot.x(), dot.y(), dot.radius()*2, dot.radius()*2);
            });
            this.serverThread.setDaemon(true);
            this.serverThread.start();
            System.out.println("Połączono!");
        } catch (IOException e) {
            System.err.println("Nie udało się połączyć z serwerem: "+e.getMessage());
        }
    }

    @FXML
    private void onMouseClicked(MouseEvent e) {
        double x = e.getX();
        double y =e.getY();
        Color color = colorPicker.getValue();
        double radius = radiusSlider.getValue();



        if (serverThread != null && serverThread.isAlive()) {
            Dot dot = new Dot(x, y, color, radius);
            serverThread.send(dot);
        } else {
            System.err.println("Połącz się z serwerem!");
        }
    }



}