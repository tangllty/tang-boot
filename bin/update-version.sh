#!/bin/sh

# Into the root directory of the project
cd "$(dirname "$0")/.."

NEW_VERSION=$1

PATTERN="^[0-9]+\.[0-9]+\.[0-9]+$"

# Check the number is valid
if [[ ! $NEW_VERSION =~ $PATTERN ]]; then
	echo "Invalid version number: $NEW_VERSION"
	exit 1
fi

# Get the old version number
OLD_VERSION=$(sed -n 's/.*<tang.version>\(.*\)<\/tang.version>.*/\1/p' pom.xml)

# Modify the version of the parent module and the tang.version of the properties
sed -i "0,/<version>[^<]*<\/version>/ s/<version>[^<]*<\/version>/<version>${NEW_VERSION}<\/version>/; s/<tang.version>[^<]*<\/tang.version>/<tang.version>${NEW_VERSION}<\/tang.version>/g" pom.xml

# Modify the version of the sub-module
for module in $(ls | grep '^tang-')
do
	sed -i "0,/<version>[^<]*<\/version>/ s/<version>[^<]*<\/version>/<version>${NEW_VERSION}<\/version>/" "${module}/pom.xml"
	echo "Version number changed from ${OLD_VERSION} to ${NEW_VERSION} in ${module}/pom.xml"
done

# Define the application yml file path
YML_PATH="tang-admin/src/main/resources/application.yml"

sed -i "0,/version:/ s/\(version:\s*\)[^\n]*/\1$NEW_VERSION/" $YML_PATH
echo "Version number changed from ${OLD_VERSION} to ${NEW_VERSION} in ${YML_PATH}"

# Define the docker-compose.yml file path
DOCKER_COMPOSE_PATH="docker-compose.yml"

sed -i "0,/version:/ s/\(version:\s*\)[^\n]*/\1'$NEW_VERSION'/" $DOCKER_COMPOSE_PATH
echo "Version number changed from ${OLD_VERSION} to ${NEW_VERSION} in ${DOCKER_COMPOSE_PATH}"
