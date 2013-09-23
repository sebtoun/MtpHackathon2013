function appMain(){
	getDropoffs();
	console.log("Loaded!")
}

function drop(){
	getLocation(function(pos,err)
	{
		document.write(
		"<p>Latitude: " + pos.coords.latitude +
		"<br />Longitude: " + pos.coords.longitude + "</p>");
	});
}

function getDropoffs(){
	getLocation(function(pos,err){
		if (err) {
			console.log("oops, pos not good")
		} else {
			console.log("Getting: " + "/geoloc/dropoffs/"+ pos.coords.latitude +"/" + pos.coords.longitude + "/10")
			$.get("/geoloc/dropoffs/"+ pos.coords.latitude +"/" + pos.coords.longitude + "/20",
				{},
				function(data, err) {
					if (false){//err) {
						console.log("oops, data not good")
					} else {
						console.log("Good?")
						document.write(data);
					}
				}
			);
		}
	});
}