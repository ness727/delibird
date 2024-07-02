![img](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FJFStr%2FbtsIkZ4uRMU%2FocxYHF53fFRmj9lnXQAGBK%2Fimg.png)

DelibirdEats
==========
MSA를 도입한 배달 어플리케이션 프로젝트입니다.

- service-discovery, api-gateway 등의 서비스를 구축하여 게이트웨이에서 각 마이크로 서비스로 요청을 전달하는 MSA 구조를 설계하고 적용하였습니다.
- GCP의 VM instance, Cloud SQL, App Engine을 통해 백엔드와 프론트 서비스를 배포하고 각각 api.delibird.store와 delibird.store로 구성하여 도메인으로 접속이 가능하도록 구성하였습니다.
- 백엔드와 프론트간 CORS 오류를 해결하고 HTTPS 환경에서 쿠키를 전달할 수 있도록 설정하였습니다.
- 설정 정보를 GitHub에 저장하여 실행 시에 해당 저장소에서 설정을 가져와 반영하고 Spring Cloud Bus와 Kafka를 통해서 실행 중에도 설정을 변경할 수 있습니다.
- DB의 부하를 줄이기 위해 장바구니 서비스를 Redis로 구현하여 빠른 접근과 30일 지나면 자동 삭제할 수 있도록 하였습니다.
- Kafka Connect를 이용하여 DB로 저장되는 주문 관련 내역을 위임하여 비동기 처리를 할 수 있도록 하였습니다.

<br/>

대칭키 관련 설정
--------
config-service에 secret.yaml 파일을 만들고

encrypt:<br/>
&nbsp;&nbsp;key: 원하는 키

입력한 후 delibird-config의 설정 yaml 파일에서 암호화한 내용 반영해야 작동합니다.

<br/>

설정 파일 위치<br/>
--------
https://github.com/ness727/delibird-config
