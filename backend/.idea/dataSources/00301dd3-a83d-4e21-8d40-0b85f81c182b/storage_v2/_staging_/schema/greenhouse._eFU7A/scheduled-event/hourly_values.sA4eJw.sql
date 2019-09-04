create definer = root@localhost event hourly_values
  on schedule
    every '1' hour 
      starts '2019-05-18 00:00:00'
  enable
do
  INSERT INTO daily_sensor_values(temperature,humidity, moisture, current_flow, flow_rate )
SELECT ROUND(AVG(temperature), 2) AS temperature,
      ROUND(AVG(humidity), 2) AS humidity,
       ROUND(AVG(moisture), 2) AS moisture,
       ROUND(AVG(current_flow), 2) AS current_flow,
       ROUND(AVG(flow_rate), 2) AS flow_rate
FROM sensor_values
WHERE time_stamp >= DATE_ADD(NOW(), INTERVAL -1 hour)
  AND time_stamp < NOW();

