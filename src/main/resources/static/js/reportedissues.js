function showDetails() {
    var hiddenColumns = document.getElementsByClassName('hidden-column');
    for (var i = 0; i < hiddenColumns.length; i++) {
      hiddenColumns[i].style.display = 'table-cell';
    }
  }