<?php
	require ('../utilities/Dbconnection.php');
	
	class  Functions{
		
		// hourly updates
		function calDailyValues($column, $year, $month, $daily, $startTime, $endTime){
			
			$dbcon = new Dbconnection();
			$con = $dbcon->con();
			$results = $con->query("Select $column from sensor_values where time_stamp  BETWEEN
  		'$year-$month-$daily $startTime:00:01' and '$year-$month-$daily $endTime:00:00'");
			
			$value = 0;
			while ($row = $results->fetch_array()) {
				$value += $row[$column];
			}
			$divider = $results->num_rows;
			if($divider == 0){
				$divider = 1;
			}
			return round($value / $divider, 2);
		}
		
		// 7 DAY INTERVAL
		function calWeeklyValues($column, $year, $month, $startDay, $endDay)
		{
			$dbcon = new Dbconnection();
			$con = $dbcon->con();
			$results = $con->query("Select $column from sensor_values where time_stamp  BETWEEN
  		'$year-$month-$startDay 00:00:00' and '$year-$month-$endDay 23:59:59'");
			
			$value = 0;
			while ($row = $results->fetch_array()) {
				$value += $row[$column];
			}
			$divider = $results->num_rows;
			if($divider == 0){
				$divider = 1;
			}
			return round($value / $divider, 2);
		}
		         // monthly updates updates
		function calMonthlyValues($column, $year, $startMonth, $endMonth, $endDay){
			$dbcon = new Dbconnection();
			$con = $dbcon->con();
			$results = $con->query("Select $column from sensor_values where time_stamp  BETWEEN
  		'$year-$startMonth-01 00:00:00' and '$year-$endMonth-$endDay 23:59:59'");
			
			$value = 0;
			while ($row = $results->fetch_array()){
				$value +=$row[$column];
			}
			$divider = $results->num_rows;
			if($divider == 0){
				$divider = 1;
			}
			return round($value / $divider, 2);
		}
		
	}