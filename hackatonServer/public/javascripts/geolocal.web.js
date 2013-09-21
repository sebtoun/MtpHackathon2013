function getLocation(f) {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(f);
	} else {
		f({coords: {latitude: 0, longitude: 0}}, "Geolocation not available...");
	}
}

