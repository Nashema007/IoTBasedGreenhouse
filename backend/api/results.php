<?php
	//set_time_limit(40);
	ini_set('max_execution_time', 0);
	header("Refresh: 20");
	         require  ("../utilities/Dbconnection.php");
	         $dbconn = new Dbconnection();
	         $con = $dbconn->con();
	
	
	//parse_str(, $data);
// Cast it to an object
	$data = json_decode(file_get_contents('http://192.168.4.1/'));
	
	
	
		if (isset($data)) {
			$temp1 = $data->temperature;
			$hum1 = $data->humidity;
			$moisture = $data->moisture;
			$flow_rate = $data->flow_rate;
			$current_rate = $data->current_flow;
			if ($flow_rate == null || $flow_rate == "null" || $flow_rate == " ") {
				$flow_rate = 0;
			}
			
			$results = $con->query("INSERT INTO sensor_values (temperature, humidity, moisture, current_flow, flow_rate)
			VALUES ('$temp1', '$hum1', '$moisture','$current_rate', '$flow_rate')");
			
			if ($results) {
				$result = array(
					'temperature' => $data->temperature,
					'humidity' => $data->humidity,
					'moisture' => $data->moisture,
					'flow_rate' => $data->flow_rate,
					'current_rate' => $data->current_flow
				);
				$json = json_encode($result);
				echo $json;
				
			} else {
				echo "failed";
				
			}
			
		
	}
	$con->close();
	