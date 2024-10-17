sudo groupadd vboxsf
echo enter username
read username
mkdir Desktop/$username
sudo usermod -aG vboxsf  $username
sudo mount -t vboxsf $username Desktop/$username/
