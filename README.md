# Java Chatting
## 프로젝트 핵심
1. 유튜브 강의를 통해 클 코드 형식으로 제작하였다. 
2. 전체적인 네트워크 통신 흐름을 알기 위해 진행하였다.
3. 이론 공부를 통해 얻은 지식보다 프로젝트를 진행하며 모르는 것을 그때그때 찾아가며 학습하는 것이 훨씬 효율적이었다!!
## 개발도구
JDK, e(fx)clipse plug-in, eclipse
## 개발과정
* 전체적인 흐름
![전체적 흐름](https://user-images.githubusercontent.com/77962884/107382102-dd48ba80-6b32-11eb-9de8-76a7e55d7460.PNG)
   * 클라이언트가 연결 요청
   * 서버의 스레드 풀에서 연결 수락 후 Socket 생성
   * 클라이언트가 작업 처리 요청
   * 서버 스레드 풀에서 요청을 처리
   * 응답을 클라이언트로 반환
   
* Server
   * 클라이언트의 요청을 처리하는 역할 수행
   * ExecutorService는 여러 개의 스레드를 효율적으로 관리하기 위한 대표적인 라이브러리
   * 클라이언트마다 가지고 있는 Socket과 연결할 ServerSocket 필요
   * receive() : 반복적으로 해당 클라이언트로부터 메시지를 전달받음
   * send() : 특정 클라이언트에게 특정 시기에 메시지 전달
   
* Client
  * 서버로 접속하여 서버와 통신하는 구조 
  * 서버에 접속할 수 있도록 클라이언트 소켓 생성
  * 각 클라이언트는 스레드를 가져야 함
  * receive() : 메시지 받기 -> InputStream 사용
    
    send() : 메시지 보내기 -> OutputStream 사용
    
    --> 서버와 통신하는 모듈 
  * startClient() : 클라이언트 프로그램 동작 
  
## 세부기능 설명
1. 서버 & 클라이언트 실행

![동작](https://user-images.githubusercontent.com/77962884/107383485-63b1cc00-6b34-11eb-9058-e24abf46c0e7.PNG)

2. 서버 시작 

![서버접속](https://user-images.githubusercontent.com/77962884/107383553-73c9ab80-6b34-11eb-8060-d97e7a3ba90f.PNG)

3. 닉네임 입력 후 채팅방 접속, 메시지 전송

![접속후 메시지 보내기1](https://user-images.githubusercontent.com/77962884/107383687-978cf180-6b34-11eb-9fb3-946de52ad97e.PNG)

Server Log

![1](https://user-images.githubusercontent.com/77962884/107383705-9c51a580-6b34-11eb-9933-b55f42b47ab9.PNG)

4. 여러 명 접속

메시지 전송 후 메시지 서버로 전달, 서버가 다시 모든 클라이언트에게 
메시지 전송

receive 메소드를 통해
모든 클라이언트는 계속해서 서버로부터 어떠한 메시지를 전달받는다.

send 메소드를 통해
어떤 메시지를 전송하면  그 메시지는 채팅 서버로 전달된다.


![여러명 접속](https://user-images.githubusercontent.com/77962884/107383751-a673a400-6b34-11eb-9986-4995c40848fe.PNG)

Server Log

![2](https://user-images.githubusercontent.com/77962884/107383765-a96e9480-6b34-11eb-89af-d7aa69df145e.PNG)

5. 종료하기

어떠한 메시지를 보내도 서버와 끊겼기 때문에 메시지 출력이 안 된다.

현재 접속하고 있는 사용자에게만 메시지 출력

![종료](https://user-images.githubusercontent.com/77962884/107383783-ad9ab200-6b34-11eb-912c-1d3d45683fcf.PNG)
