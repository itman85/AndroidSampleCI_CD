#!/usr/bin/env sh
rm app/build/outputs/bundle/debug/app_demo_debug.apks
java -jar ${ANDROID_HOME}/platform-tools/bundletool.jar build-apks --bundle app/build/outputs/bundle/demoDebug/app_demo_debug.aab --output app/build/outputs/bundle/debug/app_demo_debug.apks --mode=universal
