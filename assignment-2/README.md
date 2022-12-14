# Wafflestudio Android Seminar Assignment2

## 과제의 목표

- 지금까지 배운 내용들을 활용하여 간단한 게시물 관리 앱을 제작합니다.
- 실제 게시글/댓글 관리를 위해 준비된 백엔드 서버와 통신하는 앱을 만듭니다.

## 과제 상세

- 앱 내의 로그인 / 회원가입(Optional) 기능, 회원가입된 유저의 경우 게시글과 댓글을 작성 및 삭제할 수 있는 앱을 만듦니다.
- 이 과정에서 필요한 회원 정보 및 게시글 / 댓글 정보 관리는 세미나를 위해 준비된 [백엔드 서버](https://seminar-android-api.wafflestudio.com) 를 사용합니다.
- 이때 세미나를 통해 배운 다양한 지식들을 활용하면 됩니다.

## 최소 스펙

### 로그인 페이지

1. 사용자는 사용자 이름과 비밀번호를 입력할 수 있습니다.
2. 입력된 사용자 이름과 비밀번호를 바탕으로 백엔드 서버에 로그인 요청을 보낼 수 있습니다.
3. 로그인 요청이 성공할 경우 인증 정보 (access token) 을 저장하고, 리스트 페이지로 넘어갈 수 있습니다.
4. 로그인 요청이 실패한 경우 서버에서 내려온 안내 메세지 등 적절한 안내 메세지를 띄워줍니다. 

### 리스트 페이지

1. 사용자는 현재 작성되어 있는 모든 유저의 게시글을 볼 수 있습니다.
2. 한번에 모든 정보를 가져오지 않고, cursor pagination 을 적용하여 사용자의 스크롤에 맞춰 게시글 정보를 가져옵니다.
3. 유저의 게시글은 `title`, `content`, `author` 등의 정보를 자유롭게 보여줍니다.
4. 각 게시글을 선택하면 게시글의 상세 페이지로 이동합니다.
5. 게시글 추가 버튼을 선택하면 게시글을 생성할 수 있는 팝업을 띄우고, 전달 받은 정보로 게시글을 생성합니다.

### 상세 페이지

1. 사용자는 게시글의 정보와 함께, 필요한 댓글의 정보를 볼 수 있습니다.
2. 사용자는 자신의 게시글을 삭제할 수 있습니다.
3. 사용자는 게시글에 댓글을 남길 수 있습니다.
4. 사용자는 게시글에 남긴 자신의 댓글을 삭제할 수 있습니다.


### 과제 진행에 필요한 정보들
1. 현재 위에서 언급된 게시글 삭제, 조회, 로그인 등의 요청들은 [백엔드 서버의 API 안내 페이지](https://seminar-android-api.wafflestudio.com/api)를 통해서 확인할 수 있습니다.
    - 어떤 요청을 보낼 수 있는지, 요청을 보낼 때 어떤 값을 전달해야 하는지, 어떤 값이 응답으로 오는지, 어떤 에러가 발생하는지 등을 확인할 수 있습니다. 불명확한 정보는 세미나 진행자에게 제보해 주세요.
2. 이번 과제의 로그인 기능을 제대로 구현하기 위해서는 access token 을 통한 유저 인증 방식을 공부해야 합니다. [참고자료](https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/Authorization)
    - 로그인 시 발급되는 토큰을 저장하고 있다가, 인증이 필요한 API 를 요청할 때 HTTP Header 에 함께 전달하는 방식으로 인증이 처리됩니다.
3. 이번 과제를 구현하기 위해서는 많은 양의 자료를 쪼개어 (필요한 만큼만) 전달받고, 필요시 더 요청하는 pagination (cursor pagination) 을 공부하면 좋습니다. [참고자료](https://velog.io/@minsangk/%EC%BB%A4%EC%84%9C-%EA%B8%B0%EB%B0%98-%ED%8E%98%EC%9D%B4%EC%A7%80%EB%84%A4%EC%9D%B4%EC%85%98-Cursor-based-Pagination-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0)
    - 특히, 안드로이드에서 Paging 을 구현하기 위해 일반적으로 사용되는 [Jetpack Paging](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) 라이브러리를 사용해서 구현하는 것을 매우 권장드립니다.
4. 이번 과제는 처음부터 끝까지 구현하는 난이도가 상당히 높은편에 속하여 도움을 드리기 위해 **실제 작동하는 완성된 데모 프로젝트 코드를 과제와 함께 공개** 합니다. [링크](https://github.com/wafflestudio/seminar-2022-android-assignment/tree/main/assignment-2/SimpleCMS-Demo)
    - ViewModel 이나 Rest API 와 관련된 코드는 참고할 수 있지만, UI 위한 코드는 기존 배우신 xml 기반이 아닌 Jetpack compose 를 기반으로 작성하였으니 이 부분은 따라 작성하지 마시고 스스로 작성해 보시길 추천드립니다.

## 앱 데모

https://user-images.githubusercontent.com/37951125/207618646-5a2de051-84eb-465c-8191-47521b874331.mov


## 과제 제출 방법

1. assignement 레포지토리를 fork 한다.
2. fork 한 소스코드를 clone 하여 `assignment-2/SimpleCMS` 파일 위에서 개발을 진행한다.
3. 개발 완료시 개발한 내용을 commit 하고 fork 한 레포지토리에 push 한다.
4. push 한 내용을 assignment 레포지토리에 pull reqest 로 날린다.
