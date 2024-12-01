#!/bin/bash
serverIp="192.168.18.8"
maxAttempts=3
logInvalidAttempt() {
    local timestamp=$(date +"%F%H:%M:%S")
    echo "Invalid login attempt by $1 at $timestamp" >> invalid_attempts.log

}

tryLogin() {
    ssh -o StrictHostKeyChecking=no -o NumberOfPasswordPrompts=1 -o PubkeyAuthentication=no $1@$serverIp 2>>/dev/null
    if [[ $? -eq 0 ]]; then
        return 0
    else
        logInvalidAttempt $1
 login       return -1
    fi
}

transferLog() {
    local timestamp=$(date +"%F%H:%M:%S")
    sftp -i ~/.ssh/$1key $1@$serverIp << EOF &>/dev/null
put invalid_attempts.log logs/"$1${timestamp}_invalid_attempts.log"
bye
EOF
}

main(){
    attempts=0
    rm invalid_attempts.log 2>>/dev/null
    while [[ $attempts -lt $maxAttempts ]]; do
    read -p "Username: " username
        tryLogin $username
        if [[ $? -ne 0 ]]; then
            echo "Wrong password/username"
            ((attempts++))
        else
            echo "Access was granted"
            exit 0
        fi
    done
    echo "Unauthorized access!!"
    transferLog $username
    rm invalid_attempts.log 
    echo "You will be disconnected in 30 seconds...."
    sleep 30
    gnome-session-quit --logout --no-prompt
}

main
