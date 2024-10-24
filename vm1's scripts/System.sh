diskLog="disk_info.log"
memCpuLog="mem_cpu_int.log"
> "$disk_log"
> "$mem_cpu_log"
du -h $HOME | tee -a "$diskLog"
df -h $HOME | tee -a "$diskLog"
memorytotal=$(free | grep Mem | awk '{print $2}')
memoryused=$(free | grep Mem | awk '{print $3}')
memoryfree=$(free | grep Mem | awk '{print $4}')
echo "Memory Usage:" | tee -a "$memCpuLog"
echo "The precentage of the total memory used is: $((memoryused*100/memorytotal))%" | tee -a "$memCpuLog"
echo "The precentage of the total free memory space is: $((memoryfree*100/memorytotal))%" | tee -a "$memCpuLog"
 #the file that have the cpu model name is /proc/cpouinfo and it appears twice ---> grep and only take it once
 cpumodelnameCommand=$(grep -m 1 'model name' /proc/cpuinfo)

