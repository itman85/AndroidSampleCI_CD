# AndroidSampleCI_CD
## Setup fastlane 
 - Create a Gemfile in the root directory of your Android project
 - Then just run the following command: *sudo gem install fastlane -NV*
 - Setup Fastlane using this command: *fastlane init* and follow the prompts to fill in the details about your app: package name, Path to the json secret file, Download existing metadata and setup metadata management (y) -> 
it will create Appfile and Fastfile in fastlane folder
 - Type *fastlane test* to check the setup -> it will print out **fastlane.tools finished successfully** if the setup successful done
 - Add the firebase plugin *fastlane add_plugin firebase_app_distribution*
 - Getting the Firebase App ID: go to firebase console -> add project -> add app -> copy App ID in project settings
 - Go to Google cloud platform -> select project created from firebase -> create a new service -> add role 'Firebase Product' 'Firebase App Distribution Admin', then create key json file download and add into project name firebase_credentials.json
 - To run fastlane local it need to run this **export FIREBASE_APP_ID=YOUR_APP_ID**
 - Configure Firebase App Distribution: Create a new directory FirebaseAppDistributionConfig under the root directory of your project. Create a new file groups.txt in the created directory. Create a new file release_notes.txt in the same directory.
 - create distribute lane (in Fastfile)
 - Run local by command *fastlane distribute*
## Setup github action:
 - convert firebase credentials file to base64 string use command *base64 -i firebase_credentials.json > firebase_credentials.json.b64*
 - add into github secrets: FIREBASE_CREDENTIALS, FIREBASE_APP_ID
 - create workflow distribute.yml 
 - Push code to main branch to see how github actions distribute workflow run and distribute apk to firebase app distribution