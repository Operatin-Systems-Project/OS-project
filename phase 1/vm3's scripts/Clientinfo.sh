#!/bin/bash
server_name="abdallah"
server_ip="192.168.18.8"
mkdir -p "$HOME/Desktop/logs"
ssh -i ~/.ssh/vm1_key $server_name@$server_ip "mkdir -p ~/Desktop/logs_Clientinfo" 2>/dev/null
while true; do
rm tmp 2>>/dev/null
logfile="$HOME/Desktop/logs/process_info_$(date +"%F_%H-%M-%S").log"
echo "Process tree:" >> $logfile
pstree >> $logfile 
echo "Zombie processes:" >> $logfile
ps -eo pid,stat,comm | grep Z >> $logfile
echo "Processes CPU usage:" >> $logfile
ps -eo pid,comm,%cpu --sort=-%cpu >> tmp
cat tmp >> $logfile
echo $cpu_proceses >> $logfile
echo "Processes Memory usage:" >> $logfile
ps -eo pid,comm,%mem --sort=-%mem >> $logfile
echo "Top 5 resource consumers (based on %CPU):" >> $logfile
head -n 6 tmp >> $logfile
rm tmp 2>>/dev/null
scp -i ~/.ssh/vm1_key "$logfile" $server_name@$server_ip:~/Desktop/logs_Clientinfo 2>&1

echo "logfile sent to $server_name"

sleep 3600
done
