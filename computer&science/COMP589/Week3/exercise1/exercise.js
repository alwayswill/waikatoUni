//Step 1
function dotLines(){
	console.log("----------------------------------------------");
};

function step1(){
	console.log("Step1");
	var flaxWrapper = document.getElementById("flax-wrapper");
	var flaxHeader = document.getElementById("flaxHeader");
	var flaxBody = document.getElementById("flax-body");
	console.log("1.display elements");
	var attr = flaxHeader.attributes;
	for(var i in attr){
		console.log(attr[i].name+":"+attr[i].value);
	}
	console.log("Children's number:"+flaxHeader.childElementCount);
	console.log("first child's tag name: "+flaxHeader.firstElementChild.tagName);
	console.log("parent's id: "+flaxHeader.parentNode.id);
	console.log("2.set seq:2");
	flaxHeader.setAttribute("seq", 2);
	console.log("3.capitalize the first letters");
	var spans = flaxHeader.firstElementChild.getElementsByTagName("span");
	for(var i in spans){
		if(typeof(spans[i].innerHTML) != "undefined"){
			var text = spans[i].textContent.substr(0,1).toUpperCase()+spans[i].textContent.substr(1).toLowerCase();
			spans[i].textContent = text;
		}
	}

	var secLink = flaxBody.getElementsByTagName("a")[1];	
	console.log("4.second link href value:"+secLink.getAttribute("href"));
	var imgs = flaxWrapper.getElementsByTagName("img");	
	var imgsNo = 0;
	for(var i in imgs){
		
		if(imgs[i].nodeType == 1 && imgs[i].getAttribute("style") == null){
			imgsNo +=1;
		}
	}
	console.log("5 the number of img that fail to own style attr:"+imgsNo);
	console.log("6 append a new act");

	var uls = document.getElementsByTagName("ul");
	var actList = [];
	for(var i in uls){
		if(uls[i].nodeType ==1 && uls[i].getAttribute("class") == "activity-list"){
			actList.push(uls[i]);
		}
	}

	var node = document.createElement("li");
	var aNode = document.createElement("a");
	var att = document.createAttribute("href");
	att.value = "matching-text";
	var aText = document.createTextNode("Matching Text");
	aNode.appendChild(aText);
	aNode.setAttributeNode(att);
		
	node.appendChild(aNode);
	actList[0].appendChild(node);
	console.log("7 isnert a new act");

	var node = document.createElement("li");
	var aNode = document.createElement("a");
	var att = document.createAttribute("href");
	att.value = "split_sentences";
	var aText = document.createTextNode("Split Sentences");
	aNode.appendChild(aText);
	aNode.setAttributeNode(att);
	node.appendChild(aNode);
	actNo = actList.length - 1;
	actList[actNo].firstElementChild.appendChild(node);
	console.log("8 remove all children of the last song-li");
	
	var songList = [];
	var lis = document.getElementsByTagName("li");
	for(var i in lis){
		if(lis[i].nodeType ==1 && lis[i].getAttribute("class") == "song-li"){
			songList.push(lis[i]);
		}
	}
	var lastSong = songList[songList.length-1];
	while (lastSong.hasChildNodes()) {
		lastSong.removeChild(lastSong.lastChild);
	}
	lastSong.innerHTML = "removed";


	
	
}
dotLines();
step1();
dotLines();
