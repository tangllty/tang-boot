#!/bin/sh

source $(dirname "$0")/update-version.sh

# Push the changes to the remote repository
git add pom.xml
for module in $(ls | grep '^tang-')
do
	git add "${module}/pom.xml"
	echo "${module}/pom.xml"
done

git add tang-admin/src/main/resources/application.yml

git commit -m "v${NEW_VERSION}"
git tag "v${NEW_VERSION}"

git push
git push --tags