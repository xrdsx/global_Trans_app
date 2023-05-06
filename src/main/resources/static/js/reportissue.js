document.getElementById("locationButton").addEventListener("click", function() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(function(position) {
        var latitude = position.coords.latitude;
        var longitude = position.coords.longitude;
        document.getElementById("location").value = latitude + ", " + longitude;
      });
    } else {
      alert("Geolocation is not supported by this browser.");
    }
  });

   var descriptionInput = document.getElementById("description");
    var descriptionCount = document.getElementById("descriptionCount");

    descriptionInput.addEventListener("input", function() {
      var count = descriptionInput.value.length;
      descriptionCount.innerHTML = count + " / 100 characters";

      if (count > 100) {
        descriptionInput.setCustomValidity("Description must be 100 characters or less.");
      } else {
        descriptionInput.setCustomValidity("");
      }
    });

    window.onload = function() {
      var seconds = 15;
      var countdown = setInterval(function() {
        seconds--;
        document.getElementById("countdown").innerHTML = "Powrót do panelu za " + seconds + " sekund...";
        if (seconds <= 0) {
          clearInterval(countdown);
          window.location.href = "/dashboard";
        }
      }, 1000);
    };