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
			
			//**
			$.get("/geoloc/dropoffs/"+ pos.coords.latitude +"/" + pos.coords.longitude + "/1000",
				{},
				function(data, err) {
					var dp;
					var mindist = Infinity;
					if (false){//err) {
						console.log("oops, data not good");
					} else {
						console.log("Good?");
						var dPoints = eval('(' + data + ')');
						for (var i = dPoints.length - 1; i >= 0; i--) {
							var dist = findDistance(pos,
								{ coords: {
									latitude: dPoints[i].latitude,
									longitude: dPoints[i].longitude
								}});
							if (dist < mindist){
								mindist = dist;
								dp = dPoints[i];
							}
						};
					}
					if (dp) {
						document.write("<p>Point de collecte à proximité:<br />" +
							"Distance: " + Math.floor(mindist*100) + " mètres...<br />"+
							"Latitude: " + dp.latitude + "°<br />"+
							"Longitude: " + dp.longitude + "°<br />");


						document.write("<a href=http://www.openstreetmap.org/#map=17/"+ dp.latitude +"/" + dp.longitude + ">Cliquer pour aller à la carte</a>")
					} else {
						document.write("<p>Aucun point de collecte trouvé</p>");
					}
				}
			);
			/**/
		}
	});
}