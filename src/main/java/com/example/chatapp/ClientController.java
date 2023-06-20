package com.example.chatapp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientController  {

    @FXML
    private TextField txtField;

    @FXML
    private TextArea txtArea;

    ServerSocket serverSocket ;
    Socket socket;

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    BufferedReader bufferedReader;

    String massage ="";
    String reply ="";

    public void initialize(){
        new Thread(()->{

            try {
                socket = new Socket("Localhost",4005);
                dataInputStream=new DataInputStream(socket.getInputStream());
                dataOutputStream=new DataOutputStream(socket.getOutputStream());

                while (!massage.equals("Finish")){
                    massage = dataInputStream.readUTF();
                    txtArea.appendText("\n Server :"+massage);


                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }).start();

    }


    @FXML
    void btnOnAction(ActionEvent event) throws IOException {
        dataOutputStream.writeUTF(txtField.getText().trim());
        dataOutputStream.flush();

    }
}
