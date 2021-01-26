# `expirable-cache`


# Introduction
`ExpirableCache ` is a Java cache abstraction which provides consistent use for caching solutions.   
The annotation in `ExpirableCache ` supports native TTL, 
The features of `ExpirableCache`:

* `Spring Boot`
* `Redis Lock `
* `Zookeeper Lock`
* `Redis Cache`
* `Local Cache`
* ...

requirements:
* `JDK1.8`
* ...



## `Install`

```shell
$ git clone https://github.com/photowey/expirable-cache.git

$ ./gradlew publishToMavenLocal
$ ./gradlew packageSources
$ ./gradlew uploadArchives
```



## `Method Cache`

 ```text
Declare method cache using `@ExpirableCache` annotation.  
 ```



## `Maven`

```xml
<expirable-cache.version>1.0.0-SNAPSHOT</expirable-cache.version>
```
```xml
<dependency>
    <groupId>com.photowey</groupId>
    <artifactId>expirable-cache-spring-boot-starter</artifactId>
    <version>${expirable-cache.version}</version>
</dependency>
```



## `Gradle`

```gradle
ext {
	EXPIRABLE_VERSION = "1.0.0-SNAPSHOT"
}
```

```gradle
compile "com.photowey:expirable-cache-spring-boot-starter:${EXPIRABLE_VERSION}"
```



## USAGE

> Use `redis ` as lock candidate.

```java
    @Autowired
    private RemoteUserMapper remoteUserMapper;

    @ExpirableCache(
            key = "'remote.user.id:'+#remoteUserId",
            cacheCandidate = "redis",
            expire = 2L,
            timeUnit = TimeUnit.MINUTES
    )
    public RemoteUser findRemoteUserById(Long remoteUserId) {
        log.info("handle query the RemoteUser in db:[{}]", remoteUserId);
        return this.remoteUserMapper.selectById(remoteUserId);
    }

 // key: cache key
 // cacheCandidate: use REDIS as cache
 // expire: cache ttl
 // lock: use the redis lock as the DEFAULT lock 
```





> Use `zookeeper` as lock candidate.

```java
    @Autowired
    private RemoteUserMapper remoteUserMapper;

    @ExpirableCache(
            key = "'remote.user.id:'+#remoteUserId",
            cacheCandidate = "redis",
            expire = 2L,
            timeUnit = TimeUnit.MINUTES,
            lock = @CacheLock(lockCandidate = "zookeeper")
    )
    public RemoteUser findRemoteUserById(Long remoteUserId) {
        log.info("handle query the RemoteUser in db:[{}]", remoteUserId);
        return this.remoteUserMapper.selectById(remoteUserId);
    }

 // key: cache key
 // cacheCandidate: use REDIS as cache
 // expire: cache ttl
// lockCandidate: use ZOOKEEPER as lock
```

