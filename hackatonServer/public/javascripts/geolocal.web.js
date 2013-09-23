function getLocation(f) {
	//f({coords: {latitude: 43.6465125, longitude: 3.7964135}});
	/**/
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(f);
	} else {
		f({coords: {latitude: 0, longitude: 0}}, "Geolocation not available...");
	}
	//*/
}

function findDistance(c1, c2) {
	var R = 6371; // km
	var dLat = (c2.coords.latitude-c1.coords.latitude)/360*Math.PI;
	var dLon = (c2.coords.longitude-c1.coords.longitude)/360*Math.PI;
	var lat1 = c1.coords.latitude/360*Math.PI;
	var lat2 = c2.coords.longitude/360*Math.PI;

	var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
        Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
	var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
	var d = R * c;
	return d;
}