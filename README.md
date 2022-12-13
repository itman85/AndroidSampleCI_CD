# AndroidSampleCI_CD
## Setup fastlane 
 - Create a Gemfile in the root directory of your Android project
 - Then just run the following command: *sudo gem install fastlane -NV*
 - Setup Fastlane using this command: *fastlane init* and follow the prompts to fill in the details about your app: package name, Path to the json secret file, Download existing metadata and setup metadata management (y) -> 
it will create Appfile and Fastfile in fastlane folder
 - Type *fastlane test* to check the setup -> it will print out **fastlane.tools finished successfully** if the setup successful done
 - Add the firebase plugin *fastlane add_plugin firebase_app_distribution*
 - Type command in root folder of project to see all actions of plugin firebase_app_distribution *bundle exec fastlane action firebase_app_distribution*
 - Getting the Firebase App ID: go to firebase console -> add project -> add app -> copy App ID in project settings
 - Go to Google cloud platform -> select project created from firebase -> create a new service account -> add role 'Firebase Admin' 'Firebase App Distribution Admin', then create key json file download and add into project name firebase_credentials.json or copy json into github secrets
 - To run fastlane local it need to run this **export FIREBASE_APP_ID=YOUR_APP_ID**
 - Configure Firebase App Distribution: Create a new directory FirebaseAppDistributionConfig under the root directory of your project. Create a new file groups.txt in the created directory. Create a new file release_notes.txt in the same directory.
 - create distribute lane (in Fastfile)
 - Run local by command *fastlane distribute*
 - Use firebase cli token instead of firebase_credentials.json
   - Install firebase tool *npm install -g firebase-tools*
   - Type command *firebase login:ci* it will print firebase cli token
   - Type this command to test firebase cli token *firebase projects:list --token FIREBASE_CLI_TOKEN* -> list all projects in firebase account 
 - Add bundletool plugin *fastlane add_plugin bundletool* this tool will extract an universal apk from .aab file to distribute it to firebase, app center, store, ...
## Setup github action:
 - convert firebase credentials file to base64 string use command *base64 -i firebase_credentials.json > firebase_credentials.json.b64*
 - add into github secrets: FIREBASE_CREDENTIALS, FIREBASE_APP_ID
 - create workflow distribute.yml 
 - Push code to main branch to see how github actions distribute workflow run and distribute apk to firebase app distribution
## Create keystore cmd
keytool -genkey -v -keystore YOUR_FILE_NAME.keystore -alias YOUR_ALIAS_NAME -storepass YOUR_ALIAS_PWD -keypass YOUR_ALIAS_PWD -keyalg RSA -validity 36500

## Create base64
base64 -i firebase_credentials.json > firebase_credentials.json.b64
