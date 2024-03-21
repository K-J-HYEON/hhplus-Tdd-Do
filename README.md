
## hhplusTddPractice

1. 1주차 TDD 과제



패키지의 TODO 와 테스트코드를 작성


**요구 사항**

- PATCH  `/point/{id}/charge` : 포인트를 충전한다.
- PATCH `/point/{id}/use` : 포인트를 사용한다.
- GET `/point/{id}` : 포인트를 조회한다.
- GET `/point/{id}/histories` : 포인트 내역을 조회한다.
- 잔고가 부족할 경우, 포인트 사용은 실패한다.
- 동시에 여러 건의 포인트 충전, 이용 요청이 들어올 경우 순차적으로 처리되어야 한다.


학습내용
TDD에서 사용되는 @Mock과 @Stub에 대해서 학습하고 테스트 코드 작성 시 동시성 처리에 대해서 학습한다.

 - [@Mock vs @Stub](https://azderica.github.io/00-test-mock-and-stub/)

 - [테스트 코드 동시성 처리](https://sigridjin.medium.com/weekly-java-%EA%B0%84%EB%8B%A8%ED%95%9C-%EC%9E%AC%EA%B3%A0-%EC%8B%9C%EC%8A%A4%ED%85%9C%EC%9C%BC%EB%A1%9C-%ED%95%99%EC%8A%B5%ED%95%98%EB%8A%94-%EB%8F%99%EC%8B%9C%EC%84%B1-%EC%9D%B4%EC%8A%88-9daa85155f66)
