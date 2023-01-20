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
<img width="800" alt="image" src="https://user-images.githubusercontent.com/106662308/213617631-c33a6b80-1f66-4a09-948a-ee0b3b04fa46.png">


### 테이블 명세서
***모든 테이블 공통 속성***
|필드명|데이터타입|필드설명|
|---|---|---|
|created_at|datetime|생성된시간|
|updated_at|datetime|수정된시간|

***user 테이블***

|테이블명|필드명|데이터타입|필드설명|
|---|---|---|---|
|user|id|bigint|유저 테이블 기본키|
||email|varchar|유저 이메일|
||name|varchar|유저 이름|
||password|varchar|유저 패스워드|
||uid|varchar|유저 로그인 ID|


***board 테이블***

|테이블명|필드명|데이터타입|필드설명|
|---|---|---|---|
|Board|id|bigint|게시판 테이블 기본키|
||category|varchar|게시글 카테고리|
||content|varchar|게시글 내용|
||title|varchar|게시글 제목|
||user_id|int|게시글 작성자 (유저 id)|
||createdAt|datetime|게시글 레코드 생성 시간|

***room 테이블***

|테이블명|필드명|데이터타입|필드설명|
|---|---|---|---|
|room|id|bigint|방 테이블 기본키|
||count_of_rooms|int|방 개수|
||description|varchar|방 설명|
||max_people|int|최대 인원 수|
||standard_people|int|기준 인원 수|
||name|varchar|방 이름|
||price|varchar|방 가격|

***reservation 테이블***

|테이블명|필드명|데이터타입|필드설명|
|---|---|---|---|
|reservation|id|bigint|예약 테이블 기본키|
||check_in|date|체크인 날짜|
||check_out|date|체크아웃 날짜|
||total_price|int|총 예약 가격|
||room_id|bigint|예약된 방 (room id 참조)|
||user_id|bigint|예약 유저 (user id 참조)|


***payment 테이블***

|테이블명|필드명|데이터타입|필드설명|
|---|---|---|---|
|payment|id|bigint|결제 테이블 기본키|
||reservation_id|bigint|해당 예약 (reservation id)|
||user_id|bigint|예약자 (user id)|


***amenity 테이블***

|테이블명|필드명|데이터타입|필드설명|
|---|---|---|---|
|amenity|id|bigint|편의사항 테이블 기본키|
||name|varchar|편의사항 이름|


***room_amenities 테이블***

|테이블명|필드명|데이터타입|필드설명|
|---|---|---|---|
|room_amenities|id|bigint|편의사항과 방 다대다 중간 테이블 기본키|
||room_id|bigint|방 id(room_id)|
||amenities_id|bigint|편의사항 id(amenity_id)|

