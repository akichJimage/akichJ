package gui;

import core.Stdim;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.*;
import javafx.scene.layout.VBox;

public class Stdgui extends Application {
  static Stdim mainImage;
  ImageView View = new ImageView();
  static String WindowName;
  public static void showim(Stdim img, String name) {
    WindowName = name;
    mainImage = img.clone();
    Application.launch();
  }
  @Override
  public void start(Stage stage) throws Exception {
    stage.setTitle(WindowName);
    stage.setWidth(mainImage.getWidth());
    stage.setHeight(mainImage.getHeight());
    View.setImage(change(mainImage));
    VBox root = new VBox();
    root.getChildren().addAll(View);
    stage.setScene(new Scene(root));
    stage.show();
  }
  private static WritableImage change(Stdim img) {
    WritableImage newimg = SwingFXUtils.toFXImage(img.clone().getImg(), null);
    return newimg;
  }
}
