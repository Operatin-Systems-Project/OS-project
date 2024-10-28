check_ping() {
    ping -c 1 google.com &> /dev/null

    if [ $? -eq 0 ]; then
        return 0
    else
        return 1
    fi
}
if ! check_ping; then
    echo "routing table"route -n
    echo "hostname"hostname
    echo "testing DNS server (8.8.8.8)"
nslookup google.com 8.8.8.8
    echo "Tracing route to google.com"traceroute google.com
    sudo reboot