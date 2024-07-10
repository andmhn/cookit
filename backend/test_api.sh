#!/bin/bash

if ! command -v http > /dev/null
then
  echo httpie is not installed
  exit 1
fi

set -v

http POST 127.0.0.0:8080/auth/signup  'fullname=Anand Mohan' email=anand@email.com password=1234
http POST 127.0.0.0:8080/auth/authenticate password=1234 email=anand@email.com

http GET 127.0.0.0:8080/api/user -a anand@email.com:1234


http POST 127.0.0.0:8080/api/recipe name=alooParatha prep_time=25 difficulty=6 -a anand@email.com:1234
http GET 127.0.0.0:8080/api/recipe/all -a anand@email.com:1234
http GET 127.0.0.0:8080/api/recipe/total -a anand@email.com:1234

# TODO test deleting of recipe by recipeId

http DELETE 127.0.0.0:8080/api/user -a anand@email.com:1234

