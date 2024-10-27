log_file="network.log" > "$log_file"
currentIP=$(hostname -I | awk '{print $1}') #save the ip address of the server (the one who ran the script)
if [ "$#" -ne 2 ]; then # if statment to make sure there are 2 arguments
	echo Invalid arguemnets
	exit 1
fi
test_ping(){
	local target_IP=$1 
	local count=0
	while [ $count -lt 3 ]; do
		if ping -c 3 -W 3 "$target_IP" > /dev/null; then
			currentDate=$(date +"%Y-%m-%d %H:%M:%S")
			ping -c 3 -W 3 "$target_IP"
		

			echo "$currentDate: Connectivity with $target_IP is ok " | tee -a "$log_file"
			
			return 0

		else 
			echo "Connectivity with $target_IP is not working" | tee -a "$log_file"
			echo "Running traceroute.sh"
			./traceroute.sh "$target_IP" | tee -a "$log_file"    #here call the traceroute ( still didnt do the traceroute )
		fi	
		count=$((count+1)) 
	done

}
	for target_IP in "$@"; do
	test_ping "$target_IP" # ping server to clients
	done

