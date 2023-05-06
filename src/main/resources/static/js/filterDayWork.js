$(function() {
  // filter by date
  $('#filterDate').on('change', function() {
    var filterDate = $(this).val();
    $('.daywork-row').hide();
    $('.daywork-row').filter(function() {
      return $(this).find('td:eq(1)').text() == filterDate;
    }).show();
  });

  $(document).ready(function() {
    // Set filterDate to today's date
    var filterDate = new Date().toISOString().substr(0, 10);
    $('#filterDate').val(filterDate);

    // Filter table by today's date
    $('.daywork-row').hide();
    $('.daywork-row').filter(function() {
      return $(this).find('td:eq(1)').text() == filterDate;
    }).show();
  });

  // filter by driver
  $('#filterDriver').on('change', function() {
    var filterDriver = $(this).val();
    $('.daywork-row').hide();
    $('.daywork-row').filter(function() {
      return filterDriver == '' || $(this).find('td:eq(2)').text().indexOf(filterDriver) >= 0;
    }).show();
  });


  // today button
  $('#todayBtn').on('click', function() {
    $('#filterDate').val(getTodayDate());
    $('#filterDate').trigger('change');
  });

  // yesterday button
  $('#yesterdayBtn').on('click', function() {
    $('#filterDate').val(getYesterdayDate());
    $('#filterDate').trigger('change');
  });

  // tomorrow button
  $('#tomorrowBtn').on('click', function() {
    $('#filterDate').val(getTomorrowDate());
    $('#filterDate').trigger('change');
  });

  // get today's date in yyyy-mm-dd format
  function getTodayDate() {
    var today = new Date();
    return today.getFullYear() + '-' + padZero(today.getMonth()+1) + '-' + padZero(today.getDate());
  }

  // get yesterday's date in yyyy-mm-dd format
  function getYesterdayDate() {
    var yesterday = new Date();
    yesterday.setDate(yesterday.getDate() - 1);
    return yesterday.getFullYear() + '-' + padZero(yesterday.getMonth()+1) + '-' + padZero(yesterday.getDate());
  }

  // get tomorrow's date in yyyy-mm-dd format
  function getTomorrowDate() {
    var tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    return tomorrow.getFullYear() + '-' + padZero(tomorrow.getMonth()+1) + '-' + padZero(tomorrow.getDate());
  }

  // pad single digit number with zero
  function padZero(num) {
    return num < 10 ? '0' + num : num;
  }

$('#toggleRecordsBtn').on('click', function() {
  $('.daywork-row').toggle();
});

});

