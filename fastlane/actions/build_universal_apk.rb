module Fastlane
  module Actions
    module SharedValues
      BUILD_UNIVERSAL_APK_CUSTOM_VALUE = :BUILD_UNIVERSAL_APK_CUSTOM_VALUE
    end

    class BuildUniversalApkAction < Action
      def self.run(params)
        sh "chmod +x scripts/bundle_debug.sh"
        sh "scripts/bundle_debug.sh"
        sh "unzip app/build/outputs/bundle/debug/app_demo_debug.apks -d app/build/outputs/bundle/debug/apks"
      end

      #####################################################
      # @!group Documentation
      #####################################################

      def self.description
        "Builds universal apk"
      end

      def self.details
        # Optional:
        # this is your chance to provide a more detailed description of this action
        "Goes into the output direction of the build app bundle and builds the APK set archive with just the single universal apk file to distribute for testing purposes. The apk is than extracted and set for the next uploading"
      end

      def self.available_options
      end

      def self.output
        # Define the shared values you are going to provide
        # Example
        [
          ['BUILD_UNIVERSAL_APK_CUSTOM_VALUE', 'A description of what this value contains']
        ]
      end

      def self.return_value
        "The APK set archive build with bundletool" # If your method provides a return value, you can describe here what it does
      end

      def self.authors
        # So no one will ever forget your contribution to fastlane :) You are awesome btw!
        ["pgrube26"]
      end

      def self.is_supported?(platform)
        # you can do things like
        # 
        #  true
        # 
        #  platform == :ios
        # 
        #  [:ios, :mac].include?(platform)
        # 

        platform == :android
      end
    end
  end
end
