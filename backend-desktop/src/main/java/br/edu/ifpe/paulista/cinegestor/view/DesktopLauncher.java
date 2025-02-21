package br.edu.ifpe.paulista.cinegestor.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import br.edu.ifpe.paulista.CinegestorWebApplication; // sua classe principal do Spring Boot

public class DesktopLauncher extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Cria o WebView e carrega a URL base do Spring Boot (que serve os arquivos do React)
        WebView webView = new WebView();
        webView.getEngine().load("http://localhost:8080"); // Ajuste a porta se necessário

        Scene scene = new Scene(webView, 1024, 768);
        primaryStage.setTitle("CineGestor Desktop");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Inicia o Spring Boot em uma thread separada para garantir que o servidor inicie
        new Thread(() -> SpringApplication.run(CinegestorWebApplication.class, args)).start();
        // Em seguida, inicia o JavaFX
        launch(args);
    }
}