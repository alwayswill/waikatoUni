function splitLine {
        printf "%0.1s" "-"{1..100}
        echo
}

echo "Current Users"
finger

splitLine

echo "Current shell:" $SHELL
splitLine

echo "Home directory:" $HOME
splitLine
echo "Operating system type:"
uname -mrs
splitLine
echo "Current path setting:"
echo $PATH
splitLine

echo "Current working directory:"
pwd
splitLine

echo "Currently logged number of users:"
users | wc -l
splitLine

echo "Os info:"
uname -a
splitLine

echo "All available shells:"
compgen -c
splitLine

echo "Mouse settings:"
xinput list-props "Logitech USB Optical Mouse"
splitLine

echo "CPU infomation:"
cat /proc/cpuinfo
splitLine

echo "Memory information:"
cat /proc/meminfo
splitLine

echo "hard disk information(need root):"
hdparm -I /dev/sda

splitLine

echo "File system:"
cat /proc/mounts
splitLine
