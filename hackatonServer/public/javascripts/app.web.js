function appMain(){
	var canvas = document.getElementById("c")
	var video = document.querySelector('video');



	console.log("Loaded!")
	canvas.hidden = true;
	streamFromCamera();
	//setTimeout(function() { grabScreenShot() }, 5000);
}

function drop(){
	var img = grabScreenShot();
	getLocation(function(pos,err)
	{
		document.write(
		"<img src=" + img + "></img>" + 
		"<p>Latitude: " + pos.coords.latitude +
		"<br />Longitude: " + pos.coords.longitude + "</p>");
	});

}