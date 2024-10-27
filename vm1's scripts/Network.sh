log_file="network.log" > "$log_file"
serverIP=$(hostname -I | awk '{print $1}') #save the ip address of the server (the one who ran the script)
if [ "$#" -ne 2 ]; then # if statment to make sure there are 2 arguments
	echo Invalid arguemnets
	exit 1
fi
test_ping(){
	local client_IP=$1 
	local count=0
	while [ $count -lt 3 ]; do
		if ping -c 1 -W 3 "$target_IP" > /dev/null; then
			currentDate=$(date +"%Y-%m-%d %H:%M:%S")
			ping -c 1 -W 3 "$target_IP"
		

			echo "$currentDate: ping with $client_IP is working successfully" | tee -a "$log_file"
			
			return 0

		else 
			echo "ping with $client_IP is not working" | tee -a "$log_file"
			./traceroute.sh "$client_IP" | tee -a "$log_file"    #here call the traceroute ( still didnt do the traceroute )
		fi	
		count=$((count+1)) 
	done

}
ping_client_to_server(){
	local client_IP=$1 
	local count=0
	while [ $count -lt 3 ]; do
		if ssh login@"$target_IP" ping -c 1 -W 3 "$serverIP"  > /dev/null; then
			currentDate=$(date +"%Y-%m-%d %H:%M:%S")
			ssh login@"$target_IP" ping -c 1 -W 3 "$serverIP"  # login is a local user in vm2 and vm3 it has a public key (no need for password to use the ssh) 
			echo "$currentDate: pinging $client_IP with $serverIP is working successfully" | tee -a "$log_file"
			return 0
		else 
			echo "pinging $client_IP with $serverIP is not working" | tee -a "$log_file"
			./traceroute.sh "$serverIP" | tee -a "$log_file"    #here call the traceroute ( still didnt do the traceroute )
		fi	
		count=$((count+1)) 
	done
}
for target_IP in "$@"; do
	test_ping "$target_IP" # loop to go through the arguments 
done
for target_IP in "$@"; do
	ping_client_to_server "$target_IP" # loop to go through the arguments 
done
