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

body = document.getElementsByTagName("body");

senses = obj.labels[0].senses;


body[0].appendChild(createTag("p", "Text :"+obj.labels[0].text, {"style":"font-weight: bold;"}));
body[0].appendChild(createTag("p", "LinkDocCount :"+obj.labels[0].linkDocCount, {"style":"font-weight: bold;"}));
body[0].appendChild(createTag("p", "LinkOccCount :"+obj.labels[0].linkOccCount, {"style":"font-weight: bold;"}));
//create table
table = createTable();
body = document.getElementsByTagName("body");
body[0].appendChild(table);

//create t head
tr = createTr();
table.appendChild(tr);
var attrNames = ["id", "title", "linkDocCount", "linkOccCount"];
for (var i = 0; i <= attrNames.length -1; i++) {
	tr.appendChild(createTd(attrNames[i], true));
};


//create tr
for(var j in senses){
	var tr = createTr();
	for (var i = 0; i <= attrNames.length -1; i++) {
		var text = senses[j][attrNames[i]];
		tr.appendChild(createTd(text, false, 0));
	};
	
	table.appendChild(tr);
}
