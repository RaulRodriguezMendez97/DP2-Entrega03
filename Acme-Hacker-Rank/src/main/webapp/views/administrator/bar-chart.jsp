<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div class="chart-container" style="position: height:20vh; width:40vw">
    <canvas id="myChart" ></canvas>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.min.js"></script>

<script>
var ctx = document.getElementById("myChart");
var myChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ["Total", "Presidents", "Vice-Presidents", "Secretaries", "Treasurers", "Historians", "Fundraisers", "Officers"],
        datasets: [{
            label: '# Positions',
            data: [value="${statistics.get('total')}",
                   value="${statistics.get('countPresident')}",
                   value="${statistics.get('countVicePresident')}",
                   value="${statistics.get('countSecretaty')}",
                   value="${statistics.get('countTreasurer')}",
                   value="${statistics.get('countHistorian')}",
                   value="${statistics.get('countFundraiser')}",
                   value="${statistics.get('countOfficer')}"],
            backgroundColor: [
                'rgba(249, 245, 0, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(255, 206, 86, 0.2)'
            ],
            borderColor: [
                '#F9F500',
                'rgba(75, 192, 192, 1)',
                'rgba(255, 159, 64, 1)'
            ],
            borderWidth: 2
        }]
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero:true
                }
            }]
        }
    }
});
</script>
