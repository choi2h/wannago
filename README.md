<img width="1447" height="673" alt="Image" src="https://github.com/user-attachments/assets/5dc7dc2e-0347-47e1-a702-7231a909e297" />

<br/>

## 📍 제작 목표

저희 프로젝트는 여행 후기를 공유하는 것을 넘어, 사용자들이 자신의 여행 일정을 자유롭게 나누고, 그 안에서 느낀 경험을 함께 공유하며, 다른 사람들이 쉽게 따라 할 수 있는 상세한 여행 계획을 얻을 수 있는 공간을 만들고자 하는 목표로 시작되었습니다.

기존의 여행 플랫폼들은 주로 명소 추천이나 단순한 후기 공유에 집중되어 있어, 실제로 여행 계획을 세우는 데 필요한 구체적이고 실용적인 정보를 얻기 어려운 경우가 많았습니다. 저희는 여행 계획을 세울 때의 막연함과 모호함을 해결하고자, 날짜별 일정, 장소, 추천 포인트, 그리고 직접 느낀 점과 같은 상세한 기록을 공유할 수 있는 플랫폼이 필요하다고 생각했습니다. 이는 여행에 관심이 있는 사람들에게  더 구체적인  여행 경험을 계획하고 공유하는 데 도움이 될거라고 생각합니다.

<br/>

## 📍개발 역할

| **기능 영역** | **세부 기능 설명** | **담당자** |
| --- | --- | --- |
| **회원 기능** | - 회원가입 <br/> - 로그인 / 로그아웃 <br/> - JWT 전역 처리 | 강민서 |
| **게시판 기능** | - 게시글 등록/수정/삭제 <br/> - 게시글 조회 <br/> - 게시판 목록 조회 (최신순/랭킹순) <br/> - 게시글 상세 보기 | 최이화 |
| **댓글 기능** | - 댓글 / 대댓글 등록/조회/수정/삭제 | 김진아 |
| **좋아요 기능** | - 게시글 좋아요 추가/삭제 | 김진아 |
| **북마크 기능** | - 게시글 북마크 추가/삭제 | 김진아 |
| **검색 기능** | - 제목 기반 게시글 검색 기능 | 강병찬 / 최이화 |
| **지도 API** | - 카카오 지도 API 연동 | 최이화 |
| **마이페이지 기능** | - 내가 쓴 글 보기 <br/> - 내 북마크 보기 <br/> - 마이페이지 조회 <br/> - 내 정보 수정 | 윤형일 |
| **Q&A - 질문** | - 질문 등록/수정/삭제 <br/> - 질문 목록 조회 <br/> - 질문 상세 조회 <br/> - 질문 카테고리별 목록 조회 | 안성준 / 최이화 |
| **Q&A - 답변** | - 답변 등록/조회/수정/삭제 <br/> - 답변 채택 | 강병찬 |

<br/>

## 📍**Git 활용 전략**

- Branch 전략
    - main : 실제 배포용 브랜치
    - develop 기능 개발 완료 통합 브랜치
    - feature : 개별 기능 단위로 생성되는 브랜치
- Pull Request
    - 개발 반영 내용 명시
    - 리뷰 포인트 작성
    - PR을 통한 상호간 코드 리뷰

1. **Branch Strategy**
      ```
      main # 실제 배포용 브랜치
      └─ develop (default) # 기능 개발 완료 통합 브랜치
          └─ feat/기능명 # 개별 기능 단위로 생성되는 브랜치
      ```

2. **Commit Message**
      ```jsx
      <type>: <description>
      
      [optional body]
      ```

3. **Commit Type**
      | type | 설명 |
      | --- | --- |
      | `feat` | 기능 개발 |
      | `fix` | 오류 수정 |
      | `refactor` | 기능변경 없는 코드 수정 |
      | `env` | 초기 환경 설정 |

<br/>

## 📍기술 스택
<details>
    <summary>기술스택 정리</summary>
    <div markdown="1">
    - 백엔드  
        - JAVA  
        - Spring Boot  
            - Spring Security  
            - JWT  
            - Validation  
            - JPA  
            - Lombok  
            - Gradle  
        - MySQL  
        - MongoDB  
    - 프론트  
        - React  
        - ReactRouter  
        - Axios  
    </div>
</details>
<img width="1175" height="466" alt="Image" src="https://github.com/user-attachments/assets/ce681197-956d-43d4-a05c-b33c9236307c" />


<br/>

## 📍주요 기능

- JWT를 사용한 회원가입/로그인/로그아웃
- 여행일정 게시글 CRUD
- MongoDB를 활용한 트리구조의 댓글 관리
- 게시글 추천, 태그, 북마크, 페이징 및 필터링 기능 개발
- 카카오 지도 API를 활용한 지도 노출 및 장소 다중 마커 표시
- 제목 기반 검색시스템
- 여행일정 복제 및 수정 기능
- 사용자간 CRUD ( 답글 및 채택 )

<br/>

## 📍ERD

<img width="2120" height="782" alt="Image" src="https://github.com/user-attachments/assets/832f9a10-b011-430e-bb2a-939ff01278d3" />

<br/>
<br/>

## 📍시연 화면

| 회원가입 |
|---|
|<img width="1000" height="400" alt="Image" src="https://github.com/user-attachments/assets/7ba14537-dae7-4d53-a87e-e4d69e45f683" />|

<br/>

| 로그인 및 로그아웃 |
|---|
|<img width="1000" height="400" alt="Image" src="https://github.com/user-attachments/assets/63dfc9ee-533c-434c-bf47-6d98175241e1" />|
<br/>
    
| 회원정보 |
|---|
|<img width="1000" height="400" alt="Image" src="https://github.com/user-attachments/assets/4bdb5720-9988-4ddd-9e4c-5656677503ca" />|
<br/>
    
|메인페이지 및 게시글 목록 조회|
|---|
|<img width="1000" height="400" alt="Image" src="https://github.com/user-attachments/assets/0a4b087c-9677-4880-8840-90e019c2e8fd" />|
<br/>
    
| 게시글 등록 및 여행 일정 추가 |
|---|
|<img width="1000" height="400" alt="Image" src="https://github.com/user-attachments/assets/f9527c84-d573-44d3-bb2e-5ff817d531a8" />|
|<img width="1000" height="400" alt="Image" src="https://github.com/user-attachments/assets/17cfeefa-423f-43d7-86a8-78ea4a5bf526" />|
<br/>

|게시글 수정|
|---|
|<img width="1000" height="400" alt="Image" src="https://github.com/user-attachments/assets/55778c1d-73cd-4387-a03f-34e7373dc088" />|
<br/>
    
|좋아요 및 북마크|
|---|
|<img width="1000" height="400" alt="Image" src="https://github.com/user-attachments/assets/612a8286-d4ed-471e-91ca-7d92da9b60a6" />|
<br/>
    
|나의 게시글 및 북마크|
|---|
|<img width="1000" height="400" alt="Image" src="https://github.com/user-attachments/assets/0b9639b4-fe31-4bf2-bb89-9ef60700c8ab" />|
<br/>
    
|질문 목록 및 상세조회|
|---|
|<img width="1000" height="400" alt="Image" src="https://github.com/user-attachments/assets/39f49ce7-441a-41a0-8015-79153fb5ced1" />|
<br/>
    
| 질문 등록 및 수정/답변 작성성 |
|---|
|<img width="1000" height="400" alt="Image" src="https://github.com/user-attachments/assets/4ab47a01-f30a-45e1-a154-cd475cd9415d" />|
