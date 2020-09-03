# My Places

## 개요

내 마음대로 지도 위에 마커를 표시하는 Naver API, SDK 활용 애플리케이션입니다.

### 기능

- 네이버 지도 위에 직접 마커를 만들어 관리합니다.
- 등록한 마커들을 BottomSheet에서 리스트로 확인합니다.
- 리스트의 아이템을 클릭하면 해당 마커로 포커스 됩니다.
- 위치 권한을 부여하면, 현재 위치로 지도가 이동합니다.

## 시작하기

### 전제 조건

최신 버전의 Android 빌드 도구 및 Android 지원 저장소가 필요합니다.

### 설치

이 샘플은 Gradle 빌드 시스템을 사용합니다.

이 저장소를 복제해서 **Android Studio**에 import 합니다.

```bash
git clone https://github.com/foreknowledge/my-places.git
```

### 구성

프로젝트 루트 디렉토리에 `apikey.properties` 파일을 아래와 같이 만듭니다.

```bash
NAVER_CLIENT_ID="발급 받은 Client key"
NAVER_CLIENT_SECRET="발급 받은 Secret key"
```

API key를 얻으려면 [네이버 지도 안드로이드 SDK 시작하기](https://navermaps.github.io/android-map-sdk/guide-ko/1.html) 가이드를 참고해서 발급 받을 수 있습니다.

## 시연 영상

| 마커 생성, 삭제 | 마커 리스트 |
|:--:|:--:|
| <img src="https://user-images.githubusercontent.com/29790944/79769954-b765e580-8367-11ea-80b6-889990b2061e.gif" width="80%"> | <img src="https://user-images.githubusercontent.com/29790944/79769574-445c6f00-8367-11ea-9837-2fe0d5c6b95f.gif" width="80%"> |

## 사용한 라이브러리

- Naver API
  - [Map SDK](https://docs.ncloud.com/ko/naveropenapi_v3/maps/android-sdk/v3/start.html)
  - [Reverse GeoCoding](https://docs.ncloud.com/ko/naveropenapi_v3/maps/reverse-geocoding/reverse-geocoding.html)
- AAC
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
  - [Room](https://developer.android.com/topic/libraries/architecture/room) 
  - [DataBinding](https://developer.android.com/topic/libraries/data-binding)
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [Retrofit](https://square.github.io/retrofit) 
