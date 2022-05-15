Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';

/* Area Chart Example */
function createLineChart(id) {
    let lineChartId = document.getElementById(id);
    let lineChart = new Chart(lineChartId, {
        type: 'line',
        data: {
            labels: ["Mar 1", "Mar 2", "Mar 3", "Mar 4", "Mar 5", "Mar 6", "Mar 7", "Mar 8", "Mar 9", "Mar 10", "Mar 11", "Mar 12", "Mar 13", "Mar 14"],
            datasets: [
                {
                    label: "Sessions",
                    lineTension: 0.3,
                    backgroundColor: "rgba(2,117,216,0.2)",
                    borderColor: "rgba(2,117,216,1)",
                    pointRadius: 5,
                    pointBackgroundColor: "rgba(2,117,216,1)",
                    pointBorderColor: "rgba(255,255,255,0.8)",
                    pointHoverRadius: 5,
                    pointHoverBackgroundColor: "rgba(2,117,216,1)",
                    pointHitRadius: 50,
                    pointBorderWidth: 2,
                    data: [10000, 30162, 26263, 18394, 18287, 28682, 31274, 33259, 25849, 24159, 32651, 31984, 38451, 31452],
                }
            ]
        },
        options: {
            scales: {
                xAxes: [{
                    time: {
                        unit: 'date'
                    },
                    gridLines: {
                        display: false
                    },
                    ticks: {
                        maxTicksLimit: 7
                    }
                }],
                yAxes: [{
                    ticks: {
                        min: 0,
                        max: 700000,
                        maxTicksLimit: 5
                    },
                    gridLines: {
                        color: "rgba(0, 0, 0, .125)",
                    }
                }],
            },
            legend: {
                display: false
            }
        }
    });
    return lineChart;
}

function setLineDatas(lineChart, labels, datas) {

    // let myLineChart = createLineChart(id);
    if (lineChart === undefined) {
        log("line chart 객체가 정의 되지 않았습니다.");
        return;
    }
    lineChart.data.labels = labels;

    let index = 0;
    lineChart.data.datasets.forEach(function (element) {
        element.data = datas[index];
        index++;
    });

    lineChart.update();
}


/* Bar Chart Example */
function createBarchart(id) {
    let barChart = document.getElementById(id);
    let myBarChart = new Chart(barChart, {
        type: 'bar',
        data: {
            labels: ["January", "February", "March", "April", "May", "June"],
            datasets: [{
                label: "Revenue",
                backgroundColor: "rgba(2,117,216,1)",
                borderColor: "rgba(2,117,216,1)",
                data: [4215, 5312, 6251, 7841, 9821, 14984],
            }],
        },
        options: {
            scales: {
                xAxes: [{
                    time: {
                        unit: 'month'
                    },
                    gridLines: {
                        display: false
                    },
                    ticks: {
                        maxTicksLimit: 6
                    }
                }],
                yAxes: [{
                    ticks: {
                        min: 0,
                        max: 700000,
                        maxTicksLimit: 5
                    },
                    gridLines: {
                        display: true
                    }
                }],
            },
            legend: {
                display: false
            }
        }
    });
    return myBarChart;
}

function createBarChartHigh(id) {
    return createBarchart(id);
}

function setBarHighDatas(barChartHigh, labels, datas) {

    if (barChartHigh === undefined) {
        log("high bar chart  객체가 정의 되지 않았습니다.");
        return;
    }
    barChartHigh.data.labels = labels;

    let index = 0;
    barChartHigh.data.datasets.forEach(function (element) {
        element.data = datas[index];
        index++;
    });

    barChartHigh.update();
}


function createBarChartLow(id) {
    return createBarchart(id);
}

function setBarLowDatas(barChartLow, labels, datas) {
    if (barChartLow === undefined) {
        log("low bar chart  객체가 정의 되지 않았습니다.");
        return;
    }

    barChartLow.data.labels = labels;

    let index = 0;
    barChartLow.data.datasets.forEach(function (element) {
        element.data = datas[index];
        index++;
    });

    barChartLow.update();
}


/* Pie Chart Example */
function createPieChart(id) {
    let pieChartId = document.getElementById(id);
    let pieChart = new Chart(pieChartId, {
        type: 'pie',
        data: {
            labels: ["Blue", "Red", "Yellow", "Green"],
            datasets: [{
                data: [12.21, 15.58, 11.25, 8.32],
                backgroundColor: ['#007bff', '#dc3545', '#ffc107', '#28a745', 'black'],
            }],
        },
    });
    return pieChart;
}

function setPieDatas(pieChart, labels, datas) {
    if ( pieChart === undefined ) {
        log("pie chart  객체가 정의 되지 않았습니다.");
        return;
    }

    pieChart.data.labels = labels;

    let index = 0;
    pieChart.data.datasets.forEach(function (element) {
        element.data = datas[index];
        index++;
    });

    pieChart.update();
}
