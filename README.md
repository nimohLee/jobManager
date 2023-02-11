# Job Manager


## 개요 
구직 지원 내역을 관리할 수 

## 팀 구성
1인

## 개발 의도
- 스프링에 대한 이해
- 테스트 주도 개발 연습

## CI/CD 구축 환경 도식화
![image](https://user-images.githubusercontent.com/106662308/218245210-0142e508-e978-4338-ae16-ee2aeaf5c4d3.png)

## 데이터베이스
### ERD
<img width="494" alt="image" src="https://user-images.githubusercontent.com/106662308/214835461-80022065-cc01-454e-9b21-ac59f79a1ca4.png">

간단하게 유저 별 지원 내역 등록기능을 위해 지원 내역 테이블과 유저 테이블만 두었습니다. 
실제로 사용하기 위해 계속 업데이트할 예정이며 DB 역시 변경될 수 있습니다.

### 테이블 명세서
***user 테이블***

|테이블명|필드명|데이터타입|필드설명|
|---|---|---|---|
|user|id|bigint|유저 테이블 기본키|
||created_at|datetime|유저 가입 날짜 및 시간|
||updated_at|datetime|유저 정보 수정 날짜 및 시간|
||name|varchar|유저 이름|
||uid|varchar|유저 로그인 ID|
||password|varchar|유저 패스워드|
||email|varchar|유저 이메일|



***job 테이블***

|테이블명|필드명|데이터타입|필드설명|
|---|---|---|---|
|job|id|bigint|지원내역 테이블 기본키|
||created_at|datetime|지원내역 등록 날짜 및 시간|
||updated_at|datetime|지원내역 수정 날짜 및 시간|
||apply_date|date|지원 일자|
||company_name|varchar|회사 이름|
||employees_number|int|직원 수|
||hunting_site|varchar|구직사이트 이름|
||link|varchar|구직 공고 링크|
||location|varchar|회사 주소|
||note|varchar|비고|
||position|varchar|지원 분야|
||required_career|varchar|요구 경력|
||result|varchar|지원 결과|
||salary|varchar|연봉|
||x|varchar|맵 api를 사용하기 위한 회사 주소의 X 좌표|
||y|varchar|맵 api를 사용하기 위한 회사 주소의 Y 좌표|
||user_id|bigint|user 테이블의 idx|

## 사이트 이미지
### 지원 내역 목록
![image](https://user-images.githubusercontent.com/106662308/214839239-1a91ed92-a435-4d6e-925a-15cb91295028.png)

### 지원 내역 상세보기
![image](https://user-images.githubusercontent.com/106662308/214839451-9be7874a-8d65-4c2b-a3e7-d4040ae6ab24.png)

### 채용 공고 검색결과
![image](https://user-images.githubusercontent.com/106662308/217601782-37dcfaba-3808-4cc6-9f18-45ab46d32f66.png)


