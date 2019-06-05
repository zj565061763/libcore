# Gradle
[![](https://jitpack.io/v/zj565061763/libcore.svg)](https://jitpack.io/#zj565061763/libcore)

# 项目Module需要支持java8
在module的build.gradle文件中增加如下配置
```
android {

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }

}
```