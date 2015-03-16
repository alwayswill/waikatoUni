
if (window.DOMParser) {
	parser = new DOMParser();
	xmlDoc = parser.parseFromString(xmlString, "text/html");
}else{
	xmlDoc=new ActiveXObject("Mircrosoft.XMLDOM");
	xmlDoc.loadXML(xmlString);
};


function createTr(tds){
	var tr = document.createElement("tr");
	return tr;
}

function createTd(text, isTd, colspan){
	var tag = "td";
	if (isTd) {
		tag = "th"
	};
	var tdNode = document.createElement(tag);
	if (colspan >0) {
		var att = document.createAttribute("colspan");
		att.value = colspan;
		tdNode.setAttributeNode(att);
	};
	tdNode.innerHTML = text;
	// var textNode = document.createTextNode(text);
	// tdNode.appendChild(textNode);
	return tdNode;
}


function createTable(){
	var table = document.createElement("table");
	var att = document.createAttribute("border");
	att.value = "solid";
	table.setAttributeNode(att);
	return table;

}

function parseData(sense){
	var nameAttr = ["id", "title", "linkDocCount", "linkOccCount"];
	var data = {};
	for(var i in nameAttr){
		data[nameAttr[i]] = sense.getAttribute(nameAttr[i]);
	}
	data["definition"] = sense.getElementsByTagName(["definition"])[0].innerHTML;
	return data;
}

function createTag(tag, text, attr){
	var tagNode = document.createElement(tag);
	tagNode.innerHTML = text;
	if (attr != "undefined") {
		for(var k in attr){
			var att = document.createAttribute(k);
			att.value = attr[k];
			tagNode.setAttributeNode(att);
		}
	};
	return tagNode;
}



senses = xmlDoc.getElementsByTagName("sense");


table = createTable();
body = document.getElementsByTagName("body");
body[0].appendChild(table);

tr = createTr();
table.appendChild(tr);
var attrNames = ["id", "title", "linkDocCount", "linkOccCount"];
for (var i = 0; i <= attrNames.length -1; i++) {
	tr.appendChild(createTd(attrNames[i], true));
};
//parse Data
sensesData = new Array();
for (var i = 0; i < senses.length; i++) {
	sensesData.push(parseData(senses[i]));
};

//assign tr
for (var j = 0;  j < sensesData.length;  j++) {
	var tr = createTr();
	for (var i = 0; i <= attrNames.length -1; i++) {
		tr.appendChild(createTd(sensesData[j][attrNames[i]]));
	};
	
	table.appendChild(tr);

	var tr = createTr();
	var styleDefinition = createTag("i", sensesData[j]["definition"], {"style":"background: #ccc;"}).outerHTML;
	tr.appendChild(createTd(styleDefinition, false, 4));
	table.appendChild(tr);
};
