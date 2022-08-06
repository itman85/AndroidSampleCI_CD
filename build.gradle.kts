buildscript {
    extra.apply {
        set("compose_version", "1.1.1")
    }

    dependencies {
        classpath("com.google.gms:google-services:4.3.13")  // Google Services plugin
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.3.0-beta04" apply false
    id("com.android.library") version "7.3.0-beta04" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
}