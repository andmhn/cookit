#!/bin/bash

if ! command -v http > /dev/null
then
  echo httpie is not installed
  exit 1
fi

http POST 127.0.0.0:8080/auth/signup  'fullname=Anand Mohan' email=anand@email.com password=1234
