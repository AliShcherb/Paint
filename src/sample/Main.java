
package sample;


import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        Image imageCircle = new Image(getClass().getResourceAsStream("circle.png"));
        Image imageSquare = new Image(getClass().getResourceAsStream("square.png"));
        Image imageEllips = new Image(getClass().getResourceAsStream("oval.png"));
        Image imageTriangle = new Image(getClass().getResourceAsStream("triangle.png"));
        Image imageHexagon = new Image(getClass().getResourceAsStream("hexagon.png"));

        //Створюю кнопки-перемикачі
        ToggleButton draw_btn = new ToggleButton("Draw");
        ToggleButton rubber_btn = new ToggleButton("Rubber");
        ToggleButton dotted_line_btn = new ToggleButton("Dotted Line");
        ToggleButton text_btn = new ToggleButton("Text");
        ToggleButton line_btn = new ToggleButton("Line");
        ToggleButton rect_btn = new ToggleButton("",new ImageView(imageSquare));
        ToggleButton circle_btn = new ToggleButton("",new ImageView(imageCircle));
        ToggleButton ellips_lebtn = new ToggleButton("",new ImageView(imageEllips));
        ToggleButton triangle_btn = new ToggleButton("",new ImageView(imageTriangle));
        ToggleButton hexagon_btn = new ToggleButton("",new ImageView(imageHexagon));
        Button undo = new Button("Undo");
        Button redo = new Button("Redo");
        Button save = new Button("Save");
        Button open = new Button("Open");

        ToggleGroup tools = new ToggleGroup();

        ToggleButton[] figure = {hexagon_btn,triangle_btn,
                rect_btn, circle_btn, ellips_lebtn, text_btn};

        for (ToggleButton tool : figure) {
            tool.setMinWidth(65);
            tool.setMinHeight(40);
            tool.setToggleGroup(tools);
            tool.setCursor(Cursor.HAND);
            tool.setStyle( "-fx-font-size: 13px");
        }
        ToggleButton[] first = {draw_btn,line_btn,dotted_line_btn,rubber_btn,draw_btn,line_btn};
        for (ToggleButton tool : first) {
            tool.setMinWidth(130);
            tool.setToggleGroup(tools);
            tool.setCursor(Cursor.HAND);
            tool.setStyle( "-fx-font-size: 13px");
        }
        Button[] save_open = {save, open,undo, redo};

        for(Button btn : save_open) {
            btn.setMinWidth(65);
            btn.setCursor(Cursor.HAND);
            btn.setTextFill(Color.WHITE);
            btn.setStyle("-fx-background-color: #863562;" +
                    "-fx-font-size: 13px");
        }
        //Штуки, щоб обирати колір
        ColorPicker cpLine = new ColorPicker(Color.BLACK);
        cpLine.setMaxWidth(60);
        ColorPicker cpFill = new ColorPicker(Color.TRANSPARENT);
        cpFill.setMaxWidth(60);
        ColorPicker cpStroke = new ColorPicker(Color.BLACK);
        cpStroke.setMaxWidth(60);

        TextArea text = new TextArea();
        text.setPrefRowCount(1);
        text.setMaxHeight(40);
        text.setMaxWidth(65);

        //Слайдери
        Slider slider = new Slider(1, 50, 3);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMaxWidth(130);

        Slider dash_slider = new Slider(1, 50, 3);
        dash_slider.setShowTickLabels(true);
        dash_slider.setShowTickMarks(true);
        dash_slider.setMaxWidth(130);

        Slider stroke_slider = new Slider(1, 50, 3);
        stroke_slider.setShowTickLabels(true);
        stroke_slider.setShowTickMarks(true);
        stroke_slider.setMaxWidth(130);

        //Написи
        int font = 14;
        Label line_color = new Label("Line Color: ");
        Label fill_color = new Label("  Fill Color:     ");
        Label line_width_label = new Label("3.0");
        Label line_width_name = new Label("Line Width:  ");
        line_width_name.setStyle("-fx-font-size:" +font+"px;");
        line_width_label.setStyle("-fx-font-size:" +font+"px;");
        Label dash_label = new Label("5.0");
        dash_label.setStyle("-fx-font-size:" +font+"px;");
        Label dash_width_name = new Label("Dash Width:  ");
        dash_width_name.setStyle("-fx-font-size:" +font+"px;");
        Label stroke_width_name = new Label("Stroke Width:  ");
        stroke_width_name.setStyle("-fx-font-size:" +font+"px;");
        Label stroke_label = new Label("5.0");
        stroke_label.setStyle("-fx-font-size:" +font+"px;");
        Label stroke_color = new Label("Stroke Color: ");

        //Компоную елементи
        HBox btns4 = new HBox(2);
        btns4.getChildren().addAll(triangle_btn,circle_btn);
        HBox btns1=new HBox(2);
        btns1.getChildren().addAll(text_btn,text);
        HBox btns2=new HBox(2);
        btns2.getChildren().addAll(undo, redo);
        HBox btns3 = new HBox(2);
        btns3.getChildren().addAll(open, save);
        HBox btns5 = new HBox(2);
        btns5.getChildren().addAll(rect_btn,hexagon_btn);
        HBox btns6=new HBox(2);
        btns6.getChildren().addAll(line_width_name, line_width_label);
        HBox btns7=new HBox(2);
        btns7.getChildren().addAll(dash_width_name,dash_label);
        HBox btns8=new HBox(2);
        btns8.getChildren().addAll(line_color,cpLine);
        HBox btns9=new HBox(2);
        btns9.getChildren().addAll(fill_color, cpFill);
        HBox btns10=new HBox(2);
        btns10.getChildren().addAll(stroke_width_name, stroke_label);
        HBox btns11=new HBox(2);
        btns11.getChildren().addAll(stroke_color, cpStroke);
        VBox btns_right = new VBox(10);
        btns_right.getChildren().addAll(draw_btn,line_btn,btns6,slider,btns8,dotted_line_btn,btns7,dash_slider,
                rubber_btn,btns1,btns4,btns5, ellips_lebtn, btns10,stroke_slider,btns11,btns9,btns2,btns3);
        btns_right.setPadding(new Insets(3));
        btns_right.setStyle("-fx-background-color: #cc8899");
        btns_right.maxWidth(30);

        //"Листок", щоб малювати
        Canvas canvas = new Canvas(1040, 2000);

        //Стеки, щоб додавати елементи
        Stack<Shape> undo_stack = new Stack();
        Stack<Shape> redo_stack = new Stack();
        
        //Створюю елементи
        GraphicsContext gc;
        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(1);
        Line line = new Line();
        Line dotted_line = new Line();
        Rectangle rect = new Rectangle();
        Circle circ = new Circle();
        Ellipse elps = new Ellipse();
        Polygon triangle = new Polygon();
        
           canvas.setOnMousePressed(e->{
            if(draw_btn.isSelected()) {
                gc.setStroke(cpLine.getValue());
                gc.beginPath();
                gc.lineTo(e.getX(), e.getY());
            }
            else if(rubber_btn.isSelected()) {
                double lineWidth = gc.getLineWidth();
                gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
            }
            else if(dotted_line_btn.isSelected()) {
                gc.setStroke(cpLine.getValue());
                dotted_line.setStartX(e.getX());
                dotted_line.setStartY(e.getY());
            }
            else if(line_btn.isSelected()) {
                gc.setStroke(cpLine.getValue());
                gc.setLineDashes();
                line.setStartX(e.getX());
                line.setStartY(e.getY());
            }
            else if(triangle_btn.isSelected()) {
                gc.setStroke(cpStroke.getValue());
                gc.setFill(cpFill.getValue());
                line.setStartX(e.getX());
                line.setStartY(e.getY());

            }
            else if(hexagon_btn.isSelected()) {
                gc.setStroke(cpStroke.getValue());
                gc.setFill(cpFill.getValue());
                line.setStartX(e.getX());
                line.setStartY(e.getY());

            }
            else if(rect_btn.isSelected()) {
                gc.setStroke(cpStroke.getValue());
                gc.setFill(cpFill.getValue());
                rect.setX(e.getX());
                rect.setY(e.getY());
            }

            else if(circle_btn.isSelected()) {
                gc.setStroke(cpStroke.getValue());
                gc.setFill(cpFill.getValue());
                circ.setCenterX(e.getX());
                circ.setCenterY(e.getY());
            }
            else if(ellips_lebtn.isSelected()) {
                gc.setStroke(cpStroke.getValue());
                gc.setFill(cpFill.getValue());
                elps.setCenterX(e.getX());
                elps.setCenterY(e.getY());
            }
            else if(text_btn.isSelected()) {
                gc.setLineWidth(1);
                gc.setFont(Font.font(slider.getValue()));
                gc.setStroke(cpStroke.getValue());
                gc.setFill(cpFill.getValue());
                gc.fillText(text.getText(), e.getX(), e.getY());
                gc.strokeText(text.getText(), e.getX(), e.getY());
            }
        });

        canvas.setOnMouseDragged(e->{
            if(draw_btn.isSelected()) {
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            }
            else if(rubber_btn.isSelected()){
                double lineWidth = gc.getLineWidth();
                gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
            }
        });

        canvas.setOnMouseReleased(e->{
            if(draw_btn.isSelected()) {
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
                gc.closePath();
            }
            else if(rubber_btn.isSelected()) {
                double lineWidth = gc.getLineWidth();
                gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
            }
            else if(dotted_line_btn.isSelected()) {
                dotted_line.setEndX(e.getX());
                dotted_line.setEndY(e.getY());
                gc.strokeLine(dotted_line.getStartX(), dotted_line.getStartY(), dotted_line.getEndX(), dotted_line.getEndY());

                undo_stack.push(new Line(dotted_line.getStartX(), dotted_line.getStartY(), dotted_line.getEndX(), dotted_line.getEndY()));
            }
            else if(line_btn.isSelected()) {
                line.setEndX(e.getX());
                line.setEndY(e.getY());
                gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());

                undo_stack.push(new Line(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY()));
            }
            else if(triangle_btn.isSelected()) {
                line.setEndX(e.getX());
                line.setEndY(e.getY());
                double end =line.getEndX()-line.getStartX();
                //System.out.println(end);
                double x []={line.getStartX(),line.getEndX(),line.getEndX()-2*end};
                double y []={line.getStartY(),line.getEndY(),line.getEndY()};
                int n =3;
                gc.fillPolygon(x,y,n);
                gc.strokePolygon(x,y,n);
                undo_stack.push(new Polygon(line.getStartX(),line.getStartY(),line.getEndX(), line.getEndY(),line.getEndX()-2*end, line.getEndY()));
            }
            else if(hexagon_btn.isSelected()) {
                line.setEndX(e.getX());
                line.setEndY(e.getY());

                double ax = line.getStartX();
                double ay = line.getStartY();

                double bx = line.getEndX();
                double by = line.getEndY();

                double cx = line.getEndX()+2*(line.getEndX()-line.getStartX());
                double cy = line.getEndY();

                double dx = line.getEndX()+3*(line.getEndX()-line.getStartX());
                double dy = line.getStartY();

                double ex = line.getEndX()+2*(line.getEndX()-line.getStartX());
                double ey = line.getStartY()+(line.getStartY()-line.getEndY());

                double fx = line.getEndX();
                double fy = line.getStartY()+(line.getStartY()-line.getEndY());
                //System.out.println(end);
                double x []={ax,bx,cx,dx,ex,fx};
                double y []={ay,by,cy,dy,ey,fy};
                int n =6;
                gc.fillPolygon(x,y,n);
                gc.strokePolygon(x,y,n);
                undo_stack.push(new Polygon(ax,ay,bx,by,cx,cy,dx,dy,ex,ey,fx,fy));
            }
            else if(rect_btn.isSelected()) {
                rect.setWidth(Math.abs((e.getX() - rect.getX())));
                rect.setHeight(Math.abs((e.getY() - rect.getY())));
                if(rect.getX() > e.getX()) {
                    rect.setX(e.getX());
                }
                if(rect.getY() > e.getY()) {
                    rect.setY(e.getY());
                }

                gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

                undo_stack.push(new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight()));

            }
            else if(circle_btn.isSelected()) {
                circ.setRadius((Math.abs(e.getX() - circ.getCenterX()) + Math.abs(e.getY() - circ.getCenterY())) / 2);

                if(circ.getCenterX() > e.getX()) {
                    circ.setCenterX(e.getX());
                }
                if(circ.getCenterY() > e.getY()) {
                    circ.setCenterY(e.getY());
                }

                gc.fillOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());
                gc.strokeOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());

                undo_stack.push(new Circle(circ.getCenterX(), circ.getCenterY(), circ.getRadius()));
            }
            else if(ellips_lebtn.isSelected()) {
                elps.setRadiusX(Math.abs(e.getX() - elps.getCenterX()));
                elps.setRadiusY(Math.abs(e.getY() - elps.getCenterY()));

                if(elps.getCenterX() > e.getX()) {
                    elps.setCenterX(e.getX());
                }
                if(elps.getCenterY() > e.getY()) {
                    elps.setCenterY(e.getY());
                }


                gc.strokeOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());
                gc.fillOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());

                undo_stack.push(new Ellipse(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY()));
            }
            redo_stack.clear();
            Shape lastUndo = undo_stack.lastElement();
            lastUndo.setFill(gc.getFill());
            lastUndo.setStroke(gc.getStroke());
            lastUndo.setStrokeWidth(gc.getLineWidth());

        });
        //Передача значень кольорів
        cpLine.setOnAction(e->{
            gc.setStroke(cpLine.getValue());
        });
        cpFill.setOnAction(e->{
            gc.setFill(cpFill.getValue());
        });
        cpStroke.setOnAction(e->{
            gc.setFill(cpStroke.getValue());
        });

        //Передача товщини лінії і рамки
        slider.valueProperty().addListener(e->{
            double width = slider.getValue();
            if(text_btn.isSelected()){
                gc.setLineWidth(1);
                gc.setFont(Font.font(slider.getValue()));
                line_width_label.setText(String.format("%.1f", width));
                return;
            }
            line_width_label.setText(String.format("%.1f", width));
            gc.setLineWidth(width);
        });

        dash_slider.valueProperty().addListener(e->{
            double dash_width = dash_slider.getValue();
            if(line_btn.isSelected()){
                gc.setLineDashes(5);
                gc.setFont(Font.font(dash_slider.getValue()));
                dash_label.setText(String.format("%.1f", dash_width));
            }
            dash_label.setText(String.format("%.1f", dash_width));
            gc.setLineDashes(dash_width);
        });
        
        stroke_slider.valueProperty().addListener(e->{
            double width = stroke_slider.getValue();
            if(text_btn.isSelected()){
                gc.setLineWidth(1);
                gc.setFont(Font.font(stroke_slider.getValue()));
                stroke_label.setText(String.format("%.1f", width));
                return;
            }
            stroke_label.setText(String.format("%.1f", width));
            gc.setLineWidth(width);
        });
        
        // Ундо
        undo.setOnAction(e->{
            if(!undo_stack.empty()){
                gc.clearRect(0, 0, 1500, 790);
                Shape removedShape = undo_stack.lastElement();
                if(removedShape.getClass() == Line.class) {
                    Line tempLine = (Line) removedShape;
                    tempLine.setFill(gc.getFill());
                    tempLine.setStroke(gc.getStroke());
                    tempLine.setStrokeWidth(gc.getLineWidth());
                    redo_stack.push(new Line(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(), tempLine.getEndY()));

                }
                else if(removedShape.getClass() == Rectangle.class) {
                    Rectangle tempRect = (Rectangle) removedShape;
                    tempRect.setFill(gc.getFill());
                    tempRect.setStroke(gc.getStroke());
                    tempRect.setStrokeWidth(gc.getLineWidth());
                    redo_stack.push(new Rectangle(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight()));
                }
                else if(removedShape.getClass() == Circle.class) {
                    Circle tempCirc = (Circle) removedShape;
                    tempCirc.setStrokeWidth(gc.getLineWidth());
                    tempCirc.setFill(gc.getFill());
                    tempCirc.setStroke(gc.getStroke());
                    redo_stack.push(new Circle(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius()));

                }
                else if(removedShape.getClass() == Polygon.class) {
                    Polygon tempTriangle = (Polygon) removedShape;
                    tempTriangle.setStroke(gc.getStroke());
                    tempTriangle.setStrokeWidth(gc.getLineWidth());
                    double end =line.getEndX()-line.getStartX();
                    redo_stack.push(new Polygon(line.getStartX(),line.getStartY(),line.getEndX(), line.getEndY(),line.getEndX()-2*end, line.getEndY()));
                }
                else if(removedShape.getClass() == Polygon.class) {
                    Polygon tempHexagon = (Polygon) removedShape;
                    tempHexagon.setStroke(gc.getStroke());
                    tempHexagon.setStrokeWidth(gc.getLineWidth());

                    double ax = line.getStartX();
                    double ay = line.getStartY();

                    double bx = line.getEndX();
                    double by = line.getEndY();

                    double cx = line.getEndX()+2*(line.getEndX()-line.getStartX());
                    double cy = line.getEndY();

                    double dx = line.getEndX()+3*(line.getEndX()-line.getStartX());
                    double dy = line.getStartY();

                    double ex = line.getEndX()+2*(line.getEndX()-line.getStartX());
                    double ey = line.getStartY()+(line.getStartY()-line.getEndY());

                    double fx = line.getEndX();
                    double fy = line.getStartY()+(line.getStartY()-line.getEndY());

                    redo_stack.push(new Polygon(ax,ay,bx,by,cx,cy,dx,dy,ex,ey,fx,fy));
                }
                else if(removedShape.getClass() == Ellipse.class) {
                    Ellipse tempElps = (Ellipse) removedShape;
                    tempElps.setFill(gc.getFill());
                    tempElps.setStroke(gc.getStroke());
                    tempElps.setStrokeWidth(gc.getLineWidth());
                    redo_stack.push(new Ellipse(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY()));
                }
                Shape lastRedo = redo_stack.lastElement();
                lastRedo.setFill(removedShape.getFill());
                lastRedo.setStroke(removedShape.getStroke());
                lastRedo.setStrokeWidth(removedShape.getStrokeWidth());
                undo_stack.pop();

                for(int i=0; i < undo_stack.size(); i++) {
                    Shape shape = undo_stack.elementAt(i);
                    if(shape.getClass() == Line.class) {
                        Line temp = (Line) shape;
                        gc.setLineWidth(temp.getStrokeWidth());
                        gc.setStroke(temp.getStroke());
                        gc.setFill(temp.getFill());
                        gc.strokeLine(temp.getStartX(), temp.getStartY(), temp.getEndX(), temp.getEndY());
                    }
                    else if(shape.getClass() == Rectangle.class) {
                        Rectangle temp = (Rectangle) shape;
                        gc.setLineWidth(temp.getStrokeWidth());
                        gc.setStroke(temp.getStroke());
                        gc.setFill(temp.getFill());
                        gc.fillRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
                        gc.strokeRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
                    }
                    else if(shape.getClass() == Circle.class) {
                        Circle temp = (Circle) shape;
                        gc.setLineWidth(temp.getStrokeWidth());
                        gc.setStroke(temp.getStroke());
                        gc.setFill(temp.getFill());
                        gc.fillOval(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getRadius());
                        gc.strokeOval(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getRadius());
                    }
                    else if(shape.getClass() == Ellipse.class) {
                        Ellipse temp = (Ellipse) shape;
                        gc.setLineWidth(temp.getStrokeWidth());
                        gc.setStroke(temp.getStroke());
                        gc.setFill(temp.getFill());
                        gc.fillOval(temp.getCenterX(), temp.getCenterY(), temp.getRadiusX(), temp.getRadiusY());
                        gc.strokeOval(temp.getCenterX(), temp.getCenterY(), temp.getRadiusX(), temp.getRadiusY());
                    }

                        else if(shape.getClass() == Polygon.class) {
                        Polygon temp = (Polygon) shape;
                        gc.setLineWidth(temp.getStrokeWidth());
                        gc.setStroke(temp.getStroke());
                        gc.setFill(temp.getFill());
                        double ax = line.getStartX();
                        double ay = line.getStartY();

                        double bx = line.getEndX();
                        double by = line.getEndY();

                        double cx = line.getEndX()+2*(line.getEndX()-line.getStartX());
                        double cy = line.getEndY();

                        double dx = line.getEndX()+3*(line.getEndX()-line.getStartX());
                        double dy = line.getStartY();

                        double ex = line.getEndX()+2*(line.getEndX()-line.getStartX());
                        double ey = line.getStartY()+(line.getStartY()-line.getEndY());

                        double fx = line.getEndX();
                        double fy = line.getStartY()+(line.getStartY()-line.getEndY());
                        //System.out.println(end);
                        double x []={ax,bx,cx,dx,ex,fx};
                        double y []={ay,by,cy,dy,ey,fy};
                        int n =6;
                        gc.fillPolygon(x,y,n);
                        gc.strokePolygon(x,y,n);
                        undo_stack.push(new Polygon(ax,ay,bx,by,cx,cy,dx,dy,ex,ey,fx,fy));
                    }
                   else if(shape.getClass() == Polygon.class) {
                        Polygon temp = (Polygon) shape;
                        gc.setLineWidth(temp.getStrokeWidth());
                        gc.setStroke(temp.getStroke());
                        gc.setFill(temp.getFill());
                        double end =line.getEndX()-line.getStartX();
                        double x []={line.getStartX(),line.getEndX(),line.getEndX()-2*end};
                        double y []={line.getStartY(),line.getEndY(),line.getEndY()};
                        int n =3;
                        gc.fillPolygon(x,y,n);
                        gc.strokePolygon(x,y,n);

                    }
                }
            } else {
                System.out.println("no action to undo");
            }
        });

        // Redo
        redo.setOnAction(e->{
            if(!redo_stack.empty()) {
                Shape shape = redo_stack.lastElement();
                gc.setLineWidth(shape.getStrokeWidth());
                gc.setStroke(shape.getStroke());
                gc.setFill(shape.getFill());

                redo_stack.pop();
                if(shape.getClass() == Line.class) {
                    Line tempLine = (Line) shape;
                    gc.strokeLine(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(), tempLine.getEndY());
                    undo_stack.push(new Line(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(), tempLine.getEndY()));

                    tempLine = (Line) undo_stack.lastElement();
                    tempLine.setStrokeWidth(gc.getLineWidth());
                    tempLine.setFill(gc.getFill());
                    tempLine.setStroke(gc.getStroke());
                }
                else if(shape.getClass() == Rectangle.class) {
                    Rectangle tempRect = (Rectangle) shape;
                    gc.fillRect(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight());
                    gc.strokeRect(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight());

                    undo_stack.push(new Rectangle(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight()));


                    tempRect = (Rectangle) undo_stack.lastElement();
                    tempRect.setFill(gc.getFill());
                    tempRect.setStroke(gc.getStroke());
                    tempRect.setStrokeWidth(gc.getLineWidth());
                }
                else if(shape.getClass() == Circle.class) {
                    Circle tempCirc = (Circle) shape;
                    gc.fillOval(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius(), tempCirc.getRadius());
                    gc.strokeOval(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius(), tempCirc.getRadius());

                    undo_stack.push(new Circle(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius()));

                    tempCirc = (Circle) undo_stack.lastElement();
                    tempCirc.setFill(gc.getFill());
                    tempCirc.setStroke(gc.getStroke());
                    tempCirc.setStrokeWidth(gc.getLineWidth());
                }
                else if(shape.getClass() == Polygon.class) {
                    Polygon tempTriangle = (Polygon) shape;
                    double end =line.getEndX()-line.getStartX();
                    //System.out.println(end);
                    double x []={line.getStartX(),line.getEndX(),line.getEndX()-2*end};
                    double y []={line.getStartY(),line.getEndY(),line.getEndY()};
                    int n =3;
                    gc.fillPolygon(x,y,n);
                    gc.strokePolygon(x,y,n);
                    undo_stack.push(new Polygon(line.getStartX(),line.getStartY(),line.getEndX(), line.getEndY(),line.getEndX()-2*end, line.getEndY()));

                    tempTriangle = (Polygon) undo_stack.lastElement();
                    tempTriangle.setFill(gc.getFill());
                    tempTriangle.setStroke(gc.getStroke());
                    tempTriangle.setStrokeWidth(gc.getLineWidth());
                }
               else if(shape.getClass() == Polygon.class) {
                    Polygon tempHexagon = (Polygon) shape;
                    double end =line.getEndX()-line.getStartX();
                    //System.out.println(end);


                    tempHexagon = (Polygon) undo_stack.lastElement();
                    tempHexagon.setFill(gc.getFill());
                    tempHexagon.setStroke(gc.getStroke());
                    tempHexagon.setStrokeWidth(gc.getLineWidth());
                }
                else if(shape.getClass() == Ellipse.class) {
                    Ellipse tempElps = (Ellipse) shape;
                    gc.fillOval(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY());
                    gc.strokeOval(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY());

                    undo_stack.push(new Ellipse(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY()));

                    tempElps = (Ellipse) undo_stack.lastElement();
                    tempElps.setFill(gc.getFill());
                    tempElps.setStroke(gc.getStroke());
                    tempElps.setStrokeWidth(gc.getLineWidth());
                }

            } else {
                System.out.println("no action to redo");
            }
        });
        
        // Відкрити файл
        open.setOnAction((e)->{
            FileChooser open_file = new FileChooser();
            open_file.setTitle("Open File");
            File file = open_file.showOpenDialog(primaryStage);
            if (file != null) {
                try {
                    InputStream io = new FileInputStream(file);
                    Image img = new Image(io);
                    gc.drawImage(img, 0, 0);
                } catch (IOException ex) {
                    System.out.println("Error!");
                }
            }
        });

        // Збереження
        save.setOnAction((e)->{
            FileChooser save_file = new FileChooser();
            save_file.setTitle("Save File");
            
            File file = save_file.showSaveDialog(primaryStage);
            if (file != null) {
                try {
                    WritableImage writableImage = new WritableImage(1040, 2000);
                    canvas.snapshot(null, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", file);
                } catch (IOException ex) {
                    System.out.println("Error!");
                }
            }

        });

        //Розміщую елементи
        BorderPane pane = new BorderPane();
        pane.setLeft(btns_right);
        pane.setCenter(canvas);
        ScrollPane scrollPane = new ScrollPane(pane);
        scrollPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-focus-color: transparent;");

        Scene scene = new Scene(scrollPane, 1200, 700);

        primaryStage.setTitle("Paint");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}