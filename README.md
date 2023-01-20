# Hotel Service Toy Project ( 미완 )


## 개요 
회원 별로 호텔을 예약하고, 결제하는 사이트

## 팀 구성
1인

## 개발 의도
- 스프링에 대한 이해
- 테스트 주도 개발 연습

## 데이터베이스
### ERD
<img width="892" alt="image" src="https://user-images.githubusercontent.com/106662308/213616557-e89c95da-fca8-4e7f-8f9e-c05f778f8759.png">

### 테이블 명세서

***user 테이블***

|테이블명|필드명|데이터타입|필드설명|
|---|---|---|---|
|user|id|bigint|유저 테이블 기본키|
||email|varchar|유저 이메일|
||name|varchar|유저 이름|
||uid|varchar|유저 로그인 ID|
||password|varchar|유저 패스워드|
||regDate|datetime|유저 가입 날짜 및 시간|


***Boards 테이블***

|테이블명|필드명|데이터타입|필드설명|
|---|---|---|---|
|Boards|b_id|int|게시판 테이블 기본키|
||m_id|int|게시글 작성자 (유저 id)|
||m_name|varchar|작성자 이름|
||title|varchar|게시글 제목|
||content|varchar|게시글 내용|
||regDate|datetime|게시글 작성 날짜 및 시간|
||views|int|게시글 조회 수|
||createdAt|datetime|게시글 레코드 생성 시간|
||updatedAt|datetime|게시글 레코드 수정 시간|

***TourLists 테이블***

|테이블명|필드명|데이터타입|필드설명|
|---|---|---|---|
|TourLists|tl_id|int|여행목록 테이블 기본키|
||category|varchar|여행 분류|
||name|varchar|여행 이름|
||description|varchar|여행 설명|
||location|varchar|여행 주소|
||image|varchar|여행 이미지|

***ToursCarts 테이블***

|테이블명|필드명|데이터타입|필드설명|
|---|---|---|---|
|TourCarts|tc_id|int|여행바구니 테이블 기본키|
||m_id|int|바구니 유저 id|
||tl_id|int|담은 여행 id|
||price|varchar|총 여행 가격|

