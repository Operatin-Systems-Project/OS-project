echo enter username
read username
sudo adduser $username
sudo apt update
sudo apt install openssh-server
sudo systemctl enable ssh 
sudo systemctl start ssh