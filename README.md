## CleanArchitecture
안드로이드를 위한 클린 아키텍처 학습 프로젝트

## Description
- Architectural Patterns : MVP(Model, Viewer, Presenter)
- Dependency Injection : Dagger2(학습 중)
- Reactive Programming : RxJava2
- Network Module : Retrofit2
- Database : 미정 (하단은 DB 종류와 개인적인 견해)
  1. Realm : 객체형 데이터베이스의 성향이 강함 (ODB), 관계를 설정할 수는 있으나 답답함<br>
     https://realm.io/docs
  2. Room : 관계형 데이터베이스에 객체형 데이터베이스의 편리함을 적절하게 가져다 놓은 느낌 (RDB? ORDB??)<br>
     https://developer.android.com/training/data-storage/room/
  3. SQLite : 전형적인 관계형 데이터베이스 (RDB), 데이터베이스의 성향이 강함<br>
     https://www.sqlite.org/about.html
  4. ObjectBox : 비정형 혹은 대량의 데이터를 모바일에서 처리하기 위해 나온 데이터베이스인듯 (ODB)<br>
     https://github.com/objectbox/objectbox-java
- Unit Test : Mockito, espresso(학습 중)
