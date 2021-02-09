package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

//Chat Server�� �ϳ��� Ŭ���̾�Ʈ�� ����ϱ� ���� Ŭ����
public class Client {     
	
	// ������ �־�� ��ǻ�Ϳ� ��Ʈ��ũ �󿡼� ����� �� �ִ�	
	Socket socket;      
	
	public Client(Socket socket) {
		
		// ������ �����, ��� ������ �ʱ�ȭ
		this.socket = socket;   
		
		// �ݺ������� Ŭ���̾�Ʈ�κ��� �޽��� ���޹ޱ�
		receive();             
	}
	// Ŭ���̾�Ʈ�κ��� �޽����� ���� �޴� �޼ҵ�
		public void receive() {
				
			// �ϳ��� ������ ���� �� ���ʺ� ���� ���
			Runnable thread = new Runnable() { 
				@Override
				public void run() {
					try {    
						while(true) {
							
							// ��� ������ ���� ���� �� �ְ� 
							InputStream in = socket.getInputStream();   
							
							// �ѹ��� 512����Ʈ ���� �ޱ� ����
							byte[] buffer = new byte[512];  
							
							int length = in.read(buffer);
							
							// ���� ������ �߻��ϸ� �˷���
							while(length == -1) throw new IOException(); 
							
							System.out.println("[�޽��� ���� ����]"
								
								// ���� ������ �� Ŭ���̾�Ʈ�� �ּ����� ���	
								+ socket.getRemoteSocketAddress()  
								
								// ������ ���� ���� ��� (�̸� ��)
								+": " + Thread.currentThread().getName()); 
							
							String message = new String(buffer, 0 , length, "UTF-8");
							for(Client client : Main.clients) {  
								
								// ���� ���� �޽����� �ٸ� Ŭ���̾�Ʈ���Ե� ����
								client.send(message);    
							}
					}
				} catch(Exception e) {
						try {
							System.out.println("[�޽��� ���� ����]"
									+ socket.getRemoteSocketAddress()
									+": " + Thread.currentThread().getName());
						} catch (Exception e2) {
							e.printStackTrace();
						}
					}
				}
			};
			// ������Ǯ�� ������� �ϳ��� ������ ���
			Main.threadPool.submit(thread); 
		}	
		
		// Ŭ���̾�Ʈ���� �޽����� �����ϴ� �޼ҵ�
		public void send(String message) {
			Runnable thread = new Runnable() {
				@Override
				public void run() {
					try {
						// ���� �ݴ�, �޽��� ���� �� Output ��� 
						OutputStream out = socket.getOutputStream();  
						
						byte[] buffer = message.getBytes("UTF-8");
						
						// buffer�� ��� ������ �������� Ŭ���̾�Ʈ�� ����
						out.write(buffer);
						
						// ���������� ������� ���������� �˸�
						out.flush();   
					
					} catch (Exception e) {
						try {
							System.out.println("[�޽��� �۽� ����]"
									+ socket.getRemoteSocketAddress()
									+": " + Thread.currentThread().getName());
							
							// ���� ���� �߻� ��, Ŭ���̾�Ʈ �迭���� ������ ���� Ŭ���̾�Ʈ ����  
							Main.clients.remove(Client.this);   
							
							// ������ ���� Ŭ���̾�Ʈ�� ������ ����
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

						
						
						
						
						
						
						
						
						
						
						