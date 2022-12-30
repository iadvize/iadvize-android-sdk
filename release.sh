#!/bin/bash

# Exit script at first error:
set -e

function printHelp() {
  echo ""
  echo "USAGE: release.sh <command>"
  echo "    help"
  echo "        Prints this message"
  echo ""
  echo "    start"
  echo "        Starts the release process."
  echo ""
  echo "    finish"
  echo "        Ends a previously started release process."
}

function checkArtifact() {
  if [ -f "IAdvizeSDK.zip" ]; then
    echo -e "\033[1;42m => Found release artifact. Unzipping. \033[0m"
    rm -Rf tmp
    unzip -q IAdvizeSDK.zip -d tmp

    echo -e "\033[1;42m => Extracting version name \033[0m"
    versionName=`ls -la tmp | grep aar | sed "s/\(.*\)iadvize-sdk-\(.*\).aar/\2/"`

    if [ -d "com/iadvize/iadvize-sdk/${versionName}" ]; then
      echo -e "\033[1;101m Release ${versionName} already exists. Aborting. \033[0m"
      exit 1
    fi
  else
    echo -e "\033[1;101m No release artifact found. \033[0m"
    exit 1
  fi
}

function updateReleaseFiles() {
  echo -e "\033[1;42m => Creating release folder for ${versionName} \033[0m"

  mkdir com/iadvize/iadvize-sdk/${versionName}
  cp tmp/iadvize-sdk-${versionName}.aar com/iadvize/iadvize-sdk/${versionName}
  cp tmp/iadvize-sdk-${versionName}.pom com/iadvize/iadvize-sdk/${versionName}

  echo -e "\033[1;42m => Updating artifact version metadata \033[0m"
  sed -i '' "s/^  <version>\(.*\)<\/version>/  <version>${versionName}<\/version>/" com/iadvize/iadvize-sdk/maven-metadata.xml
  sed -i '' "s/^    <latest>\(.*\)<\/latest>/    <latest>${versionName}<\/latest>/" com/iadvize/iadvize-sdk/maven-metadata.xml
  sed -i '' "s/^    <release>\(.*\)<\/release>/    <release>${versionName}<\/release>/" com/iadvize/iadvize-sdk/maven-metadata.xml
  sed -i '' "s/^    <\/versions>/      <version>${versionName}<\/version>|    <\/versions>/" com/iadvize/iadvize-sdk/maven-metadata.xml
  tr '|' '\n' < com/iadvize/iadvize-sdk/maven-metadata.xml > tmp.txt
  mv tmp.txt com/iadvize/iadvize-sdk/maven-metadata.xml

  now=`date +"%Y%m%d%H%M0000"`
  sed -i '' "s/^    <lastUpdated>\(.*\)<\/lastUpdated>/    <lastUpdated>${now}<\/lastUpdated>/" com/iadvize/iadvize-sdk/maven-metadata.xml

  echo -e "\033[1;42m => Updating demo app build.gradle.kts to target latest SDK. \033[0m"
  sed -i '' "s/(\"com.iadvize:iadvize-sdk:\(.*\)\")/(\"com.iadvize:iadvize-sdk:${versionName}\")/" mobile/build.gradle.kts

  echo -e "\033[1;42m => Updating CHANGELOG, UPGRADING & README. \033[0m"
  mv tmp/CHANGELOG.md CHANGELOG.md
  mv tmp/UPGRADING.md UPGRADING.md
  mv tmp/README.md README.md

  echo -e "\033[1;42m => Updating documentation \033[0m"
  rm -rf docs
  mv tmp/docs docs
}

function printStartSuccess() {
  echo -e "\033[1;42m => Release ${newVersionName} is applied! This is what remains for you to do: \033[0m"
  echo -e "\033[1;95m - Test the sample project locally with this release \033[0m"
  echo -e "\033[1;95m - Execute './scripts/release.sh finish' to continue the release process \033[0m"
}

function checkCurrentRelease() {
  if [ -d "tmp" ]; then
    echo -e "\033[1;42m => Extracting version name \033[0m"
    versionName=`ls -la tmp | grep aar | sed "s/\(.*\)iadvize-sdk-\(.*\).aar/\2/"`
  else
    echo -e "\033[1;101m No ongoing release found. \033[0m"
    exit 1
  fi
}

function requestFinishApproval() {
  echo -e "\033[1;31m WARNING - You are about to push release ${versionName} to the public repository. \033[0m"
  echo -e "\033[1;31m Proceed ? [y/n] \033[0m"
  read -s -n 1 key
  case $key in
    y) ;;
    *)
      exit 0
  esac
}

function commitPushRelease() {
  echo -e "\033[1;42m => Committing/pushing version update \033[0m"
  git add --all
  git commit -m "(build) publish version ${versionName}" --quiet
  git tag "${versionName}"
  git push origin master --tags
}

function cleanRelease() {
  rm -R tmp
  rm IAdvizeSDK.zip
}

function printFinishSuccess() {
  echo -e "\033[1;42m => Release ${versionName} is now public! This is what remains for you to do: \033[0m"
  echo -e "\033[1;95m - Create a github release from tag ${versionName} : https://github.com/iadvize/iadvize-android-sdk/releases/new \033[0m"
  echo -e "\033[1;95m - Fill description with changelog info \033[0m"
}

if [[ "$1" == "start" ]]; then
  checkArtifact
  updateReleaseFiles
  printStartSuccess
elif [[ "$1" == "finish" ]]; then
  checkCurrentRelease
  requestFinishApproval
  commitPushRelease
  cleanRelease
  printFinishSuccess
else
  printHelp
fi
