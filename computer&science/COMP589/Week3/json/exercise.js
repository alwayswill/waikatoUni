
// lib
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
	if (text != undefined) {
		tagNode.innerHTML = text;
	};
	if (attr != undefined) {
		for(var k in attr){
			var att = document.createAttribute(k);
			att.value = attr[k];
			tagNode.setAttributeNode(att);
		}
	};
	return tagNode;
}
//init body and data
body = document.getElementsByTagName("body");
senses = obj.labels[0].senses;

// head text
body[0].appendChild(createTag("p", "Text :"+obj.labels[0].text, {"style":"font-weight: bold;"}));
body[0].appendChild(createTag("p", "LinkDocCount :"+obj.labels[0].linkDocCount, {"style":"font-weight: bold;"}));
body[0].appendChild(createTag("p", "LinkOccCount :"+obj.labels[0].linkOccCount, {"style":"font-weight: bold;"}));

//create table
table = createTag("table", undefined, {"border":"solid"});
tbody = createTag("tbody");
thead = createTag("thead");
tfoot = createTag("tfoot");


table.appendChild(thead);
table.appendChild(tbody);
table.appendChild(tfoot);

//create thead
tr = createTag("tr");
thead.appendChild(tr);
var attrNames = ["id", "title", "linkDocCount", "linkOccCount"];
for (var i = 0; i <= attrNames.length -1; i++) {
	tr.appendChild(createTag("th",attrNames[i]));
};

//create tbody
for(var j in senses){
	var tr = createTag("tr");
	for (var i = 0; i <= attrNames.length -1; i++) {
		var text = senses[j][attrNames[i]];
		tr.appendChild(createTag("td", text));
	};
	tbody.appendChild(tr);
}

//create tfoot
var tr = createTag("tr");
tr.appendChild(createTag("td", "Created by Shuzu Li", {"colspan":"4", "style":"text-align:right;background:#ccc"}));
tfoot.appendChild(tr);

//append to body
body = document.getElementsByTagName("body");
body[0].appendChild(table);