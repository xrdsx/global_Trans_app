document.querySelectorAll('.start-route-form').forEach(form => {
    const dayWorkId = form.parentNode.parentNode.getAttribute('data-dayWorkId');
    fetch(`/isRouteInProgress/${dayWorkId}`)
      .then(response => response.json())
      .then(inProgress => {
        if (inProgress) {
          form.style.display = 'none';
        }
      });
  });