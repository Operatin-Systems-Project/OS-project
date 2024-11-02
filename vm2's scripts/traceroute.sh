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
    
if   !(traceroute -m 30 $1 > /dev/null);then 
  logandprint "$(sudo reboot)"
fi