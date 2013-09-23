function getLocation(f) {
	f({coords: {latitude: 43.6465125, longitude: 3.7964135}});
	/**
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(f);
	} else {
		f({coords: {latitude: 0, longitude: 0}}, "Geolocation not available...");
	}
	//*/
}

