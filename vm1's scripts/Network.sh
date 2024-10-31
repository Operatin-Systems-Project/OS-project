log_file="network.log" > "$log_file"
currentIP=$(hostname -I | awk '{print $1}') #save the ip address of the server (the one who ran the script)
if [ "$#" -ne 2 ]; then # if statment to make sure there are 2 arguments
	echo Invalid arguemnets
	exit 1
fi
declare -a TargetIPsArray=($1 $2)
test_ping(){
	for target_IP in "${TargetIPsArray[@]}"
	do
		if ping -c 3 -W 3 $target_IP > /dev/null
		then
			currentDate=$(date +"%Y-%m-%d %H:%M:%S")
			ping -c 3 -W 3 $target_IP >> $log_file
		

			echo "$currentDate: Connectivity with $target_IP is ok " >> $log_file
			

		else 
			echo "Connectivity with $target_IP is not working" >> $log_file
			echo "Running traceroute.sh" >> $log_file
			./traceroute.sh $target_IP >> $log_file  
		fi	
	done

}
checkinstall(){
	echo "Checking if net tools are installed....."
	if ! command -v ifconfig &> /dev/null; then
		echo "Installing tools.........."
		echo "Enter the password to install the package"
		sudo apt upgrade
		sudo apt install net-tools
	else
		echo "Tools are installed"
	fi
}
checkinstall
test_ping
cat $log_file
