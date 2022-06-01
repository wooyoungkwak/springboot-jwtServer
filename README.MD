## 설정
1. 배포시 war 파일 생성 (이름 : ROOT.war)
2. 배포시 내장 tomcat 은 사용하지 않도록 설정

## 의존성 Gradle  (initProject.gradle 참조)
1. initConfiguration.gradle
2. initDependencies.gradle
3. initDependencies-Jwt.gradle
4. initFile.gradle 

## 주의 사항
[ JPA - Hibernate ]
1. EntityManager 클래스는 @Autowired 가아니라 @PersistenceContext 를 사용한다. 
   (모든 @Autowired 사용하여 DI 하는것 보다는 @RequiredArgsConstructor 를 사용하여 DI 가 되도록 하는것이 좋다.)
2. QueryDSL 을 이용시 JPAQueryFactory 클래스를 이용한다. 
3. 복잡한 쿼리는 MYBATIS 를 이용하는것이 시간적 낭비가 준다.

[ JWT ]

