# [콜버스랩] 자리톡 채용 사전과제_천현우

> 실제 서비스를 배포하고 프론트와 실제 협업한다는 기준으로 개발을 진행하였습니다.
> 
> 일반 커뮤니티 서비스와 동일하게 회원가입 후 로그인을 진행하여 atk를 발급 받을 수 있습니다 😀

# 기능 구현
> 💡 JWT, SpringSecurity를 사용하여 외부사용자 및 회원의 임대인,임차인,공인중개사를 구분하여 해당 검증이 필요한 API의 경우 로그인을 통해 발급받은 AccessToken을 Authorization의 Bearer Token을 통해 구분합니다.
- 회원가입 (요구사항 외 기능)
  - 구현 : 임대인, 임차인, 공인중개사와 같은 항목을 선택하고 가입하는 형식으로 구현하였습니다.
  - 검증 : @NotNull을 통한 예외처리를 커스텀하여 핸들링 하였고 이미 존재하는 닉네임 또는 이메일인 경우 커스텀 예외처리 하였습니다.


- 로그인 (요구사항 외 기능)
  - 구현 : SpringSecurity, JWT를 이용하여 로그인을 하며 AccessToken을 발급합니다.
  - 검증 : @NotNull을 통한 예외처리를 커스텀하여 핸들링 하였고 존재하지 않는 계정이거나 아이디/비밀번호를 잘못입력하였을 경우 커스텀 예외처리 하였습니다.


- 글 목록 조회
  - 구현 :
    - 외부사용자도 조회가 가능하며 글에 달린 좋아요 수 및 회원인 경우 자신이 좋아요 한 글의 여부(외부사용자는 false)를 확인 할 수 있습니다.
    - 글을 작성한 사용자가 어떤 계정 타입인지 목록에 표시됩니다.
    - 페이징처리를 통하여 클라이언트가 요청할때 원하는 페이지, 하나의 페이지에 최대 글 개수, 정렬 기준, 정렬 방식에 대해 설정 할 수 있게끔 구현했습니다. 
  - 검증 : 
    - SpringSecurity와 JWT를 이용하여 회원인지 회원이 아닌지의 여부를 구분합니다.
    - @RequestParam의 MissingServletRequestParameterException을 핸들링 하여 Null 값을 예외처리 하였습니다.
    - 잘못된 정렬 기준, 한 페이지에 너무 많은 게시글을 요청, 잘못된 정렬 방식인 경우 커스텀 예외처리 하였습니다.


- 글 작성
  - 구현 : 클라이언트는 글 제목, 글 내용, 글 이미지를 보낼 수 있고 나머지 컬럼은 default 값이 들어가게 구현했습니다.
  - 검증 : 
    - @NotNull을 통한 예외처리를 커스텀 하여 핸들링 했습니다.
    - SpringSecurity와 JWT를 이용하여 임대인, 임차인, 공인중개사를 구분하며 회원인 경우에만 글을 작성할 수 있습니다.


- 글 수정
  - 구현 : 글 제목, 글 내용, 글 이미지가 수정 가능 하게끔 구현했습니다.
  - 검증 : @NotNull을 통한 예외처리를 커스텀하여 핸들링 하였고 유저가 다른 유저의 게시글을 수정하려한 경우 커스텀 예외처리 하였습니다.


- 글 삭제
  - 구현 : delete_at 컬럼을 이용해 soft delete로 구현하였습니다.
  - 검증 : @NotNull을 통한 예외처리를 커스텀하여 핸들링 하였고 유저가 다른 유저의 게시글을 삭제하려한 경우 커스텀 예외처리 하였습니다.


- 글 좋아요 등록 및 취소
  - 구현 : 글에 좋아요는 한 계정이 한 글에 한번만 하며, 좋아요 등록 여부를 통해 좋아요가 등록되거나 삭제되게끔 구현했습니다.
  - 검증 : @NotNull을 통한 예외처리를 커스텀하여 핸들링 하였고 자신이 등록한 좋아요가 아닌 것을 삭제할경우 커스텀 예외처리 하였습니다.


