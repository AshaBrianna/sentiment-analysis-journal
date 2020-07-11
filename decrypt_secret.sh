#!/bin/sh

# Decrypt the file
mkdir $HOME/secrets
echo ${CREDENTIAL_PASSPHRASE:0:3}
# --batch to prevent interactive command
# --yes to assume "yes" for questions
gpg --quiet --batch --yes --decrypt --passphrase="$CREDENTIAL_PASSPHRASE" \
--output $HOME/secrets/spsTeam26.json spsTeam26.json.gpg
