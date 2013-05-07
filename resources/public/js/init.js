var webrtc = new WebRTC({
    // the id/element dom element that will hold "our" video
    localVideoEl: 'localVideo',
    // the id/element dom element that will hold remote videos
    remoteVideosEl: 'remotesVideos',
    // immediately ask for camera access
    autoRequestMedia: true
});

webrtc.createRoom("myroom123", function (err, name) {
  var newUrl = location.pathname + '?' + name;
  if (!err) {
    history.replaceState({foo: 'bar'}, null, newUrl);
    console.log("created room: " + name);
  }
});

// we have to wait until it's ready
webrtc.on('readyToCall', function () {
    // you can name it anything
    webrtc.joinRoom('myroom123');
});


