myFolder="mytar"
if [ ! -d $myFolder ]; then
        mkdir $myFolder
fi
tar -xvf *.tar -C $myFolder"/"
baseDir="partFiles/"


folderList=`ls $baseDir`
i=1
for folder in $folderList
do
	xmlFile=`ls $baseDir"/"$folder`
	
	echo "rename to $baseDir/"$folder"/$i.xml"
	mv $myFolder"/"$baseDir"/"$folder"/$xmlFile" $myFolder"/"$baseDir"/"$folder"/"$i".xml"
 	i=$(($i+1))
done
tar -cvf $myFolder.tar $myFolder






