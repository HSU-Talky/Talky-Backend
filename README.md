# [Talky] - Frontend

> 제 21회 한성공학경진대회 프론트엔드  
> 상황별 대화 추천 서비스

---

## 📌 프로젝트 소개

Talky는 언어장애인을 위한 자동 문장 추천 앱서비스입니다.  
장소, 시간을 기반으로 문자을 추천하고, TTS를 이용해 텍스트를 음성으로 변환합니다.

---
## ✨기술 스택

### 백엔드 (Spring + FastAPI)
| 구분 | 기술                             |
|------|--------------------------------|
| 언어 | Java 21, Python 3.10           |
| 프레임워크 | Spring Boot, FastAPI           |
| ORM | JPA (Hibernate)                |
| DB | MySQL                          |
| API 통신 | REST API (Spring ↔ FastAPI 연결) |
| AI 모델 처리 | Python 기반 사용자 맞춤 문장 추천         |

---

### Git 명령어

| 명령어 | 설명 |
| --- | --- |
| `git clone <url>` | 원격 저장소 복제 |
| `git add .` | 전체 변경 파일 스테이징 |
| `git add <파일명>` | 특정 파일만 스테이징 |
| `git commit -m "메시지"` | 커밋 메시지와 함께 커밋 |
| `git log` | 커밋 히스토리 확인 |
| `git branch` | 현재 브랜치 목록 확인 |
| `git checkout <이름>` | 해당 브랜치로 이동 |
| `git checkout -b <이름>` | 새 브랜치 생성 + 이동 |
| `git push` | 현재 브랜치 내용을 원격 `main`에 푸시 |
| `git pull origin main` | 원격 `main` 브랜치 내용 가져오기 (병합) |

## 🌿 Git Flow 브랜치 전략 (with `main`) 🌿


### 🌴 기본 브랜치
| 브랜치 | 역할 |
|--------|------|
| `main`     | 최종 배포용 브랜치 (stable) |
| `develop`  | 다음 배포를 위한 통합 개발 브랜치 |

<br>

### 🌱 **작업 브랜치 네이밍 규칙**

```
type/#issue번호  (작업 단위는 기능/수정/리팩토링 등으로 구분)
``` 


| prefix       | 설명                         | 예시                              |
|--------------|------------------------------|-----------------------------------|
| `feature/`   | ✨ 새로운 기능 개발             | `feature/#15`           |
| `fix/`       | 🐛 버그 수정                    | `fix/#42`     |
| `refactor/`  | ♻️ 코드 리팩토링                | `refactor/#23`       |
| `chore/`     | 🔧 설정 변경, 잡일              | `chore/#25`        |
| `perf/`      | ⚡ 성능 개선                    | `perf/#94`        |
| `hotfix/`    | 🚑 급한 수정 (main에서 바로 분기) | `hotfix/#102`          |
| `test/`      | 🧪 테스트 코드 추가/수정        | `test/#55`    |

<br>

### 🚀 브랜치 흐름 요약

```text
1. main ← 배포
2. develop ← 통합 개발 (PR 대상)
3. develop에서 feature/fix/... 브랜치 분기
4. 기능 완료 후 develop으로 PR & 머지
5. 배포 시 develop → main 머지
6. 급한 수정은 hotfix에서 main → develop 병합
```

---