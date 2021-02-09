package application;
	
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;



public class Main extends Application {
	
	Socket socket;
	
	// 다양한 내용 주고 받았을 때 그런 메시지들을 출력하는 공간
	TextArea textArea; 
	
	// 클라이언트 프로그램 동작 메소드
	public void startClient(String IP, int port) {
		Thread thread = new Thread() { 
			public void run() {
				try {
					// 소켓 초기화
					socket = new Socket(IP, port); 
					
					// 서버로 부터 메시지 전달 받음
					receive();  
					
					} catch (Exception e) {
					if(!socket.isClosed()) {
						stopClient();
						System.out.println("[서버 접속 실패]");
						
						// 프로그램 자체 종료
						Platform.exit();  
					}
				}
			}
		};
		thread.start();
	}
	
	// 클라이언트 프로그램 종료 메소드
	public void stopClient() {
		try {
			if(socket != null && !socket.isClosed()) {
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 서버로부터 메시지를 전달받는 메소드
	public void receive() {
		
		// 무한 루프
		while(true) { 
		
			try {
				// 소켓에서 InputStream을 열어서 현재 서버로부터 어떠한 메시지를 전달받을 수 있도록 해줌
				InputStream in = socket.getInputStream(); 
				
				byte[] buffer = new byte[512];
				int length = in.read(buffer);
				if(length == -1) throw new IOException();
				String message = new String(buffer, 0, length, "UTF-8");
				Platform.runLater(()->{
					textArea.appendText(message);
				});
			} catch (Exception e) {
				stopClient();
				break;
			}
		}
	}
	
	// 서버로 메시지를 전송하는 메소드
	public void send(String message) {
		Thread thread = new Thread() {
			public void run() {
				try {
					OutputStream out = socket.getOutputStream();
					byte[] buffer = message.getBytes("UTF-8");
					out.write(buffer);
					out.flush();  
				} catch (Exception e) {
					stopClient();
				}
			}
		};
		thread.start();
	}
	
	// 실제로 프로그램을 동작시키는 메소드
	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(5));
		
		HBox hbox = new HBox();
		hbox.setSpacing(5);
		
		TextField userName = new TextField();
		userName.setPrefWidth(150);
		userName.setPromptText("닉네임 입력");
		HBox.setHgrow(userName, Priority.ALWAYS);
		
		TextField IPText = new TextField("127.0.0.1");
		TextField portText = new TextField("1234");
		portText.setPrefWidth(80);
		
		hbox.getChildren().addAll(userName, IPText, portText);
		root.setTop(hbox);
		
		textArea = new TextArea();
		textArea.setEditable(false); 
		root.setCenter(textArea);
		
		TextField input = new TextField();
		input.setPrefWidth(Double.MAX_VALUE);
		input.setDisable(true);
		
		input.setOnAction(event -> {
			
			// 서버로 어떠한 메시지를 전달할 수 있도록 함 (사용자 이름과 함께)
			send(userName.getText() + " :" + input.getText() + "\n"); 
			input.setText("");
			input.requestFocus();
		});
		
		Button sendButton = new Button("보내기");
		sendButton.setDisable(true);
		
		sendButton.setOnAction(event -> {
			send(userName.getText() + ": " + input.getText() + "\n"); 
			input.setText("");
			input.requestFocus();
		});
		
		Button connectionButton = new Button("접속하기");
		connectionButton.setOnAction(event -> {
			if(connectionButton.getText().equals("접속하기")) {
				int port = 1234;
				try {
					// 기본적으로 포트 번호는 1234, 사용자가 별도의 포트번호를 입력하면 그 포트로 접속이 이뤄지게 함
					port = Integer.parseInt(portText.getText()); 
				
				} catch (Exception e) {
					e.printStackTrace();
				}
				startClient(IPText.getText(), port);
				Platform.runLater(() -> {
					textArea.appendText("[ 채팅방 접속 ]\n");
					});
				connectionButton.setText("종료하기");
				input.setDisable(false); 
				
				// 사용자가 버튼을 눌러서 메시지 전송할 수 있도록 함
				sendButton.setDisable(false);  
				
				input.requestFocus();
			} else {
				stopClient();
				Platform.runLater(() -> {
					textArea.appendText("[ 채팅방 퇴장 ]\n");
				});
				connectionButton.setText("접속하기");
				
				// 버튼 누를 수 없도록 함
				input.setDisable(true);  
				sendButton.setDisable(true);
			}
		});
		BorderPane pane = new BorderPane();
		
		// 접속하기 버튼
		pane.setLeft(connectionButton); 
		
		// 사용자가 입력할 수 있도록
		pane.setCenter(input); 
		
		// 보내기 버튼
		pane.setRight(sendButton); 
		
		root.setBottom(pane);
		Scene scene = new Scene(root, 400, 400);
		primaryStage.setTitle("[ 채팅 클라이언트 ]");
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(event -> stopClient());
		primaryStage.show();
		
		connectionButton.requestFocus();
		
	}
	// 프로그램의 진입점
	public static void main(String[] args) {
		launch(args);
	}
}
