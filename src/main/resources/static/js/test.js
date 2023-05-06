document.getElementById("date").addEventListener("change", function() {
  var selectedDate = this.value;
  updateDropdownOptions("/daywork-management/drivers-by-date?date=" + selectedDate, "driver", function(option, item) {
    option.value = item.id;
    option.text = item.firstName + ' ' + item.lastName;
  });
  updateDropdownOptions("/daywork-management/vehicles-by-date?date=" + selectedDate, "vehicle", function(option, item) {
    option.value = item.id;
    option.text = item.mark + ' ' + item.model;
  });
  updateDropdownOptions("/daywork-management/routes-by-date?date=" + selectedDate, "route", function(option, item) {
    option.value = item.id;
    option.text = item.nameRoute;
  });
});

function updateDropdownOptions(url, dropdownId, setOptionProperties) {
  var xhr = new XMLHttpRequest();
  xhr.open("GET", url, true);
  xhr.onload = function() {
    if (xhr.status >= 200 && xhr.status < 400) {
      var options = JSON.parse(xhr.responseText);
      var dropdown = document.getElementById(dropdownId);
      dropdown.innerHTML = "";
      for (var i = 0; i < options.length; i++) {
        var option = document.createElement("option");
        setOptionProperties(option, options[i]);
        dropdown.add(option);
      }
    }
  };
  xhr.send();
}