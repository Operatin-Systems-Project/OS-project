#!/bin/bash

serverIp="192.168.18.8"
maxAttempts=3
sshKeyPath="~/.ssh/id_rsa"  # Path to your private SSH key (adjust if necessary)
username="abdallah"     # Define your username here

logInvalidAttempt() {
    local timestamp=$(date +"%F_%H:%M:%S")
    echo "Invalid login attempt by $1 at $timestamp" >> invalid_attempts.log
}

tryLogin() {
    ssh -o NumberOfPasswordPrompts=1 -o PubkeyAuthentication=yes -i $sshKeyPath $username@$serverIp 2>>/dev/null
    if [[ $? -eq 0 ]]; then
        return 0
    else
        logInvalidAttempt $username    
        return -1
    fi
}

transferLog() {
    local timestamp=$(date +"%F_%H:%M:%S")
    sftp -i $sshKeyPath $username@$serverIp << EOF &>/dev/null
put invalid_attempts.log logs/"$username_${timestamp}_invalid_attempts.log"
bye
EOF
}

main(){
    attempts=0
    rm invalid_attempts.log 2>>/dev/null
    while [[ $attempts -lt $maxAttempts ]]; do
        tryLogin
        if [[ $? -ne 0 ]]; then
            echo "Wrong password"
            ((attempts++))
        else
            echo "Access was granted"
            exit 0
        fi
    done
    echo "Unauthorized access!!"
    transferLog
    rm invalid_attempts.log 
    echo "You will be disconnected in 30 seconds...."
    sleep 30
    gnome-session-quit --logout --no-prompt
}

main

