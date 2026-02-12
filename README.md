# UVC Camera Demo for Q-Face Pro

Q-Face Pro 장치의 UVC 카메라 스트리밍을 테스트하는 간단한 Android 앱입니다.

## 프로젝트 구조

```
qface-test-app/
├── app/
│   ├── build.gradle.kts          # 앱 빌드 설정
│   ├── src/main/
│   │   ├── AndroidManifest.xml   # 앱 매니페스트 (USB 권한)
│   │   ├── java/.../MainActivity.kt  # UVC 카메라 액티비티
│   │   └── res/
│   │       ├── layout/activity_main.xml  # UI 레이아웃
│   │       └── xml/device_filter.xml     # USB 장치 필터
│   └── proguard-rules.pro
├── build.gradle.kts              # 프로젝트 빌드 설정
├── settings.gradle.kts
└── gradle.properties
```

## 사용된 라이브러리

- **AndroidUSBCamera** (`com.github.chenyeju295.AndroidUSBCamera:libausbc:3.3.6`)
  - UVC 카메라 스트리밍을 위한 라이브러리
  - 원본: https://github.com/jiangdongguo/AndroidUSBCamera
  - 사용 버전: chenyeju295 포크 (원본 3.3.3은 JitPack AAR 업로드 오류)

## Q-Face Pro 카메라 구조

Q-Face Pro는 두 개의 카메라를 가집니다:

1. **UVC 카메라** (이 앱에서 사용)
   - 일반적인 USB Video Class 카메라
   - 사용자에게 미리보기 화면 제공
   - VID: 0x36E5 (14053), PID: 0x0101 (257)

2. **IR 카메라** (별도 앱에서 사용)
   - USB 시리얼 통신으로 제어
   - 얼굴 인식 처리

## 빌드 및 실행

1. Android Studio에서 `qface-test-app` 폴더 열기
2. Gradle Sync 실행
3. USB 디버깅이 활성화된 안드로이드 장치에 설치
4. Q-Face Pro 연결 시 자동으로 앱 실행 또는 수동 실행

## 요구 사항

- Android 7.0 (API 24) 이상
- USB OTG 지원 장치
- Q-Face Pro 또는 기타 UVC 호환 카메라

## 주요 코드

### MainActivity.kt

```kotlin
class MainActivity : CameraActivity() {
    override fun getCameraRequest(): CameraRequest {
        return CameraRequest.Builder()
            .setPreviewWidth(720)
            .setPreviewHeight(1280)
            .create()
    }

    override fun onCameraState(camera, code, message) {
        // 카메라 상태 처리 (연결, 해제, 에러)
    }
}
```

## 다음 단계

이 UVC 카메라 데모를 확장하여:
1. IR 카메라 시리얼 통신 추가
2. 얼굴 인식 SDK 통합
3. 출석 체크 기능 구현
