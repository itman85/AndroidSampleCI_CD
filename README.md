# AndroidSampleCI_CD
## Setup fastlane 
 - Create a Gemfile in the root directory of your Android project
 - Then just run the following command: *sudo gem install fastlane -NV*
 - Setup Fastlane using this command: *fastlane init* and follow the prompts to fill in the details about your app: package name, Path to the json secret file, Download existing metadata and setup metadata management (y) -> 
it will create Appfile and Fastfile in fastlane folder
 - Type *fastlane test* to check the setup -> it will print out **fastlane.tools finished successfully** if the setup successful done
 - Add the firebase plugin *fastlane add_plugin firebase_app_distribution*
 - 