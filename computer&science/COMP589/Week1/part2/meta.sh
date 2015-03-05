xmlFile="meta_identifiers.xml"
baseDir="partFiles/"
#check baseDir
if [ ! -d $baseDir ]; then
	mkdir $baseDir
fi
echo "remove $xmlFile"
rm -rf $xmlFile
wget -nv -c -O $xmlFile "http://nzdl.org/cgi-bin/oaiserver?verb=ListIdentifiers&set=demo&metadataPrefix=oai_dc"

#cat $xmlFile|tr -d " "
#cat $xmlFile|sed 's/<[^>]*>//g'
identifiers=`sed -n '/identifier/{s/.*<identifier>//;s/<\/identifier.*//;p;}' meta_identifiers.xml`
i=1
for identifier in $identifiers
do
	dirName=$baseDir"/xmlDir$i/"
	identifierOrigin=$identifier
	identifier=`echo "$identifier"|cut -d ":" -f2`
	if [ ! -d "$dirName" ]; then
		mkdir $dirName
	fi
	echo  "save to" $dirName $identifier
	url="http://nzdl.org/cgi-bin/oaiserver?verb=GetRecord&identifier=$identifierOrigin&metadataPrefix=oai_dc"
	echo $url
	wget -c -nv -O "${dirName}${identifier}.xml"  "$url"
	i=$(($i+1))
#	echo $identifier"!!!!!!!"
done

tar -cvf part2.tar $baseDir
