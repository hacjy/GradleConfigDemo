**基于Android Studio3.0**

**1、依赖库的统一管理配置**
**-创建config.gradle 关键字ext**
**-如何使用**：首先，**在工程的build.gradle中导入该配置文件：apply from:"config.gradle"**；
然后，使用rootProject对象调用，如rootProject.ext.android["compileSdkVersion"]。

**2、生成签名打包**
创建签名：**Build->Generate Signed Apk**,然后Create new keystore。
之后在app中的build.gradle中设置签名(**必须设置在buildType之前**）
```
signingConfigs {
        release {
            storeFile file('D:/workspace/keystore.jks')
            keyAlias 'androidKeystore'
            keyPassword 'android'
            storePassword 'android'
        }
    }
```
当然不喜欢手动写的话，也可以设置**自动生成：Project Structure->app->Signing添加一个签名即可**。
Generate Signed Apk这种方式生产的apk是放在app目录下对应的release/debug目录下。当然，如果使用gradle的命令去打包，生成的apk是存放在app->build-output-apk目录下。

**3、修改打包出的apk名称**
**studio3.0 gradle api有变动**，具体请看：https://developer.android.google.cn/studio/build/gradle-plugin-3-0-0-migration.html#variant_api

具体代码如下：
```
android.applicationVariants.all { variant ->
        variant.outputs.all {
            //修改apk名称variant.name对应buildType.name
            outputFileName = "${variant.name}-${project.archivesBaseName}-

${variant.versionCode}-${variant.versionName}-${releaseTime()}.apk"
        }
    }
```

**4、多渠道打包配置**
**-必须声明flavorDimensions**：flavorDimensions("default")
studio提示：Error:All flavors must now belong to a named flavor dimension. Learn more at https://d.android.com/r/tools/flavorDimensions-missing-error-message.html

**-定义渠道名称**
```
 flavorDimensions("default")
 productFlavors{
        xiaomi{dimension "default"}
        huawei{dimension "default"}
        baidu{dimension "default"}
    }
 productFlavors.all{
        flavor->flavor.manifestPlaceholders = [UMENG_CHANNEL_VALUE:name]
    }
```

**5、通过buildTypes根据环境设置测试/生产环境地址**
```
 buildTypes {
        debug{
            minifyEnabled false
            //配置api地址,在BuildConfig可以找到该字段
            buildConfigField ("String", "API_URL", "\"http://debug.com\"")
        }

        release {
            minifyEnabled true
            //配置api地址,在BuildConfig可以找到该字段
            buildConfigField("String","API_URL","\"http://release.com\"")
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-

rules.pro'
        }
    }
```