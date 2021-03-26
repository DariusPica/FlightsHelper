import Controller.LoginController;
import Domain.Bilet;
import Repository.BiletRepository;
import Repository.UserRepository;
import Repository.ZborRepository;
import Service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {

        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }



        ZborRepository zborRepo=new ZborRepository(props);
        BiletRepository biletRepo=new BiletRepository(props);
        UserRepository userRepository=new UserRepository(props);


        Service srv=new Service(biletRepo,userRepository,zborRepo);

        zborRepo.getAll().forEach(System.out::println);
        biletRepo.getAll().forEach(System.out::println);
        userRepository.getAll().forEach(System.out::println);

        FXMLLoader loader = null;
        loader = new FXMLLoader(getClass().getClassLoader().getResource("login.fxml"));


        LoginController controller = new LoginController();
        controller.setService(srv);
        loader.setController(controller);

        primaryStage.setScene(new Scene(loader.load()));

        primaryStage.show();
    }
}
