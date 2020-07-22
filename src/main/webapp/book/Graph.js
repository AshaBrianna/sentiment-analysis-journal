google.charts.load('current', {'packages':['corechart', 'line']});
google.charts.setOnLoadCallback(drawChart);



function drawChart() {
    var data = new google.visualization.DataTable();
    data.addColumn('date','Date');
    data.addColumn('number', 'Score');

    var options = {
        title: 'Score vs. Date comparison',
        hAxis: {title: 'Date'},
        vAxis: {title: 'Score',
                ticks: [-1,-.5,0,.5,1]},
        legend: 'none'
    };

    var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));


    fetch('/journal').then(response => response.json()).then((entries) => {
        var rows = [];
         entries.forEach((entry) => {
             var row = [];
             row.push(new Date(entry.timestamp));
             row.push(entry.score);
             rows.push(row);
         })
         data.addRows(rows);
         chart.draw(data, options); 
         })

/*
    data.addRows([
        [new Date(2020,07,13), -1],
        [new Date(2020,07,14), .5],
        [new Date(2020,07,15), 1],
        [new Date(2020,07,16), .25]
    ]);
*/



}