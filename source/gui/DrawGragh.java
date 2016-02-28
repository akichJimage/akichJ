package gui;

import core.Core;
import core.Stdim;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class DrawGragh extends Application {

    public static final boolean DRAWGRAGH_BESIDE =  false;
    public static final boolean DRAWGRAGH_LONG =  true;
    static String WindowName, GraghName;
    static Stdim mainImage = null;
    static boolean LONG_OR_BESIDE;
    public static void draw(Stdim img, String windowName, String graghName, boolean Gragh_Type){
        LONG_OR_BESIDE = Gragh_Type;
        mainImage = img;
        WindowName = windowName;
        GraghName = graghName;
        Application.launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(WindowName);
        stage.setWidth(500);
        stage.setHeight(500);

        if(LONG_OR_BESIDE) {
            NumberAxis xA = new NumberAxis(0, mainImage.getWidth(), mainImage.getWidth() / 10);
            NumberAxis yA = new NumberAxis(0, 255, 25);
            StackedAreaChart<Number, Number> chart
                    = new StackedAreaChart<>(xA, yA);
            if (mainImage.getImg().getType() == 1) {
                int gray = 0;
                XYChart.Series<Number, Number> Gray = new XYChart.Series<>();
                Gray.setName("Gray");
                for (int x = 0; x < mainImage.getWidth(); x++) {
                    for (int y = 0; y < mainImage.getHeight(); y++) {
                        int c = mainImage.getImg().getRGB(x, y);
                        if(Core.r(c) > 0)
                            gray += Core.r(c);
                        else if(Core.g(c) > 0)
                            gray += Core.g(c);
                        else if(Core.b(c) > 0)
                            gray += Core.b(c);
                    }
                    gray /= mainImage.getHeight();
                    Gray.getData().add(new XYChart.Data<>(x, gray));
                    gray = 0;
                }
                chart.getData().add(Gray);
                chart.setTitle(GraghName);

                BorderPane root = new BorderPane();
                root.setCenter(chart);

                stage.setScene(new Scene(root));
                stage.show();
            } else {
                int r, g, b;
                r = g = b = 0;
                XYChart.Series<Number, Number> R = new XYChart.Series<>();
                R.setName("Red");
                for (int x = 0; x < mainImage.getWidth(); x++) {
                    for (int y = 0; y < mainImage.getHeight(); y++) {
                        int c = mainImage.getImg().getRGB(x, y);
                        r += Core.r(c);
                    }
                    r /= mainImage.getHeight();
                    R.getData().add(new XYChart.Data<>(x, r));
                    r = 0;
                }


                XYChart.Series<Number, Number> G = new XYChart.Series<>();
                G.setName("Green");
                for (int x = 0; x < mainImage.getWidth(); x++) {
                    for (int y = 0; y < mainImage.getHeight(); y++) {
                        int c = mainImage.getImg().getRGB(x, y);
                        g += Core.g(c);
                    }
                    g /= mainImage.getHeight();
                    G.getData().add(new XYChart.Data<>(x, g));
                    g = 0;
                }

                XYChart.Series<Number, Number> B = new XYChart.Series<>();
                B.setName("Blue");
                for (int x = 0; x < mainImage.getWidth(); x++) {
                    for (int y = 0; y < mainImage.getHeight(); y++) {
                        int c = mainImage.getImg().getRGB(x, y);
                        b += Core.b(c);
                    }
                    b /= mainImage.getHeight();
                    B.getData().add(new XYChart.Data<>(x, b));
                    b = 0;
                }

                chart.getData().add(R);
                chart.getData().add(G);
                chart.getData().add(B);

                chart.setTitle(GraghName);

                BorderPane root = new BorderPane();
                root.setCenter(chart);

                stage.setScene(new Scene(root));
                stage.show();
            }
        }else{
            NumberAxis xA = new NumberAxis(0, mainImage.getHeight(), mainImage.getHeight() / 10);
            NumberAxis yA = new NumberAxis(0, 255, 25);
            StackedAreaChart<Number, Number> chart
                    = new StackedAreaChart<>(xA, yA);
            if (mainImage.getImg().getType() == 1) {
                int gray = 0;
                XYChart.Series<Number, Number> Gray = new XYChart.Series<>();
                Gray.setName("Gray");
                for (int y = 0; y < mainImage.getHeight(); y++) {
                    for (int x = 0; x < mainImage.getWidth(); x++) {
                        int c = mainImage.getImg().getRGB(x, y);
                        if(Core.r(c) > 0)
                            gray += Core.r(c);
                        else if(Core.g(c) > 0)
                            gray += Core.g(c);
                        else if(Core.b(c) > 0)
                            gray += Core.b(c);
                    }
                    gray /= mainImage.getWidth();
                    Gray.getData().add(new XYChart.Data<>(y, gray));
                    gray = 0;
                }
                chart.getData().add(Gray);
                chart.setTitle(GraghName);

                BorderPane root = new BorderPane();
                root.setCenter(chart);

                stage.setScene(new Scene(root));
                stage.show();
            } else {
                int r, g, b;
                r = g = b = 0;
                XYChart.Series<Number, Number> R = new XYChart.Series<>();
                R.setName("Red");
                for (int y = 0; y < mainImage.getHeight(); y++) {
                    for (int x = 0; x < mainImage.getWidth(); x++) {
                        int c = mainImage.getImg().getRGB(x, y);
                        r += Core.r(c);
                    }
                    r /= mainImage.getWidth();
                    R.getData().add(new XYChart.Data<>(y, r));
                    r = 0;
                }


                XYChart.Series<Number, Number> G = new XYChart.Series<>();
                G.setName("Green");
                for (int y = 0; y < mainImage.getHeight(); y++) {
                    for (int x = 0; x < mainImage.getWidth(); x++) {
                        int c = mainImage.getImg().getRGB(x, y);
                        g += Core.g(c);
                    }
                    g /= mainImage.getWidth();
                    G.getData().add(new XYChart.Data<>(y, g));
                    g = 0;
                }

                XYChart.Series<Number, Number> B = new XYChart.Series<>();
                B.setName("Blue");
                for (int y = 0; y < mainImage.getHeight(); y++) {
                    for (int x = 0; x < mainImage.getWidth(); x++) {
                        int c = mainImage.getImg().getRGB(x, y);
                        b += Core.b(c);
                    }
                    b /= mainImage.getWidth();
                    B.getData().add(new XYChart.Data<>(y, b));
                    b = 0;
                }

                chart.getData().add(R);
                chart.getData().add(G);
                chart.getData().add(B);

                chart.setTitle(GraghName);

                BorderPane root = new BorderPane();
                root.setCenter(chart);

                stage.setScene(new Scene(root));
                stage.show();
            }
        }
    }
}
