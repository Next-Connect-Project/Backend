# Backend
---
# 
<img width="412" alt="image" src="https://github.com/Next-Connect-Project/Frontend/assets/70136320/362a7929-9f2d-4859-82e2-b35e36c578ce">


### 프로젝트 개요

- 사이드 프로젝트 , 스터디 팀원을 자유롭게 구할 수 있는 플랫폼

### 기획배경

- 프로젝트나 스터디를 진행하고 싶은데 팀원을 어디에서 구해야할지 고민이되는 경우가 많습니다.
- 따라서 원하는 포지션의 팀원을 쉽게 구할 수 있는 플랫폼을 기획했습니다.

### 해결방법

- 사용자는 본인이 필요한 유형에 맞춰, 두 가지 유형의 게시물을 작성할 수 있습니다.
    - 모집 - 필요한 팀원의 role, 기술 스택, 진행 기간, 진행 방식 등을 기재한 프로젝트 ,스터디 팀원 모집글
    - 홍보 - 다양한 주제로 소통하고 싶거나 홍보가 필요한 소재를 자유롭게 올리는 홍보글

**Demo link:** 

**팀 노션:** [NextConnect](https://www.notion.so/ea86bb2be7484da699f9b5c91c2b502f?pvs=21)

## 🕰️ 개발 기간

- 23.03.28 - 23.06.08

</br>

## 🛠️ Skills
### Front-End
<img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=white"> <img src="https://img.shields.io/badge/TypeScript-3178C6?style=for-the-badge&logo=TypeScript&logoColor=white"> 
<img src="https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=Vite&logoColor=white"> <img src="https://img.shields.io/badge/redux-764ABC?style=for-the-badge&logo=redux&logoColor=white"> <img src="https://img.shields.io/badge/React Router-CA4245?style=for-the-badge&logo=sasReact Routers&logoColor=white"> <img src="https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge&logo=Axios&logoColor=white"> <img src="https://img.shields.io/badge/Scss-cc6699?style=for-the-badge&logo=sass&logoColor=white"> 


### Back-End

<img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white">  <img src="https://img.shields.io/badge/spring boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/spring batch-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=spring security&logoColor=white"> <img src="https://img.shields.io/badge/gradle-2D4999?style=for-the-badge&logo=gradle&logoColor=white">

<img src="https://img.shields.io/badge/JPA-6DB33F?style=for-the-badge"> <img src="https://img.shields.io/badge/QueryDql-7957D5?style=for-the-badge&logo=QueryDql&logoColor=white"> <img src="https://img.shields.io/badge/mariaDB-003545?style=for-the-badge&logo=mariaDB&logoColor=white"> <img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white"> 

<img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens"> <img src="https://img.shields.io/badge/Server Sent Event-B5314C?style=for-the-badge&logo=net%20web%20tokens">

### Infrastructure
<img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white"> <img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white"> <img src="https://img.shields.io/badge/Amazon RDS-527FFF?style=for-the-badge&logo=Amazon RDS&logoColor=white"> <img src="https://img.shields.io/badge/Amazon S3-569A31.svg?style=for-the-badge&logo=Amazon S3&logoColor=white"> <img src="https://img.shields.io/badge/Git Action-181717.svg?style=for-the-badge&logo=Git&logoColor=white">


### Collaboration tool
<img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white"> <img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white"> <img src="https://img.shields.io/badge/slack-4A154B?style=for-the-badge&logo=slack&logoColor=white"> <img src="https://img.shields.io/badge/GitHub-4A154B?style=for-the-badge&logo=GitHub&logoColor=white">



### ERD modeling

<img src = "https://github.com/songhee1/algorithm-study/assets/96781855/d99df16b-4d15-4690-a767-9b6ba1cea64c" width = "auto" height = "800px"> 


<br>
<br>

## ⚒️ 프로젝트 주요 기능

### 1. 로그인
<details>
<summary> 회원가입 & 소셜 로그인</summary>
<div markdown="1">
<ul>
<li>소셜 로그인은 JWT 인증 방식으로 구현</li>
<li>OAuth 로그인 : 네이버 계정을 통해 로그인
<ul>
<li>Front 에서 Authorization code 발급받아 Server 로 전달</li>
<li>Back 에서 Authorization code 를 받아 네이버 서버로부터 Access Token 및 유저 정보를 요청</li>
<li>응답받은 유저 정보로 Database 를 검색하여 회원 정보 생성</li>
<li>로그인 성공 시 Access, Refresh Token을 발급하여 응답</li>
</ul>
</li>
<li>인증
<ul>
<li>요청마다 AccessToken을 준다.</li>
<li>AccessToken이 만료되었다면 RefreshToken을 통해 토큰을 새로 발급 받는다.</li>
</ul>
</li>
</ul>
</div>
</details>

### 2. 모집 게시판
<details>
<summary>모집 공고 게시판</summary>
<div markdown="1">
<ul>
<li>메인 페이지
<ul>
<li>마감일이 가장 임박한 모집 공고글 4개를 보여준다.</li>
</ul>
</li>
<li>모집 공고글 조회
<ul>
<li>분류 (프로젝트, 스터디) , 상태(진행중, 모집완료)로 필터 하여 사용자에게 보여준다.</li>
<li>AccessToken이 만료되었다면 RefreshToken을 통해 토큰을 새로 발급 받는다.</li>
</ul>
</li>
</ul>
<ul>
<li>모집 공고 등록
<ul>
<li>제목, 모집 구분, 모임 목적, 기술 스택, 모집 마감일, 모집 인원, 진행 방식, 진행 기간, 모임 시간 및 장소, 진행 방법, 연락 방법, 자유 소개글 항목으로 나뉘어져 있다.</li>
<li>자유 소개글을 제외한 모든 항목은 필수 항목이며, 빈 항목으로 제출 시 등록이 되지 않는다.</li>
<li>글쓴이, 작성 시간, 상태(OPEN, CLOSED)는 자동으로 기입된다.</li>
<li>상세 조회 시, 해당 모집글이 없으면 에러가 발생한다.</li>
</ul>
</li>
<li>모집 마감
<ul>
<li>모집중 버튼을 눌러 프로젝트의 상태를 비활성화 할 수 있다.</li>
<li>모집완료 버튼을 눌러 모집 마감했던 프로젝트를 다시 모집 활성화 할 수 있다.</li>
<li>작성자가 아닌 사용자가 모집글을 마감하거나 다시 활성화할 수 없다.</li>
</ul>
</li>
</ul>
<ul>
<li>모집 글 수정
<ul>
<li>상세페이지 에서 AccessToken으로 사용자 정보를 확인 후 수정권한을 부여한다. </li>
<li>사용자가 작성한 글을 기반으로 자동으로 입력되어진 상태로 편집페이지를 보여준다.</li>
<li>작성한 모집 공고 수정한다. </li>
<li>작성자가 아닌 사용자가 모집글을 수정할 수 없다.</li>
</ul>
</li>
<li>모집 글 삭제
<ul>
<li>삭제 확인 모달에서 한번 더 버튼을 누를 시 작성한 모집 공고가 삭제된다.</li>
<li>작성자가 아닌 사용자가 모집글을 삭제할 수 없다.</li>
</ul>
</li>
</ul>

</div>
</details>


### 3. 홍보 게시판
<details>
<summary>홍보 게시판 </summary>
<div markdown="1">
<ul>
<li>메인 페이지
<ul>
<li>현재 날짜로부터 2주 이내로 작성한 홍보글 4개를 불러온다.</li>
</ul>
</li>
<li>홍보글 조회
<ul>
<li>제목, 내용,  프로젝트 홍보 요약정보에 대한 글을 작성한다.</li>
<li>작성자, 작성 시간, 조회수, 추천 수가 자동으로 정보 추가된다.</li>
<li>제목과 내용, 프로젝트 홍보 요약정보를 필수로 작성한다.</li>
<li>조회수는 방문 중복 처리가 되지 않도록 사용자 페이지에 쿠키에 값을 넣고 세션 시간을 정해 만료되기 전까지 조회수가 오르지 않도록 방지한다.</li>
</ul>
</li>
</ul>
<ul>
<li>홍보글 수정
<ul>
<li>본인이 작성한 글 인지 DB 사용자 정보와 글쓴이 정보를 유효 값으로 비교하여 수정 권한을 준다. </li>
<li>수정 페이지로 넘어가서 제목과 내용, 프로젝트 요약 정보 내용을 수정한다.</li>
</ul>
</li>
<li>홍보글 추천
<ul>
<li>추천을 누르지 않은 경우 추천 수가 각 회원 별로 1씩 오르고 줄어든다.</li>
<li>같은 수의 추천 수를 가진 게시물이 존재할 경우 최신 순 정렬된다.</li>
</ul>
</li>
<li>홍보글 조회
<ul>
<li>쿼리 파라미터 값에 따라 최신 순, 추천 순, 최신 및 추천 순 동시 적용해 정렬한다.</li>
</ul>
</li>
<li>마이페이지 홍보글 조회
<ul>
<li>본인이 작성한 글을 오래된 순으로 정렬하여 조회한다.</li>
</ul>
</li>
</ul>
 

</div>
</details>


</br>

## 🚀Demo
###
|소셜로그인,마이페이지|로그아웃|
|---|---|
|<img src ="https://github.com/Next-Connect-Project/Frontend/assets/70136320/2f507846-ad12-42c6-9396-7505f4f06958" />|<img src ="https://github.com/Next-Connect-Project/Frontend/assets/70136320/07a1c4cc-b644-4c97-9d3d-a3ff2d723e84"/>|
|모집게시판|홍보게시판|
|<img src ="https://github.com/Next-Connect-Project/Frontend/assets/70136320/7b2f6a88-9dda-496d-bea5-29a5a6dd2718"/>|<img src ="https://github.com/Next-Connect-Project/Frontend/assets/70136320/33c09d9f-70f3-4ea5-a4e0-5d67b1d7f308"/>|
|모집글 작성|모집글 수정,삭제|
|<img src ="https://github.com/Next-Connect-Project/Frontend/assets/70136320/6023eee4-dc57-4829-815f-743a856e453f"/>|<img src ="https://github.com/Next-Connect-Project/Frontend/assets/70136320/bd38b5bc-3199-45ef-8fc0-0ddd3afea1a3"/>|
|홍보글 작성|홍보글 수정,삭제|
|<img src ="https://github.com/Next-Connect-Project/Frontend/assets/70136320/7f9f153c-ac73-45fe-8d93-920d941f2887"/>|<img src ="https://github.com/Next-Connect-Project/Frontend/assets/70136320/d4ec5e05-415a-4e91-91b9-5a114ee6c383"/>|
|마감여부 수정|메인화면|
|<img src ="https://github.com/Next-Connect-Project/Frontend/assets/70136320/cba8ca7d-4f66-4cc7-bd84-b5aba044275c"/>|<img src ="https://github.com/Next-Connect-Project/Frontend/assets/70136320/085fcf7b-b8d8-4f52-aa46-4b8145a48f0e"/>|

</br>


## 👥 팀원소개
- 최송희(Back-end) : [Github  ](https://github.com/songhee1)
- 한희나(Back-end) :  [Github  ](https://github.com/heenahan)
- 송지수(Front-end) : [Github  ](https://github.com/z1su)
- 조혜준(Front-end) : [Github  ](https://github.com/chj950807)
