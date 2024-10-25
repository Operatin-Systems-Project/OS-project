echo enter username
read username
mkdir Desktop/$username
sudo mount -t vboxsf $username Desktop/$username/
