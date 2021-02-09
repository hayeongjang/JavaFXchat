# Java Chatting
## 프로젝트 핵심
1. 유튜브 강의를 통해 클린 코드 형식으로 제작하였다. 
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
## 세부기능 설명
1. 서버&클라이언트 실행

![동작](https://user-images.githubusercontent.com/77962884/107383485-63b1cc00-6b34-11eb-9058-e24abf46c0e7.PNG)

2. 서버 시작 

![서버접속](https://user-images.githubusercontent.com/77962884/107383553-73c9ab80-6b34-11eb-8060-d97e7a3ba90f.PNG)

3. 닉네임 입력 후 채팅방 접속, 메시지 전송

![접속후 메시지 보내기1](https://user-images.githubusercontent.com/77962884/107383687-978cf180-6b34-11eb-9fb3-946de52ad97e.PNG)
