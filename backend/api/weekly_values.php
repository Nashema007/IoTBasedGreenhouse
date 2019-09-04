<?php
	require ('../utilities/Dbconnection.php');
	
	$dbcon = new Dbconnection();
	$con = $dbcon->con();
	
	$query = $con->query("Select * from weekly_sensor_values
		WHERE time_stamp >= DATE_ADD(NOW(), INTERVAL -1 week) AND time_stamp < NOW()");
	
	$response = array();
	
	while($row = $query->fetch_array()){
		
		array_push($response, array(
			"temperature"=>$row['temperature'],
			"humidity"=>$row['humidity'],
			"moisture"=>$row['moisture'],
			"current_flow"=>$row['current_flow'],
			"flow_rate"=>$row['flow_rate']));
	}
	
	               echo json_encode($response);
	$con->close();
	