echo "Current Users"
finger
echo "Current shell:" $SHELL

echo "Home directory:" $HOME
echo "Operating system type:"
uname -mrs
echo "Current path setting:"
echo $PATH
echo "Current working directory:"
pwd
echo "Currently logged number of users:"
users | wc -l
echo "Os info:"
uname -a
echo "All available shells:"
compgen -c
echo "Mouse settings:" $HOME
echo "CPU infomation:" $HOME
echo "Memory information:" $HOME
echo "hard disk information:" $HOME
echo "File system:" $HOME