#!/bin/sh

source $(dirname "$0")/update-version.sh

# Push the changes to the remote repository
find . -name 'pom.xml' | while read -r pomfile
do
  git add "${pomfile}"
  echo "${pomfile}"
done

git add tang-admin/src/main/resources/application.yml
git add docker-compose.yml

git commit -m "v${NEW_VERSION}"
git tag -a "v${NEW_VERSION}" -m "v${NEW_VERSION}"

git push
git push --tags