- 그 외 사항
  - 별도의 회원 테이블(User)을 사용합니다.
  - DB에 Likes schema를 통해 어떤 사용자가 어떤 글에 좋아요 했는지 히스토리를 확인할 수 있습니다.
  - DB에 Board schema를 통해 각 글의 작성시간, 마지막 수정시간, 삭제시간에 대한 히스토리를 확인할 수 있습니다.
  
# ERD 및 API 문서
- https://striped-sponge-381.notion.site/API-4405dd9224d94f4cb0574d06a3bf6e97

# 기타사항
- 기술 요구사항에 히스토리를 확인하는 것은 DB에 기록을 남기는것으로 이해하여 글 상세 조회 API 및 자신이 좋아요 누른 리스트 조회 API는 제외하였습니다.

- spring.io외 사용한 Dependencies

    ```bash
    runtimeOnly group: 'io.jsonwebtoken', name: 'jjwt-impl', version: '0.11.2'
    runtimeOnly group: 'io.jsonwebtoken', name: 'jjwt-jackson', version: '0.11.2'
    ```
  - 해당 프로젝트에 JWT를 적용하기 위해 추가하였습니다. 
    - 해당 커뮤니티 서버는 JWT를 사용하지 않더라도 Authentication 헤더의 값을 만들어 구현할 수 있지만 실제 서비스라고 생각함에있어 보안을 강화하고자 프로젝트에 도입하였습니다.


- 요구사항 외 필요한 조건 및 구현
    - 실제 서비스를 개발한다는 기준 하에 해당 커뮤니티를 사용하기위한 회원가입이 서비스에 필요해 구현하였습니다.
    - 로그인 API를 통하여 JWT 토큰을 발급받아 해당 토큰으로 임대인, 임차인, 공인중개사를 구분할 수 있으며 유저 정보 접근에 편의성과 보안성을 강화하였습니다. (글 목록은 외부사용자도 조회할 수 있습니다!)
    - RTK와 로그아웃 API를 Redis를 통해 구현하려했으나, 과제 프로젝트의 목적이 흐려지는 것 같아 해당 부분은 제외하였습니다.

# Q. 질문있습니다! 🤔

>💡 기능 요구사항 4번 : 커뮤니티에 가입한 사용자라면 글 목록에 자신이 좋아요한 글인지 아닌지를 표시해줄 수 있어야 합니다.

### 해당 기능을 구현하였지만, 의문점이 생겨서 질문드립니다 !

→ 글 목록 조회시 페이징 처리를 통해 구현하였으며, 가입되지않은 유저 / 가입된 유저로 1차 구분후 가입되지 않은 유저라면 당연히 좋아요를 등록 할 수 없기에 모든 좋아요 등록 상태는 false로 떨어져 성능에 문제가 없다 생각하지만 가입된 유저라면 게시글을 stream 할때마다 좋아요를 등록했는지 체크하게 되며 성능에 저하가 올 수 있다고 생각합니다.

타 앱 / 사이트의 경우 자신이 좋아요를 했는지 체크하는 경우는 보통 목록이 아닌 글을 상세 조회 하였을때 확인이 된다고 생각돼서 질문이 생겼습니다. ex) velog, tistory

### 이에 따른 질문사항 🤔

1. 요구사항에서 해당 기능이 글 목록 조회에서는 성능 저하를 불러오는건 아닌지 궁금합니다 ! (페이징처리해서 10개씩 가져오게끔 만들었지만, 추후에 많은 사람들이 좋아요를 눌렀다 가정하면 결국은 JPA로 Likes 테이블에 많은 데이터를 하나씩 검증하는 것이 성능저하를 일으키진 않을까 하는 생각입니다)
2. 제가 프로그래밍한 코드가 효율적이지 못할 확률이 높기에 추후 코드 개선을 하고 싶어서 Likes 테이블의 컬럼을 페이징 처리시 좋아요 등록 여부를 하나씩 검증하지 않고 한번에 하는 코드 또는 정답이 있다면 개선된 코드를 알고싶습니다 🥹
3. 궁금한걸 참지 못하는 성격이라 이렇게라도 여쭤보려합니다! 감사합니다 😊