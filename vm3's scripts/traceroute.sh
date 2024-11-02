  Log="network.log"
   logandprint() {
    echo "$1" | tee -a $Log
}

    logandprint "routing table"
     logandprint "$(route -n)"
    logandprint "hostname"
    logandprint "$(hostname)"
    logandprint "testing DNS server"
    LOCAL_DNS=$(grep 'nameserver' /etc/resolv.conf | awk '{print $2}' | head -n 1)
     logandprint "$(nslookup google.com $LOCAL_DNS)"
    logandprint "Tracing route to google.com"
     logandprint "$(traceroute google.com)"
    logandprint "$(ping -c 1 google.com &> /dev/null)"
    
traceroute_output=$(traceroute -m 30 "$TARGET_IP" 2>&1)
logandprint "$traceroute_output"
last_line=$(echo "$traceroute_output" | tail -n 1)
if echo "$last_line" | grep -q "$TARGET_IP"; then
    logandprint "Successfully traced route to $TARGET_IP"
else
    logandprint "Unable to reach $TARGET_IP via traceroute. Rebooting..."
    sudo reboot
fi