var onFailSoHard = function(e) {
	console.log('Reeeejected!', e);
};

navigator.getUserMedia = (navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia);

	// Not showing vendor prefixes.
function streamFromCamera(){
	navigator.getUserMedia({video: true, audio: true}, function(localMediaStream) {
		var video = document.querySelector('video');
		video.src = window.URL.createObjectURL(localMediaStream);

		// Note: onloadedmetadata doesn't fire in Chrome when using it with getUserMedia.
		// See crbug.com/110938.
		video.onloadedmetadata = function(e) {
		  // Ready to go. Do some stuff.
		};
	}, onFailSoHard);
}

function grabScreenShot(){
	var canvas = document.getElementById("c");
	var context = canvas.getContext("2d");
	var video = document.querySelector('video');
	canvas.width = video.videoWidth;
	canvas.height = video.videoHeight;
	context.drawImage(video,0,0,canvas.width,canvas.height);
	//video.hidden = true;
	//video.pause();
	//canvas.hidden = false;
	var img    = canvas.toDataURL("image/png");
	return img
}