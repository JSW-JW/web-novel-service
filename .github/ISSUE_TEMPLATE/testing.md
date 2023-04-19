---
name: testing
about: Describe this issue template's purpose here.
title: "[test]"
labels: test
assignees: JSW-JW

---

Feature
- [ ] 소장권 구매 성공
    - [ ] NovelTokenCounter 차감 여부 체크
- [ ] NovelTokenCounter 숫자값 바운더리 체크

예외 처리
- [ ] NovelTokenCounter 보유 개수 부족 시 InsufficientTokenError
- [ ]  구매하지 않은 소설인 경우 BadRequestException
