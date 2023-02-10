import org.jetbrains.kotlin.util.capitalizeDecapitalize.capitalizeAsciiOnly
import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")  // Google Services plugin
}

android {
    namespace = "com.picoder.androidsamplecicd"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.picoder.androidsamplecicd"
        minSdk = 21
        targetSdk = 33
        versionCode = Utilities.buildNumber(projectDir)
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    flavorDimensions += "app"
    productFlavors{
        create("demo") {
            dimension = "app"
            applicationId = "com.picoder.androidsamplecicd.demo"
            versionName = "3.3"
        }

        create("prod") {
            dimension = "app"
            applicationId = "com.picoder.androidsamplecicd.prod"
            versionName = "3.0"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        create("alpha") {
            initWith(getByName("release"))
            applicationIdSuffix = ".alpha"
            versionNameSuffix = "-alpha"

            productFlavors.getByName("demo") {
                signingConfig = signingConfigs.create("demo-alpha")
            }
            productFlavors.getByName("prod") {
                signingConfig = signingConfigs.create("prod-alpha")
            }
        }

        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isDebuggable = true
            isMinifyEnabled = false
            signingConfig = signingConfigs.getAt("debug")
        }
    }

    signingConfigs {
        // use local_secrets for testing in case running CI, this folder will not existed in github env
        val secretsPropertiesFile = rootProject.file("local_secrets/secrets.properties")
        val secretProperties = Properties()
        if (secretsPropertiesFile.exists()) {
            // local build
            secretProperties.load(FileInputStream(secretsPropertiesFile))
            // not necessary for debug
            getByName("debug") {
                storeFile = file("${secretProperties["debugStorePath"]}")
                storePassword = "${secretProperties["debugStorePw"]}"
                keyAlias = "${secretProperties["debugAlias"]}"
                keyPassword = "${secretProperties["debugKeyPw"]}"
            }

            getByName("demo-alpha") {
                storeFile = file("../${secretProperties["demoAlphaStorePath"]}")
                storePassword = "${secretProperties["demoAlphaStorePw"]}"
                keyAlias = "${secretProperties["demoAlphaAlias"]}"
                keyPassword = "${secretProperties["demoAlphaKeyPw"]}"
            }

            getByName("prod-alpha") {
                storeFile = file("../${secretProperties["prodAlphaStorePath"]}")
                storePassword = "${secretProperties["prodAlphaStorePw"]}"
                keyAlias = "${secretProperties["prodAlphaAlias"]}"
                keyPassword = "${secretProperties["prodAlphaKeyPw"]}"
            }
            // not defined signing config for release build of demo and prod flavor

        }else{
            // CI alpha build
            getByName("demo-alpha") {
                storeFile = file(System.getenv("ALPHA_KEYSTORE_FILE_PATH"))
                storePassword = System.getenv("DEMO_ALPHA_KEYSTORE_PW")
                keyAlias = System.getenv("DEMO_ALPHA_KEYSTORE_ALIAS")
                keyPassword = System.getenv("DEMO_ALPHA_KEY_PW")
            }

            getByName("prod-alpha") {
                storeFile = file(System.getenv("ALPHA_KEYSTORE_FILE_PATH"))
                storePassword = System.getenv("PROD_ALPHA_KEYSTORE_PW")
                keyAlias = System.getenv("PROD_ALPHA_KEYSTORE_ALIAS")
                keyPassword = System.getenv("PROD_ALPHA_KEY_PW")
            }
            // CI prod build (do same to alpha but with different keystore info)
        }
    }



    applicationVariants.all {
        val outputApkFileName = "${this.flavorName}-v${this.versionName}+${this.versionCode}.apk"
        val outputAabFileName = "${this.flavorName}-v${this.versionName}+${this.versionCode}.aab"

        outputs.all {
            val output = this as? com.android.build.gradle.internal.api.BaseVariantOutputImpl
            output?.outputFileName = outputApkFileName

            // Get final bundle task name for this variant
            val bundleFinalizeTaskName = StringBuilder("sign").run {
                // Add each flavor dimension for this variant here
                productFlavors.forEach {
                    append(it.name.capitalizeAsciiOnly())
                }
                // Add build type of this variant
                append(buildType.name.capitalizeAsciiOnly())
                append("Bundle")
                toString()
            }
            tasks.named(bundleFinalizeTaskName, com.android.build.gradle.internal.tasks.FinalizeBundleTask::class.java) {
                val file = finalBundleFile.asFile.get()
                val finalFile = File(file.parentFile, outputAabFileName)
                finalBundleFile.set(finalFile)
            }
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as? String
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val compose_version = rootProject.extra["compose_version"] as? String

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.compose.ui:ui:$compose_version")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")
    implementation("androidx.compose.material:material:1.1.1")
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:30.3.0"))


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")
    debugImplementation("androidx.compose.ui:ui-tooling:$compose_version")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$compose_version")
}
