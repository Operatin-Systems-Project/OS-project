serverIp="192.168.18.2"
maxAttempts=3
logInvalidAttempt() {
	local timestamp=$(date +"%F %H-%M-%S")
	echo "Invalid login attempt by $1 at $timestamp" >> invalid_attempts.log

}

tryLogin() {
	ssh -o NumberOfPasswordPrompts=1 $1@$serverIp 2>>/dev/null
	if [[ $? -eq 0 ]]; then
		return 0
	else
		logInvalidAttempt $1	
		return -1
	fi
}

transferLog() {
	local timestamp=$(date +"%F %H-%M-%S")
	sftp -i ~/.ssh/logger_key logger@$serverIp << EOF 1>>/dev/null
put invalid_attempts.log $1_$timestamp_invalid_attempts.log
bye
EOF
}

main(){
	attempts=0
	rm invalid_attempts.log 2>>/dev/null
	read -p "Username: " username
	while [[ $attempts -lt $maxAttempts ]]; do
		tryLogin $username
		if [[ $? -ne 0 ]]; then
			echo "Wrong password"
			((attempts++))
		else
			echo "Access Granted"
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