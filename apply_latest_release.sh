#!/bin/bash

# Exit script at first error:
set -e

GREEN="\033[1;32m"
GREEN_HIGHLIGHT="\033[1;42m"
RED="\033[1;31m"
PINK="\033[1;95m"
DEFAULT="\033[0m"

REPO_API_URL="https://api.github.com/repos/iadvize/mobile-android-sdk-conversation-library"

# Read GitHub Personal Access Token in local.properties file
GITHUB_TOKEN=$(grep 'GITHUB_TOKEN=' local.properties | sed 's/GITHUB_TOKEN=\(.*\)/\1/')
if [ -z "${GITHUB_TOKEN}" ]; then
    echo -e "${RED}Set your GITHUB_TOKEN in local.properties file.${DEFAULT}"
    exit 0
fi

# Fetch latest release JSON
echo -e "${GREEN}Fetching latest SDK release${DEFAULT}"
latest_json=$(curl -L -H "Accept: application/vnd.github+json" -H "Authorization: Bearer ${GITHUB_TOKEN}" "${REPO_API_URL}/releases/latest")
sdk_version=$(echo "${latest_json}" | grep "\"tag_name\":" | sed "s/\(.*\)\"tag_name\": \"\(.*\)\"\(.*\)/\2/")

if [ -z "${sdk_version}" ]; then
    echo -e "${RED}Error fetching latest release. Please double check your credentials.${DEFAULT}"
    exit 0
fi
echo -e "${GREEN}Latest release is${DEFAULT} ${GREEN_HIGHLIGHT}${sdk_version}${DEFAULT}"

# Ask for validation before applying the release
echo -e "${RED}You are about to apply release${DEFAULT} ${GREEN_HIGHLIGHT}${sdk_version}${DEFAULT} ${RED}into this project.${DEFAULT}"
echo -e "${RED}Proceed ? [y/n]${DEFAULT}"
read -n 1 key
case $key in
y) ;;
*)
    echo ""
    echo -e "${GREEN}kthxbye${DEFAULT}"
    exit 0
esac
echo ""

# Read asset list
asset_ids=($(echo "${latest_json}" | grep "\"url\": \"${REPO_API_URL}/releases/assets/" | sed "s#\(.*\)/releases/assets/\(.*\)\"\(.*\)#\2#"))
echo -e "${GREEN}Found ${#asset_ids[@]} assets${DEFAULT}"

# Download assets
echo -e "${GREEN}Downloading assets${DEFAULT}"
for asset_id in "${asset_ids[@]}";
do
    echo -e "${GREEN}=> Fetching ${REPO_API_URL}/releases/assets/${asset_id}${DEFAULT}"
    curl -LJO -H 'Accept: application/octet-stream' -H "Authorization: Bearer ${GITHUB_TOKEN}" "${REPO_API_URL}/releases/assets/${asset_id}"
done

# Verify presence of downloaded files
echo -e "${GREEN}Checking downloaded files${DEFAULT}"
downloaded_files=("IAdvizeSDK.zip" "verification-metadata.xml" "IAdvizeSDK.checksums")
for downloaded_file in "${downloaded_files[@]}";
do
    if ! [ -f "${downloaded_file}" ]; then
        echo -e "${RED}ERROR: can't find ${downloaded_file}${DEFAULT}"
        exit 1
    else
       echo -e "=> Found ${downloaded_file}"
    fi
done

# Apply release
echo -e "${GREEN}Applying release ${sdk_version}${DEFAULT}"

sed -i '' "s/iadvize-sdk = \"\(.*\)\"/iadvize-sdk = \"${sdk_version}\"/" example/gradle/libs.versions.toml
versionName=$(grep 'iadvize-sdk = "' example/gradle/libs.versions.toml | sed 's/iadvize-sdk = "\(.*\)"/\1/')

rm -Rf tmp
unzip -q IAdvizeSDK.zip -d tmp
mkdir com/iadvize/iadvize-sdk/${sdk_version}
cp tmp/iadvize-sdk-${sdk_version}.aar com/iadvize/iadvize-sdk/${sdk_version}
cp tmp/iadvize-sdk-${sdk_version}.pom com/iadvize/iadvize-sdk/${sdk_version}
sed -i '' "s/^  <version>\(.*\)<\/version>/  <version>${sdk_version}<\/version>/" com/iadvize/iadvize-sdk/maven-metadata.xml
sed -i '' "s/^    <latest>\(.*\)<\/latest>/    <latest>${sdk_version}<\/latest>/" com/iadvize/iadvize-sdk/maven-metadata.xml
sed -i '' "s/^    <release>\(.*\)<\/release>/    <release>${sdk_version}<\/release>/" com/iadvize/iadvize-sdk/maven-metadata.xml
sed -i '' "s/^    <\/versions>/      <version>${sdk_version}<\/version>|    <\/versions>/" com/iadvize/iadvize-sdk/maven-metadata.xml
tr '|' '\n' < com/iadvize/iadvize-sdk/maven-metadata.xml > tmp.txt
mv tmp.txt com/iadvize/iadvize-sdk/maven-metadata.xml
now=`date +"%Y%m%d%H%M0000"`
sed -i '' "s/^    <lastUpdated>\(.*\)<\/lastUpdated>/    <lastUpdated>${now}<\/lastUpdated>/" com/iadvize/iadvize-sdk/maven-metadata.xml
mv tmp/*.md .
rm -rf docs
mv tmp/docs docs
rm -R tmp

# Remove downloaded artifacts
for downloaded_file in "${downloaded_files[@]}";
do
    rm "${downloaded_file}"
done

echo -e "${GREEN}Release${DEFAULT} ${GREEN_HIGHLIGHT}${sdk_version}${DEFAULT} ${GREEN}is now applied${DEFAULT}"
echo -e "${PINK}Check the functionality by integrating the SDK locally, update the integration project if needed${DEFAULT}"
echo -e "${PINK}Commit, tag with${DEFAULT} ${GREEN_HIGHLIGHT}${sdk_version}${DEFAULT} ${PINK}then push to trigger the public release${DEFAULT}"

exit 0
