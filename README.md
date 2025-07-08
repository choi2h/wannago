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

| 회원가입 및 로그인 |
|---|
|![Image](https://github.com/user-attachments/assets/903b08b6-0544-4587-9441-a43b4a57b7e1)|

<br/>
    
| 회원정보 |
|---|
|![Image](https://github.com/user-attachments/assets/ea8d40eb-89c8-4f89-a74f-a1191ff09b9e)|
<br/>
    
| 게시글 등록 및 여행 일정 추가 |
|---|
|![Image](https://github.com/user-attachments/assets/5d134707-2b33-4111-8248-27c4eb9286b8)|
<br/>

| 게시글 댓글 작성/수정/삭제 |
|--|
|![Image](https://github.com/user-attachments/assets/5044e587-89eb-4461-93c3-6b9d5bb352d6)|
<br/>

|게시글 수정/삭제|
|---|
|![Image](https://github.com/user-attachments/assets/349fcec5-f3f0-425d-a6fb-379cf7de09c0)|
<br/>
    
|좋아요 및 북마크|
|---|
|![Image](https://github.com/user-attachments/assets/2632dfd0-dd83-4eed-bb4f-8d68b71e9db3)|
<br/>
    
|질문 작성 및 조회|
|---|
|![Image](https://github.com/user-attachments/assets/1cd72e9c-303a-4d62-a71e-903f800289f4)|
<br/>
    
| 질문 답변 |
|---|
|![Image](https://github.com/user-attachments/assets/07613da5-4bd2-43b4-8097-8625b19bcce4)|
