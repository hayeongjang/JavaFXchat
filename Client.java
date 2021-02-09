package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

//Chat Server가 하나의 클라이언트와 통신하기 위한 클래스
public class Client {     
	
	// 소켓이 있어야 컴퓨터와 네트워크 상에서 통신할 수 있다	
	Socket socket;      
	
	public Client(Socket socket) {
		
		// 생성자 만들기, 어떠한 변수의 초기화
		this.socket = socket;   
		
		// 반복적으로 클라이언트로부터 메시지 전달받기
		receive();             
	}
	// 클라이언트로부터 메시지를 전달 받는 메소드
		public void receive() {
				
			// 하나의 스레드 만들 때 러너블 많이 사용
			Runnable thread = new Runnable() { 
				@Override
				public void run() {
					try {    
						while(true) {
							
							// 어떠한 내용을 전달 받을 수 있게 
							InputStream in = socket.getInputStream();   
							
							// 한번에 512바이트 전달 받기 가능
							byte[] buffer = new byte[512];  
							
							int length = in.read(buffer);
							
							// 만약 오류가 발생하면 알려줌
							while(length == -1) throw new IOException(); 
							
							System.out.println("[메시지 수신 성공]"
								
								// 현재 접속을 한 클라이언트의 주소정보 출력	
								+ socket.getRemoteSocketAddress()  
								
								// 스레드 고유 정보 출력 (이름 값)
								+": " + Thread.currentThread().getName()); 
							
							String message = new String(buffer, 0 , length, "UTF-8");
							for(Client client : Main.clients) {  
								
								// 전달 받은 메시지를 다른 클라이언트에게도 전달
								client.send(message);    
							}
					}
				} catch(Exception e) {
						try {
							System.out.println("[메시지 수신 오류]"
									+ socket.getRemoteSocketAddress()
									+": " + Thread.currentThread().getName());
						} catch (Exception e2) {
							e.printStackTrace();
						}
					}
				}
			};
			// 스레드풀에 만들어진 하나의 스레드 등록
			Main.threadPool.submit(thread); 
		}	
		
		// 클라이언트에게 메시지를 전송하는 메소드
		public void send(String message) {
			Runnable thread = new Runnable() {
				@Override
				public void run() {
					try {
						// 위와 반대, 메시지 보낼 때 Output 사용 
						OutputStream out = socket.getOutputStream();  
						
						byte[] buffer = message.getBytes("UTF-8");
						
						// buffer에 담긴 내용을 서버에서 클라이언트로 전송
						out.write(buffer);
						
						// 성공적으로 여기까지 전달했음을 알림
						out.flush();   
					
					} catch (Exception e) {
						try {
							System.out.println("[메시지 송신 오류]"
									+ socket.getRemoteSocketAddress()
									+": " + Thread.currentThread().getName());
							
							// 만약 오류 발생 시, 클라이언트 배열에서 오류가 생긴 클라이언트 제거  
							Main.clients.remove(Client.this);   
							
							// 오류가 생긴 클라이언트의 소켓을 닫음
							socket.close(); 
						
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
			};
			Main.threadPool.submit(thread);
			
		}
	}

						
						
						
						
						
						
						
						
						
						
						