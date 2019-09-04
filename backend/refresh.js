	$.ajax({

		url: 'https://192.168.4.2/iotgreenhouse/api/daily_values.php',
		success: (data) =>{
			if(data.success === true){
				setTimeout(()=>{
					// location.reload();
				}, 20000);

			}
		}
	});
